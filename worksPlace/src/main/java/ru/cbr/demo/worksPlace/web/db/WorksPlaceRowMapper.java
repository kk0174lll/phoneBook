package ru.cbr.demo.worksPlace.web.db;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.cbr.demo.worksPlace.web.models.WorksPlace;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WorksPlaceRowMapper implements RowMapper<WorksPlace>
{

  @Override
  public WorksPlace mapRow(ResultSet rs, int rowNum) throws SQLException
  {

    WorksPlace worksPlace = new WorksPlace();
    worksPlace.address = rs.getString("address");
    worksPlace.work = rs.getString("work");
    worksPlace.surname = rs.getString("surname");
    worksPlace.name = rs.getString("name");

    return worksPlace;

  }
}
