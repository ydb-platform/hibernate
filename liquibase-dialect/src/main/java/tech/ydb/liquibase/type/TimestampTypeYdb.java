package tech.ydb.liquibase.type;

import java.sql.Timestamp;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;

/**
 * @author Kirill Kurdyukov
 */
@DataTypeInfo(
        name = "Timestamp",
        aliases = {
                "timestamp", "java.sql.Types.TIMESTAMP", "java.sql.TIMESTAMP",
                "java.sql.Types.TIMESTAMP_WITH_TIMEZONE", "timestamptz"
        },
        minParameters = 0,
        maxParameters = 0,
        priority = LiquibaseDataType.PRIORITY_DATABASE
)
public class TimestampTypeYdb extends BaseTypeYdb {

    @Override
    protected String objectToSql(Object value) {
        if (value instanceof Timestamp) {
            return "TIMESTAMP('" + ((Timestamp) value).toInstant() + "')";
        }

        return "TIMESTAMP('" + value + "')";
    }
}
