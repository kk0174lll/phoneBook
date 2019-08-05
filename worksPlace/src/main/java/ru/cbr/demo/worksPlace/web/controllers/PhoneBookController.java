package ru.cbr.demo.worksPlace.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.PhoneBook;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.cbr.demo.worksPlace.web.db.DBPhoneBook;
import models.Response;
import ru.cbr.demo.worksPlace.web.models.SearchFilter;
import ru.cbr.demo.worksPlace.web.models.SearchResponse;

import javax.validation.Valid;

@RestController
public class PhoneBookController extends AbstractController<PhoneBook>
{

  public PhoneBookController(DBPhoneBook dBPhoneBook, ObjectMapper objectMapper, PhoneBookValidator phoneBookValidator)
  {
    super(dBPhoneBook, objectMapper, phoneBookValidator);
  }

  @RequestMapping (method = RequestMethod.POST, produces = CONTENT_TYPE, value = "/phoneBook/search")
  public SearchResponse<PhoneBook> search(@RequestBody SearchFilter<PhoneBook> filter)
  {
    return searchData(filter);
  }

  @RequestMapping (method = RequestMethod.POST, produces = CONTENT_TYPE, value = "/PhoneBook/add")
  public Response add(@Valid @RequestBody PhoneBook worksPlace,
                      BindingResult bindingResult)
  {
    return addData(worksPlace, bindingResult);
  }
}
