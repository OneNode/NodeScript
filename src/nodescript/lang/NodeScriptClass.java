package nodescript.lang;

import java.util.HashMap;

/**
  Class in the runtime.
  Classes store methods and constants. Each object in the runtime has a class.
*/
public class NodeScriptClass extends NodeScriptObject {
  private String name;
  private NodeScriptClass superClass;
  private HashMap<String, Method> methods;
  HashMap<String, NodeScriptObject> constants;
  
  /**
    Creates a class inheriting from superClass.
  */
  public NodeScriptClass(String name, NodeScriptClass superClass) {
    super("Class");
    this.name = name;
    this.superClass = superClass;
    methods = new HashMap<String, Method>();
    constants = new HashMap<String, NodeScriptObject>();
  }
  
  /**
    Creates a class inheriting from Object.
  */
  public NodeScriptClass(String name) {
    this(name, NodeScriptRuntime.getObjectClass());
  }
  
  public String getName() {
    return name;
  }
  
  public NodeScriptClass getSuperClass() {
    return superClass;
  }
  
  public void setConstant(String name, NodeScriptObject value) {
    constants.put(name, value);
  }

  public NodeScriptObject getConstant(String name) {
    if (constants.containsKey(name)) return constants.get(name);
    if (superClass != null) return superClass.getConstant(name);
    return NodeScriptRuntime.getNil();
  }
  
  public boolean hasConstant(String name) {
    if (constants.containsKey(name)) return true;
    if (superClass != null) return superClass.hasConstant(name);
    return false;
  }
  
  public Method lookup(String name) throws MethodNotFound {
    if (methods.containsKey(name)) return methods.get(name);
    if (superClass != null) return superClass.lookup(name);
    throw new MethodNotFound(name);
  }

  public boolean hasMethod(String name) {
    if (methods.containsKey(name)) return true;
    if (superClass != null) return superClass.hasMethod(name);
    return false;
  }
  
  public void addMethod(String name, Method method) {
    methods.put(name, method);
  }
  
  /**
    Creates a new instance of the class.
  */
  public NodeScriptObject newInstance() {
    return new NodeScriptObject(this);
  }
  
  /**
    Creates a new instance of the class, storing the value inside a ValueObject.
  */
  public NodeScriptObject newInstance(Object value) {
    return new ValueObject(this, value);
  }
  
  /**
    Creates a new subclass of this class.
  */
  public NodeScriptClass newSubclass(String name) {
    NodeScriptClass klass = new NodeScriptClass(name, this);
    NodeScriptRuntime.getObjectClass().setConstant(name, klass);
    return klass;
  }
  
  /**
    Creates or returns a subclass if it already exists.
  */
  public NodeScriptClass subclass(String name) {
    NodeScriptClass objectClass = NodeScriptRuntime.getObjectClass();
    if (objectClass.hasConstant(name)) return (NodeScriptClass) objectClass.getConstant(name);
    return newSubclass(name);
  }
  
  /**
    Returns true if klass is a subclass of this class.
  */
  public boolean isSubclass(NodeScriptClass klass) {
    if (klass == this) return true;
    if (klass.getSuperClass() == null) return false;
    if (klass.getSuperClass() == this) return true;
    return isSubclass(klass.getSuperClass());
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if ( !(other instanceof NodeScriptClass) ) return false;
    return name == ((NodeScriptClass)other).getName();
  }
}
