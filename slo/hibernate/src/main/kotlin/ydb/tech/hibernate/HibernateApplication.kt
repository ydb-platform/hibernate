package tech.ydb.slo.hibernate

import io.prometheus.metrics.core.metrics.Counter
import io.prometheus.metrics.exporter.pushgateway.PushGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import tech.ydb.slo.hibernate.service.SloService
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Executors


@SpringBootApplication
class HibernateApplication : CommandLineRunner {

	@Autowired
	private lateinit var sloService: SloService

	override fun run(vararg args: String) {
		if (args.isNotEmpty() && args[0] == "create") {
			return // Flyway created slo table
		}

		val pushGateway = PushGateway.builder()
			.job("hibernate-slo")
			.address("prometheus-pushgateway:9091")
			.build()

		val okCount = Counter.builder().name("oks")
			.labelNames("jobName", "sdk", "sdkVersion")
			.help("Count of OK")
			.register()
		val notOkCount = Counter.builder().name("not_oks")
			.labelNames("jobName", "sdk", "sdkVersion")
			.help("Count of not OK")
			.register()

		val workersRead = Executors.newFixedThreadPool(3)
		val workersWrite = Executors.newFixedThreadPool(1)

		val startTime = Instant.now()

		while (Instant.now() < startTime.plus(Duration.ofSeconds(10))) {
			workersRead.submit {
				try {
					sloService.find()
					okCount.labelValues("read", "TODO", "hibernate").inc()
				} catch (e: Exception) {
					notOkCount.labelValues("read", "TODO", "hibernate").inc()
					println(e)
				}
				pushGateway.push()
			}

			workersWrite.submit {
				try {
					sloService.save()
					okCount.labelValues("write", "TODO", "hibernate").inc()
				} catch (e: Exception) {
					notOkCount.labelValues("write", "TODO", "hibernate").inc()
					println(e)
				}
				pushGateway.push()
			}
		}

		pushGateway.delete()
	}
}

fun main(args: Array<String>) {
	runApplication<HibernateApplication>(*args)
}
