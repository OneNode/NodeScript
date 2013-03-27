package nodescript.lang;

/**
  Exception that can be catched from inside the runtime.
*/
public class NodeScriptException extends Exception {
  private NodeScriptClass runtimeClass;
  
  /**
    Creates a new exception from a runtime class.
    @param runtimeClass Class of the exception from whitin the language.
  */
  public NodeScriptException(NodeScriptClass runtimeClass, String message) {
    super(message);
    this.runtimeClass = runtimeClass;
  }

  public NodeScriptException(NodeScriptClass runtimeClass) {
    super();
    this.runtimeClass = runtimeClass;
  }
  
  public NodeScriptException(String runtimeClassName, String message) {
    super(message);
    setRuntimeClass(runtimeClassName);
  }
  
  /**
    Creates a new exception from the Exception runtime class.
  */
  public NodeScriptException(String message) {
    super(message);
    this.runtimeClass = NodeScriptRuntime.getExceptionClass();
  }
  
  /**
    Wrap an exception to pass it to the runtime.
  */
  public NodeScriptException(Exception inner) {
    super(inner);
    setRuntimeClass(inner.getClass().getName());
  }
  
  /**
    Returns the runtime instance (the exception inside the language) of this exception.
  */
  public NodeScriptObject getRuntimeObject() {
    NodeScriptObject instance = runtimeClass.newInstance(this);
    instance.setInstanceVariable("message", new ValueObject(getMessage()));
    return instance;
  }

  public NodeScriptClass getRuntimeClass() {
    return runtimeClass;
  }

  protected void setRuntimeClass(String name) {
    runtimeClass = NodeScriptRuntime.getExceptionClass().subclass(name);
  }
}