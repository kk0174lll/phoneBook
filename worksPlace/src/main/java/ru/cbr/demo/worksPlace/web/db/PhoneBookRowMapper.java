package ru.cbr.demo.worksPlace.web.db;

import models.PhoneBook;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class PhoneBookRowMapper implements RowMapper<PhoneBook>
{

  @Override
  public PhoneBook mapRow(ResultSet rs, int rowNum) throws SQLException
  {
    PhoneBook phoneBook = new PhoneBook();
    phoneBook.lastName = rs.getString("lastname");
    phoneBook.firstName = rs.getString("firstname");
    phoneBook.workPhone = rs.getString("workphone");
    phoneBook.mobilePhone = rs.getString("mobilephone");
    phoneBook.mail = rs.getString("mail");
    Date birthDate = rs.getDate("birthdate");
    if (birthDate != null) {
      phoneBook.birthDate = birthDate.toLocalDate();
    }
    phoneBook.work = rs.getString("work");
    return phoneBook;
  }
}