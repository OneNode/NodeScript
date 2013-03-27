package nodescript.lang;

/**
  Exception thrown when a unknown method is called.
*/
public class ArgumentError extends NodeScriptException {
  public ArgumentError(String method, int expected, int actual) {
    super("Expected " + expected + " arguments for " + method + ", got " + actual);
    setRuntimeClass("ArgumentError");
  }
}