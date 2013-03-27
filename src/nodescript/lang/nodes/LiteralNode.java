package nodescript.lang.nodes;

import nodescript.lang.*;

public class LiteralNode extends Node {
  NodeScriptObject value;
  
  public LiteralNode(NodeScriptObject value) {
    this.value = value;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    return value;
  }
}