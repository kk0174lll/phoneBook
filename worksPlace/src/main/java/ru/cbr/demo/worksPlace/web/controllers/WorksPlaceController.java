package ru.cbr.demo.worksPlace.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.cbr.demo.worksPlace.web.db.DBWorksPlace;
import models.Response;
import ru.cbr.demo.worksPlace.web.models.SearchFilter;
import ru.cbr.demo.worksPlace.web.models.SearchResponse;
import ru.cbr.demo.worksPlace.web.models.WorksPlace;

import javax.validation.Valid;

@RestController
public class WorksPlaceController extends AbstractController<WorksPlace>
{

  public WorksPlaceController(DBWorksPlace dBWorksPlace, ObjectMapper objectMapper, WorksPlaceValidator worksPlaceValidator)
  {
    super(dBWorksPlace, objectMapper, worksPlaceValidator);

  }

  @RequestMapping (method = RequestMethod.POST, produces = CONTENT_TYPE, value = "/worksPlace/search")
  public SearchResponse<WorksPlace> search(@RequestBody SearchFilter<WorksPlace> filter)
  {
    return searchData(filter);
  }


  @RequestMapping (method = RequestMethod.POST, produces = CONTENT_TYPE, value = "/worksPlace/add")
  public Response add(@Valid @RequestBody WorksPlace worksPlace,
                      BindingResult bindingResult)
  {
    return addData(worksPlace, bindingResult);
  }
}
