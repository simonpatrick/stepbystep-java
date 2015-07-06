package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain.jdbc;

import com.mysema.query.QueryException;
import com.mysema.query.sql.*;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.sql.types.Type;
import com.mysema.query.types.Expression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.jdbc.query.*;
import org.springframework.data.jdbc.support.DatabaseType;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QueryDslJdbcTemplate implements QueryDslJdbcOperations {

    protected final Logger logger = LoggerFactory.getLogger(QueryDslJdbcTemplate.class);

    private JdbcTemplate jdbcTemplate;

    private SQLTemplates dialect;

    private Configuration configuration;

    public QueryDslJdbcTemplate(DataSource dataSource) {
        this(new JdbcTemplate(dataSource));
    }

    public QueryDslJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        DatabaseType dbType;
        try {
            dbType = DatabaseType.fromMetaData(jdbcTemplate.getDataSource());
        } catch (MetaDataAccessException e) {
            throw new org.springframework.dao.DataAccessResourceFailureException("Unable to determine database type: ", e);
        }
        if (dbType == DatabaseType.DERBY) {
            this.dialect = new DerbyTemplates();
        } else if (dbType == DatabaseType.H2) {
            this.dialect = new H2Templates();
        } else if (dbType == DatabaseType.HSQL) {
            this.dialect = new HSQLDBTemplates();
        } else if (dbType == DatabaseType.MYSQL) {
            this.dialect = new MySQLTemplates();
        } else if (dbType == DatabaseType.ORACLE) {
            this.dialect = new OracleTemplates();
        } else if (dbType == DatabaseType.POSTGRES) {
            this.dialect = new PostgresTemplates();
        } else if (dbType == DatabaseType.SQLSERVER) {
            this.dialect = new SQLServerTemplates();
        } else {
            throw new InvalidDataAccessResourceUsageException(dbType + " is an unsupported database");
        }
        this.configuration = new Configuration(dialect);
    }

    public QueryDslJdbcTemplate(JdbcTemplate jdbcTemplate, SQLTemplates dialect) {
        this.jdbcTemplate = jdbcTemplate;
        this.dialect = dialect;
        this.configuration = new Configuration(this.dialect);
    }

    public QueryDslJdbcTemplate(
            JdbcTemplate jdbcTemplate, Configuration configuration) {
        this.jdbcTemplate = jdbcTemplate;
        this.configuration = configuration;
        if (configuration != null) {
            this.dialect = configuration.getTemplates();
        }
        if (this.dialect == null) {
            throw new InvalidDataAccessResourceUsageException(
                    "The given configuration must contains SQLTemplates (not null).");
        }
    }

    public QueryDslJdbcTemplate(DataSource dataSource, Configuration configuration) {
        this(new JdbcTemplate(dataSource), configuration);
    }

    @Override
    public JdbcOperations getJdbcOperations() {
        return jdbcTemplate;
    }

    @Override
    public SQLQuery newSqlQuery() {
        return new SQLQuery(configuration);
    }

    @Override
    public long count(SQLQuery sqlQuery) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            try {
                return liveQuery.count();
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLQuery", liveQuery.toString());
            }
        });
    }

    @Override
    public long countDistinct(SQLQuery sqlQuery) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            liveQuery.distinct();
            try {
                return liveQuery.count();
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLQuery", liveQuery.toString());
            }
        });
    }

    @Override
    public boolean exists(SQLQuery sqlQuery) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            try {
                return liveQuery.exists();
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLQuery", liveQuery.toString());
            }
        });
    }

    @Override
    public boolean notExists(SQLQuery sqlQuery) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            try {
                return liveQuery.notExists();
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLQuery", liveQuery.toString());
            }
        });
    }

    @Override
    public <T> T queryForObject(SQLQuery sqlQuery, ResultSetExtractor<T> resultSetExtractor, Expression<?>... projection) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            ResultSet resultSet = queryForResultSet(liveQuery, projection);
            T t = resultSetExtractor.extractData(resultSet);
            JdbcUtils.closeResultSet(resultSet);
            return t;
        });
    }

    @Override
    public <T> T queryForObject(SQLQuery sqlQuery, RowMapper<T> rowMapper, Expression<?>... projection) {
        List<T> results = query(sqlQuery, rowMapper, projection);
        if (results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
        return results.get(0);
    }

    @Override
    public <T> T queryForObject(SQLQuery sqlQuery, Expression<T> expression) {
        List<T> results = query(sqlQuery, expression);
        if (results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
        return results.get(0);
    }

    @Override
    public <T> List<T> query(
            final SQLQuery sqlQuery, final ResultSetExtractor<List<T>> resultSetExtractor,
            final Expression<?>... projection) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            ResultSet resultSet = queryForResultSet(liveQuery, projection);
            List<T> list = resultSetExtractor.extractData(resultSet);
            JdbcUtils.closeResultSet(resultSet);
            return list;
        });
    }

    @Override
    public <T> List<T> query(SQLQuery sqlQuery, RowMapper<T> rowMapper, Expression<?>... projection) {
        return query(sqlQuery, new RowMapperResultSetExtractor<>(rowMapper), projection);
    }

    @Override
    public <T> List<T> query(SQLQuery sqlQuery, Expression<T> expression) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLQuery liveQuery = sqlQuery.clone(con);
            if (logger.isDebugEnabled()) {
                logger.debug("#QueryDSL query: {}", liveQuery.toString());
            }
            try {
                return liveQuery.list(expression);
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLQuery", liveQuery.toString());
            }
        });
    }

    @Override
    public long insert(RelationalPath<?> entity, SqlInsertCallback callback) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLInsertClause sqlClause = new SQLInsertClause(con, configuration, entity);
            try {
                return callback.doInSqlInsertClause(sqlClause);
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLInsertClause", sqlClause.toString());
            }
        });
    }

    @Override
    public <K> K insertWithKey(RelationalPath<?> entity, SqlInsertWithKeyCallback<K> callback) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLInsertClause sqlClause = new SQLInsertClause(con, configuration, entity);
            try {
                return callback.doInSqlInsertWithKeyClause(sqlClause);
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLInsertClause", sqlClause.toString());
            }
        });
    }

    @Override
    public long update(RelationalPath<?> entity, SqlUpdateCallback callback) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLUpdateClause sqlClause = new SQLUpdateClause(con, configuration, entity);
            try {
                return callback.doInSqlUpdateClause(sqlClause);
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLUpdateClause", sqlClause.toString());
            }
        });
    }

    @Override
    public long delete(RelationalPath<?> entity, SqlDeleteCallback callback) {
        return jdbcTemplate.execute((Connection con) -> {
            SQLDeleteClause sqlClause = new SQLDeleteClause(con, configuration, entity);
            try {
                return callback.doInSqlDeleteClause(sqlClause);
            } catch (QueryException qe) {
                throw translateQueryException(qe, "SQLDeleteClause", sqlClause.toString());
            }
        });
    }

    public void registerColumnType(String table, String column, Type<?> columnType) {
        Objects.requireNonNull(table, "The given table must be not null!");
        Objects.requireNonNull(column, "The given column name must be not null!");
        Objects.requireNonNull(columnType, "The column type must be not null!");

        configuration.register(table, column, columnType);
    }

    public void setCustomColumnTypes(Map<String, Map<String, Type<?>>> customColumnTypes) {
        if (customColumnTypes == null || customColumnTypes.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Map<String, Type<?>>> entry : customColumnTypes.entrySet()) {
            Map<String, Type<?>> column2TypeMap = entry.getValue();
            if (column2TypeMap == null || column2TypeMap.isEmpty()) {
                continue;
            }
            column2TypeMap.entrySet().stream()
                    .filter(entry2 -> entry2.getValue() != null)
                    .forEach(entry2 -> configuration.register(
                            entry.getKey(), entry2.getKey(), entry2.getValue()));
        }
    }

    public void setCustomTypes(List<Type<?>> types) {
        if (types == null || types.isEmpty()) {
            return;
        }
        types.stream().forEach(configuration::register);
    }

    private ResultSet queryForResultSet(SQLQuery liveQuery, Expression<?>[] projection) throws DataAccessException {
        ResultSet resultSet;
        try {
            resultSet = liveQuery.getResults(projection);
        } catch (QueryException qe) {
            throw translateQueryException(qe, "SQLQuery", liveQuery.toString());
        }
        return resultSet;
    }

    private RuntimeException translateQueryException(QueryException qe, String task, String query) {
        Throwable t = qe.getCause();
        if (t instanceof SQLException) {
            return jdbcTemplate.getExceptionTranslator()
                    .translate(task, query, (SQLException) t);
        }
        return new UncategorizedQueryException("Error in " + "SQLQuery", qe);
    }
}
