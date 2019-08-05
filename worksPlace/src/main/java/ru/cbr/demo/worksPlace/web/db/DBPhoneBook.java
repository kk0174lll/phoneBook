package ru.cbr.demo.worksPlace.web.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.PhoneBook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBPhoneBook extends DBAbstract<PhoneBook>
{

  private static final String searchPhoneBook = "select lastname, firstname, workphone, mobilephone, mail, birthdate, work from public.search_phonebook(?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String searchPhoneBookCount = "select public.search_phonebook_count(?, ?, ?, ?, ?, ?, ?)";
  private static final String addPhoneBook = "select public.add_phonebook(?, ?, ?, ?, ?, ?, ?)";

  public DBPhoneBook(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper, PhoneBookRowMapper phoneBookRowMapper)
  {
    super(jdbcTemplate, objectMapper, phoneBookRowMapper);
  }


  @Override
  public Object[] getParams(PhoneBook searchData)
  {
    return new Object[] {searchData.lastName,
                         searchData.firstName,
                         searchData.workPhone,
                         searchData.mobilePhone,
                         searchData.mail,
                         searchData.birthDate,
                         searchData.work
    };
  }

  @Override
  public Object[] getPagingParams(PhoneBook searchData, Integer rowBegin, Integer rowEnd)
  {
    return new Object[] {searchData.lastName,
                         searchData.firstName,
                         searchData.workPhone,
                         searchData.mobilePhone,
                         searchData.mail,
                         searchData.birthDate,
                         searchData.work,
                         rowBegin,
                         rowEnd
    };
  }

  @Override
  public String getListSql()
  {
    return searchPhoneBook;
  }

  @Override
  public String getCountSql()
  {
    return searchPhoneBookCount;
  }

  @Override
  public String addSql()
  {
    return addPhoneBook;
  }
}
