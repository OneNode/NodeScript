package nodescript.lang;

/**
  A method attached to a NodeScriptClass.
*/
public abstract class Method {
  /**
    Calls the method.
    @param receiver  Instance on which to call the method (self).
    @param arguments Arguments passed to the method.
  */
  public abstract NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException;
}
