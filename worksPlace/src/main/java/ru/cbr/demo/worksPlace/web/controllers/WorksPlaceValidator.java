package ru.cbr.demo.worksPlace.web.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.cbr.demo.worksPlace.web.models.WorksPlace;
import utils.Utils;

@Component
public class WorksPlaceValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return WorksPlace.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    WorksPlace worksPlace = (WorksPlace) obj;
    if (Utils.isEmpty(worksPlace.address)) {
      errors.reject("address", "address is empty");
    }
    if (Utils.isEmpty(worksPlace.name)) {
      errors.reject("name", "name is empty");
    }
    if (Utils.isEmpty(worksPlace.surname)) {
      errors.reject("surname", "surname is empty");
    }
    if (Utils.isEmpty(worksPlace.work)) {
      errors.reject("work", "work is empty");
    }
  }

}