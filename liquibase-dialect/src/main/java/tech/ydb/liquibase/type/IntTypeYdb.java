package tech.ydb.liquibase.type;

import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;

/**
 * @author Kirill Kurdyukov
 */
@DataTypeInfo(
        name = "Int32",
        aliases = {"int", "integer", "java.sql.Types.INTEGER", "java.lang.Integer", "int4", "int32"},
        minParameters = 0,
        maxParameters = 0,
        priority = LiquibaseDataType.PRIORITY_DATABASE
)
public class IntTypeYdb extends BaseTypeYdb {

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if ((value == null) || "null".equalsIgnoreCase(value.toString())) {
            return "NULL";
        }

        return super.objectToSql(value, database);
    }
}
