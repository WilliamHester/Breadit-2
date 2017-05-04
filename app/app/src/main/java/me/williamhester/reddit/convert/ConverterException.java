package me.williamhester.reddit.convert;

import com.google.gson.JsonElement;

/** An exception thrown when we failed to parse the response. */
public class ConverterException extends Exception {
  public ConverterException(String message) {
    super(message);
  }

  public ConverterException(JsonElement object, String expectedType) {
    super(expectedType + ", got:\n" + object.toString());
  }
}
