package nodescript.lang.nodes;

import nodescript.lang.*;

public class OrNode extends Node {
  private Node receiver;
  private Node argument;
  
  /**
    receiver || argument
  */
  public OrNode(Node receiver, Node argument) {
    this.receiver = receiver;
    this.argument = argument;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptObject receiverEvaled = receiver.eval(context);
    if (receiverEvaled.isTrue())
      return receiverEvaled;
    return argument.eval(context);
  }
}