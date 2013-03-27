package nodescript.lang.nodes;

import nodescript.lang.*;

public class LocalAssignNode extends Node {
  private String name;
  private Node expression;
  
  public LocalAssignNode(String name, Node expression) {
    this.name = name;
    this.expression = expression;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptObject value = expression.eval(context);
    context.setLocal(name, value);
    return value;
  }
}