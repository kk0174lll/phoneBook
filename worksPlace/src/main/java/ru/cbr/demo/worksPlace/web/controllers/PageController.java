package ru.cbr.demo.worksPlace.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController
{


  @GetMapping (value = "/")
  public String main()
  {
    return "main";
  }

  @GetMapping ("/worksPlace")
  public String worksPlace()
  {
    return "worksPlace";
  }

  @GetMapping (value = "/phoneBook")
  public String phoneBook()
  {
    return "phoneBook";
  }
}
