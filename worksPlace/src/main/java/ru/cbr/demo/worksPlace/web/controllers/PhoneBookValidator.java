package ru.cbr.demo.worksPlace.web.controllers;

import models.PhoneBook;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import utils.Utils;

@Component
public class PhoneBookValidator implements Validator
{

  @Override
  public boolean supports(Class<?> aClass) {
    return PhoneBook.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object obj, Errors errors) {
    PhoneBook phoneBook = (PhoneBook) obj;
    if (Utils.isEmpty(phoneBook.lastName)) {
      errors.reject("lastName", "lastName is empty");
    }
    if (Utils.isEmpty(phoneBook.firstName)) {
      errors.reject("firstName", "firstName is empty");
    }
    if (Utils.isEmpty(phoneBook.workPhone)) {
      errors.reject("workPhone", "workPhone is empty");
    }
    if (Utils.isEmpty(phoneBook.mobilePhone)) {
      errors.reject("mobilePhone", "mobilePhone is empty");
    }
    if (Utils.isEmpty(phoneBook.mail)) {
      errors.reject("mail", "mail is empty");
    }
    if (Utils.isEmpty(phoneBook.work)) {
      errors.reject("work", "work is empty");
    }
  }

}