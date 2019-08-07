package ru.cbr.demo.phoneBook.web.processor;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class PhoneBookSchema
{

  private static final String FILE = "classpath:phoneBook.schema.json";
  private Schema schema;

  public PhoneBookSchema() throws IOException
  {
    File file = ResourceUtils.getFile(FILE);
    try (InputStream inputStream = new FileInputStream(file)) {
      JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
      schema = SchemaLoader.load(rawSchema);
    }
  }

  public void validate(String json) throws ValidationException
  {
    schema.validate(new JSONObject(json));
  }

}
