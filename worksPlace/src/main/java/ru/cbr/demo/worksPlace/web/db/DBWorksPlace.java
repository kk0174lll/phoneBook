package ru.cbr.demo.worksPlace.web.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cbr.demo.worksPlace.web.models.WorksPlace;

@Component
public class DBWorksPlace extends DBAbstract<WorksPlace>
{

  private static final String searchWorksPlace = "select name, surname, work, address from public.search_worksplace(?, ?, ?, ?, ?, ?)";
  private static final String searchWorksPlaceCount = "select public.search_worksplace_count(?, ?, ?, ?)";
  private static final String addWorksPlace = "select public.add_worksplace(?, ?, ?, ?)";

  public DBWorksPlace(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper, WorksPlaceRowMapper worksPlaceRowMapper)
  {
    super(jdbcTemplate, objectMapper, worksPlaceRowMapper);
  }


  @Override
  public Object[] getParams(WorksPlace searchData)
  {
    return new Object[] {searchData.name,
                         searchData.surname,
                         searchData.work,
                         searchData.address
    };
  }

  @Override
  public Object[] getPagingParams(WorksPlace searchData, Integer rowBegin, Integer rowEnd)
  {
    return new Object[] {searchData.name,
                         searchData.surname,
                         searchData.work,
                         searchData.address,
                         rowBegin,
                         rowEnd
    };
  }

  @Override
  public String getListSql()
  {
    return searchWorksPlace;
  }

  @Override
  public String getCountSql()
  {
    return searchWorksPlaceCount;
  }

  @Override
  public String addSql()
  {
    return addWorksPlace;
  }
}
