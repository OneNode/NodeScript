package nodescript.lang.nodes;

import nodescript.lang.*;

public class WhileNode extends Node {
  private Node condition;
  private Node body;
  
  public WhileNode(Node condition, Node body) {
    this.condition = condition;
    this.body = body;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    while (condition.eval(context).isTrue()) {
      body.eval(context);
    }
    return NodeScriptRuntime.getNil();
  }
}