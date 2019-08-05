package ru.cbr.demo.worksPlace.web.models;

import models.Response;

import java.util.List;

public class SearchResponse<T> extends Response
{

  public List<T> searchResult;

  public Integer count;
  public Integer page;
}
