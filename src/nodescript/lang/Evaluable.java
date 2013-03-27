package nodescript.lang;

import nodescript.lang.nodes.Node;

/**
  Anything that can be evaluated inside a context must implement this interface.
*/
public interface Evaluable {
  NodeScriptObject eval(Context context) throws NodeScriptException;
}
