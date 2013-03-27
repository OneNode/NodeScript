package nodescript.lang.nodes;

import nodescript.lang.*;

/**
  Get the value of a constant.
*/
public class ConstantNode extends Node {
  private String name;
  
  public ConstantNode(String name) {
    this.name = name;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    return context.getCurrentClass().getConstant(name);
  }
}