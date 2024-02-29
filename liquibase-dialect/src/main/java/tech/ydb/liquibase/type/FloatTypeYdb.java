package tech.ydb.liquibase.type;

import liquibase.change.core.LoadDataChange;
import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.LiquibaseDataType;

/**
 * @author Kirill Kurdyukov
 */
@DataTypeInfo(
        name = "Float",
        aliases = {
                "float", "java.sql.Types.FLOAT", "java.lang.Float",
                "real", "java.sql.Types.REAL"
        },
        minParameters = 0,
        maxParameters = 0,
        priority = LiquibaseDataType.PRIORITY_DATABASE
)

public class FloatTypeYdb extends BaseTypeYdb {

    @Override
    public LoadDataChange.LOAD_DATA_TYPE getLoadTypeName() {
        return LoadDataChange.LOAD_DATA_TYPE.NUMERIC;
    }

    @Override
    public String objectToSql(Object value, Database database) {
        if ((value == null) || "null".equalsIgnoreCase(value.toString())) {
            return "NULL";
        }

        return "CAST('" + value + "' AS FLOAT)";
    }
}
