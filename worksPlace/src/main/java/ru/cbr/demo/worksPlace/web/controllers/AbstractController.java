package ru.cbr.demo.worksPlace.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.cbr.demo.worksPlace.web.db.DBAbstract;
import ru.cbr.demo.worksPlace.web.models.Pagination;
import models.Response;
import ru.cbr.demo.worksPlace.web.models.SearchFilter;
import ru.cbr.demo.worksPlace.web.models.SearchResponse;
import utils.Utils;

import java.util.Collections;

public abstract class AbstractController<T>
{

  private final DBAbstract<T> db;
  private final ObjectMapper objectMapper;
  private final Validator validator;

  static final String CONTENT_TYPE = Utils.CONTENT_TYPE;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected AbstractController(DBAbstract<T> db, ObjectMapper objectMapper, Validator validator)
  {
    this.db = db;
    this.objectMapper = objectMapper;
    this.validator = validator;
  }

  SearchResponse<T> searchData(SearchFilter<T> filter)
  {
    SearchResponse<T> response = new SearchResponse<>();
    try {
      logger.info("incoming search obj: " + objectMapper.writeValueAsString(filter));

      Integer rowCount = db.getCount(filter);
      if (rowCount != null && rowCount > 0) {
        Pagination pagination = Pagination.create(filter.page, filter.count, rowCount);
        response.searchResult = db.getList(filter, pagination.getRowBegin(), pagination.getRowEnd());
      } else {
        response.searchResult = Collections.emptyList();
      }
      response.count = rowCount;
      response.page = filter.page;
    } catch (Exception e) {
      response.errorMessage = "Ошибка при поиске записи";
      logger.error(e.getMessage());
    }
    return response;
  }

  Response addData(T data, BindingResult bindingResult)
  {
    Response response = new Response();
    try {
      logger.info("incoming add obj: " + objectMapper.writeValueAsString(data));

      validator.validate(data, bindingResult);
      if (bindingResult.hasErrors()) {
        logger.error(objectMapper.writeValueAsString(bindingResult.getAllErrors()));
        response.errorMessage = "Ошибка валидации";
        return response;
      }

      db.add(data);
    } catch (Exception e) {
      response.exceptionMessage = e.getMessage();
      response.errorMessage = "Ошибка при добавлении новой записи";
      logger.error(e.getMessage());
    }
    return response;
  }
}
