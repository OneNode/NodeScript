package nodescript.lang;

/**
  Exception thrown when a unknown method is called.
*/
public class MethodNotFound extends NodeScriptException {
  public MethodNotFound(String method) {
    super(method + " not found");
    setRuntimeClass("MethodNotFound");
  }
}