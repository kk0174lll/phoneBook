package ru.cbr.demo.phoneBook.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.PhoneBook;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.cbr.demo.phoneBook.web.models.FileRequest;
import ru.cbr.demo.phoneBook.web.models.PhoneBookResponse;
import ru.cbr.demo.phoneBook.web.processor.PhoneBookProcess;
import ru.cbr.demo.phoneBook.web.processor.PhoneBookSchema;
import utils.Utils;

@RestController
public class PhoneBookController
{

  private final ObjectMapper objectMapper;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final PhoneBookProcess processor;
  private final PhoneBookSchema phoneBookSchema;

  public PhoneBookController(ObjectMapper objectMapper, PhoneBookProcess processor, PhoneBookSchema phoneBookSchema)
  {
    this.objectMapper = objectMapper;
    this.processor = processor;
    this.phoneBookSchema = phoneBookSchema;
  }

  @RequestMapping (method = RequestMethod.POST, produces = Utils.CONTENT_TYPE, value = "/add")
  public PhoneBookResponse search(@RequestBody PhoneBook phoneBook)
  {
    PhoneBookResponse response = new PhoneBookResponse();
    logger.info("/add");
    try {
      String phoneBookJson = objectMapper.writeValueAsString(phoneBook);
      logger.info("incoming add obj: " + phoneBookJson);
      phoneBookSchema.validate(phoneBookJson);
      response.fileName = processor.writePrepareFile(phoneBookJson);
    } catch (ValidationException e) {
      response.errorMessage = "ошибка валидации файла";
      response.exceptionMessage = e.getMessage();
      logger.error(e.getMessage());
    } catch (Exception e) {
      response.errorMessage = "не удалось сохранить файл";
      response.exceptionMessage = e.getMessage();
      logger.error(e.getMessage(), e);
    }
    return response;
  }

  @RequestMapping (method = RequestMethod.POST, produces = Utils.CONTENT_TYPE, value = "/clear")
  public PhoneBookResponse clear(@RequestBody FileRequest fileRequest)
  {
    PhoneBookResponse response = new PhoneBookResponse();
    logger.info("/clear");
    try {
      logger.info("incoming clear obj: " + objectMapper.writeValueAsString(fileRequest));
      response.fileName = processor.deletePrepareFile(fileRequest.name);
    } catch (Exception e) {
      response.errorMessage = "не удалось удалить файл";
      response.exceptionMessage = e.getMessage();
      logger.error(e.getMessage());
    }
    return response;
  }

  @RequestMapping (method = RequestMethod.POST, produces = Utils.CONTENT_TYPE, value = "/send")
  public PhoneBookResponse send(@RequestBody FileRequest fileRequest)
  {
    PhoneBookResponse response = new PhoneBookResponse();
    logger.info("/send");
    try {
      logger.info("incoming clear obj: " + objectMapper.writeValueAsString(fileRequest));
      response.fileName = processor.transferFile(fileRequest.name);
    } catch (Exception e) {
      response.errorMessage = "не удалось переместить файл";
      response.exceptionMessage = e.getMessage();
      logger.error(e.getMessage());
    }
    return response;
  }
}
