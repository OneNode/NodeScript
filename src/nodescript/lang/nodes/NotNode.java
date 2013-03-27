package nodescript.lang.nodes;

import nodescript.lang.*;

/**
  Negate a value.
*/
public class NotNode extends Node {
  private Node receiver;
  
  /**
    !receiver
  */
  public NotNode(Node receiver) {
    this.receiver = receiver;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    if (receiver.eval(context).isTrue())
      return NodeScriptRuntime.getFalse();
    return NodeScriptRuntime.getTrue();
  }
}