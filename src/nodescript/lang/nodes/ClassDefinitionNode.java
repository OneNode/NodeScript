package nodescript.lang.nodes;

import java.util.List;

import nodescript.lang.*;

public class ClassDefinitionNode extends Node {
  private String name;
  private String superName;
  private Node body;
  
  public ClassDefinitionNode(String name, String superName, Node body) {
    this.name = name;
    this.superName = superName;
    this.body = body;
  }
  
  public NodeScriptObject eval(Context context) throws NodeScriptException {
    NodeScriptClass klass;
    // Default superclass to Object.
    if (superName == null) {
      klass = new NodeScriptClass(name);
    } else {
      NodeScriptClass superClass = (NodeScriptClass) context.getCurrentClass().getConstant(superName);
      klass = new NodeScriptClass(name, superClass);
    }
    
    // Evaluated the body of the class with self=class and class=class.
    body.eval(new Context(klass, klass));
    // Add the class as a constant
    context.getCurrentClass().setConstant(name, klass);
    
    return klass;
  }
}