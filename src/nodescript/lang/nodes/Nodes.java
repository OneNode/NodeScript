package nodescript.lang.nodes;

import nodescript.lang.*;
import java.util.ArrayList;

/**
  Collection of nodes.
*/
public class Nodes extends Node {
  private ArrayList<Node> nodes;
  
  public Nodes() {
    nodes = new ArrayList<Node>();
  }
  
  public void add(Node n) {
    nodes.add(n);
  }
  
  /**
    Eval all the nodes and return the last returned value.
  */
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptObject lastEval = NodeScriptRuntime.getNil();
    for (Node n : nodes) {
      lastEval = n.eval(context);
    }
    return lastEval;
  }
}