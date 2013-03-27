package nodescript.lang.nodes;

import nodescript.lang.*;

public class InstanceVariableNode extends Node {
  private String name;
  
  public InstanceVariableNode(String name) {
    this.name = name;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    return context.getCurrentSelf().getInstanceVariable(name);
  }
}