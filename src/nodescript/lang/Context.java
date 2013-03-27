package nodescript.lang;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import nodescript.lang.nodes.Node;


/**
  Evaluation context. Determines how a node will be evaluated.
  A context tracks local variables, self, and the current class under which
  methods and constants will be added.
  
  There are three different types of context:
  1) In the root of the script, self = main object, class = Object
  2) Inside a method body, self = instance of the class, class = method class
  3) Inside a class definition self = the class, class = the class
*/
public class Context {
  private NodeScriptObject currentSelf;
  private NodeScriptClass currentClass;
  private HashMap<String, NodeScriptObject> locals;
  // A context can share local variables with a parent. For example, in the
  // try block.
  private Context parent;
  
  public Context(NodeScriptObject currentSelf, NodeScriptClass currentClass, Context parent) {
    this.currentSelf = currentSelf;
    this.currentClass = currentClass;
    this.parent = parent;
    if (parent == null) {
      locals = new HashMap<String, NodeScriptObject>();
    } else {
      locals = parent.locals;
    }
  }
  
  public Context(NodeScriptObject currentSelf, NodeScriptClass currentClass) {
    this(currentSelf, currentClass, null);
  }
  
  public Context(NodeScriptObject currentSelf) {
    this(currentSelf, currentSelf.getNodeScriptClass());
  }
  
  public Context() {
    this(NodeScriptRuntime.getMainObject());
  }
  
  public NodeScriptObject getCurrentSelf() {
    return currentSelf;
  }

  public NodeScriptClass getCurrentClass() {
    return currentClass;
  }
  
  public NodeScriptObject getLocal(String name) {
    return locals.get(name);
  }
    
  public boolean hasLocal(String name) {
    return locals.containsKey(name);
  }
    
  public void setLocal(String name, NodeScriptObject value) {
    locals.put(name, value);
  }
  
  /**
    Creates a context that will share the same attributes (locals, self, class)
    as the current one.
  */
  public Context makeChildContext() {
    return new Context(currentSelf, currentClass, this);
  }
  
  /**
    Parse and evaluate the content red from the reader (eg.: FileReader, StringReader).
  */
  public NodeScriptObject eval(Reader reader) throws NodeScriptException {
    try {
      NodeScriptLexer lexer = new NodeScriptLexer(new ANTLRReaderStream(reader));
      NodeScriptParser parser = new NodeScriptParser(new CommonTokenStream(lexer));
      Node node = parser.parse();
      if (node == null) return NodeScriptRuntime.getNil();
      return node.eval(this);
    } catch (NodeScriptException e) {
      throw e;
    } catch (Exception e) {
      throw new NodeScriptException(e);
    }
  }
  
  public NodeScriptObject eval(String code) throws NodeScriptException {
    return eval(new StringReader(code));
  }
}
