package nodescript.lang.nodes;

import nodescript.lang.*;

public class SelfNode extends Node {
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    return context.getCurrentSelf();
  }
}