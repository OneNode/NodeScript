package nodescript.lang.nodes;

import nodescript.lang.*;

public class AndNode extends Node {
  private Node receiver;
  private Node argument;
  
  /**
    receiver && argument
  */
  public AndNode(Node receiver, Node argument) {
    this.receiver = receiver;
    this.argument = argument;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptObject receiverEvaled = receiver.eval(context);
    if (receiverEvaled.isTrue())
      return argument.eval(context);
    return receiverEvaled;
  }
}