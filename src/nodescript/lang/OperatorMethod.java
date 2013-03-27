package nodescript.lang;

/**
  Specialized method of operators (+, -, *, /, etc.)
*/
public abstract class OperatorMethod<T> extends Method {
  @SuppressWarnings("unchecked")
  public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
    T self = (T) receiver.as(ValueObject.class).getValue();
    T arg = (T) arguments[0].as(ValueObject.class).getValue();
    return perform(self, arg);
  }
  
  public abstract NodeScriptObject perform(T receiver, T argument) throws NodeScriptException;
}
