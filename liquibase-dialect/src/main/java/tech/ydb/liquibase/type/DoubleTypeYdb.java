package tech.ydb.liquibase.type;

import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;

/**
 * @author Kirill Kurdyukov
 */
@DataTypeInfo(
        name = "Double",
        aliases = {"double", "java.sql.Types.DOUBLE", "java.lang.Double"},
        minParameters = 0,
        maxParameters = 0,
        priority = LiquibaseDataType.PRIORITY_DATABASE
)
public class DoubleTypeYdb extends BaseTypeYdb {

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if ((value == null) || "null".equalsIgnoreCase(value.toString())) {
            return "NULL";
        }

        return value.toString();
    }
}
