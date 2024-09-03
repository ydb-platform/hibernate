package tech.ydb.jooq.dsl.upsert;

import java.util.Collection;
import java.util.function.Function;
import org.jooq.CheckReturnValue;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.Rows;
import org.jooq.Select;
import tech.ydb.jooq.Upsert;
import tech.ydb.jooq.YdbDSLContext;

/**
 * This type is used for the {@link Upsert}'s DSL API.
 * <p>
 * Example: <pre><code>
 * using(configuration)
 *       .upsertInto(table, field1)
 *       .values(field1)
 *       .values(field1)
 *       .execute();
 * </code></pre>
 */
public interface UpsertValuesStep1<R extends Record, T1> extends Upsert<R> {

    /**
     * Add a single row of values to the upsert statement.
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> values(T1 value1);

    /**
     * Add a single row of values to the upsert statement.
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> values(Field<T1> value1);

    /**
     * Add a single row of values to the upsert statement.
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> values(Collection<?> values);

    /**
     * Add a single row of values to the upsert statement.
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> values(Row1<T1> values);

    /**
     * Add a single row of values to the upsert statement.
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> values(Record1<T1> values);

    /**
     * Add multiple rows of values to the upsert statement.
     * <p>
     * This is equivalent to calling the other values clauses multiple times, but
     * allows for dynamic construction of row arrays.
     *
     * @see Rows#toRowArray(Function)
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    UpsertValuesStep1<R, T1> valuesOfRows(Row1<T1>... values);

    /**
     * Add multiple rows of values to the upsert statement.
     * <p>
     * This is equivalent to calling the other values clauses multiple times, but
     * allows for dynamic construction of row arrays.
     *
     * @see Rows#toRowList(Function)
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> valuesOfRows(Collection<? extends Row1<T1>> values);

    /**
     * Add multiple rows of values to the upsert statement.
     * <p>
     * This is equivalent to calling the other values clauses multiple times, but
     * allows for dynamic construction of row arrays.
     * <p>
     * <strong>Note</strong>: Irrespective of individual {@link Record#changed()}
     * flag values, all record values are copied to the <code>VALUES</code> clause
     * using {@link Record#intoArray()}, to match upsert columns by position, not
     * by name. If you prefer omitting unchanged values and adding values by field
     * name rather than by index, use {@link UpsertSetStep#set(Record...)} instead.
     * That syntax is available only if you omit the explicit upsert columns list.
     */
    @CheckReturnValue
    @SuppressWarnings("unchecked")
    UpsertValuesStep1<R, T1> valuesOfRecords(Record1<T1>... values);

    /**
     * Add multiple rows of values to the upsert statement.
     * <p>
     * This is equivalent to calling the other values clauses multiple times, but
     * allows for dynamic construction of row arrays.
     * <p>
     * <strong>Note</strong>: Irrespective of individual {@link Record#changed()}
     * flag values, all record values are copied to the <code>VALUES</code> clause
     * using {@link Record#intoArray()}, to match upsert columns by position, not
     * by name. If you prefer omitting unchanged values and adding values by field
     * name rather than by index, use {@link UpsertSetStep#set(Record...)} instead.
     * That syntax is available only if you omit the explicit upsert columns list.
     */
    @CheckReturnValue
    UpsertValuesStep1<R, T1> valuesOfRecords(Collection<? extends Record1<T1>> values);

    /**
     * Use a <code>SELECT</code> statement as the source of values for the
     * <code>UPSERT</code> statement
     * <p>
     * This variant of the <code>UPSERT … SELECT</code> statement expects a
     * select returning exactly as many fields as specified previously in the
     * <code>INTO</code> clause:
     * {@link YdbDSLContext#upsertInto(Table, Field)}
     */
    @CheckReturnValue
    Upsert<R> select(Select<? extends Record1<T1>> select);
}
