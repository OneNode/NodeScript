package nodescript.lang.nodes;

import nodescript.lang.*;

public class InstanceVariableAssignNode extends Node {
  private String name;
  private Node expression;
  
  public InstanceVariableAssignNode(String name, Node expression) {
    this.name = name;
    this.expression = expression;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptObject value = expression.eval(context);
    context.getCurrentSelf().setInstanceVariable(name, value);
    return value;
  }
}