package ru.cbr.demo.worksPlace.web.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.cbr.demo.worksPlace.web.models.SearchFilter;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;

@Component
public abstract class DBAbstract<T>
{

  private final ObjectMapper objectMapper;
  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<T> rowMapper;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());


  public DBAbstract(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper, RowMapper<T> rowMapper)
  {
    this.jdbcTemplate = jdbcTemplate;
    this.objectMapper = objectMapper;
    this.rowMapper = rowMapper;
  }


  private void logInfo(String sql, Object[] params)
  {
    try {
      logger.info("sql: " + sql + "; with params:" + objectMapper.writeValueAsString(params));
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage());
    }
  }

  public abstract Object[] getParams(T searchData);

  public abstract Object[] getPagingParams(T searchData, Integer rowBegin, Integer rowEnd);

  public abstract String getListSql();

  public abstract String getCountSql();

  public abstract String addSql();

  public List<T> getList(SearchFilter<T> searchData, Integer rowBegin, Integer rowEnd)
  {
    Object[] params = getPagingParams(searchData.filter, rowBegin, rowEnd);
    logInfo(getListSql(), params);
    return jdbcTemplate.query(getListSql(), params, rowMapper);
  }

  public Integer getCount(SearchFilter<T> searchData)
  {
    Object[] params = getParams(searchData.filter);
    logInfo(getCountSql(), params);
    return jdbcTemplate.queryForObject(getCountSql(), params, Integer.class);
  }

  public void add(T searchData) throws SQLException
  {
    Object[] params = getParams(searchData);
    logInfo(addSql(), params);
    String error = jdbcTemplate.queryForObject(addSql(), params, String.class);
    if (!Utils.isEmpty(error)) {
      throw new SQLException(error);
    }
  }

}
