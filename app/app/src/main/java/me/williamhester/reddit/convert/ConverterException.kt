package me.williamhester.reddit.convert

import com.google.gson.JsonElement

/** An exception thrown when we failed to parse the response.  */
class ConverterException : Exception {
  constructor(message: String) : super(message)
  constructor(element: JsonElement, expectedType: String) :
      super(expectedType + ", got:\n" + element.toString())
}
