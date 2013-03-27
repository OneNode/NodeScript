package nodescript.lang.nodes;

import nodescript.lang.*;

public class ConstantAssignNode extends Node {
  private String name;
  private Node expression;
  
  public ConstantAssignNode(String name, Node expression) {
    this.name = name;
    this.expression = expression;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptObject value = expression.eval(context);
    context.getCurrentClass().setConstant(name, value);
    return value;
  }
}