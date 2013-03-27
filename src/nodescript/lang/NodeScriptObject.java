package nodescript.lang;

import java.util.HashMap;

/**
  Any object, instance of a class, inside the runtime.
  Objects store a class and instance variables.
*/
public class NodeScriptObject {
  private NodeScriptClass yourLangClass;
  private HashMap<String, NodeScriptObject> instanceVariables;
  
  /**
    Creates an instance of class yourLangClass.
  */
  public NodeScriptObject(NodeScriptClass yourLangClass) {
    this.yourLangClass = yourLangClass;
    this.instanceVariables = new HashMap<String, NodeScriptObject>();
  }
  
  public NodeScriptObject(String className) {
    this(NodeScriptRuntime.getRootClass(className));
  }
  
  public NodeScriptObject() {
    this(NodeScriptRuntime.getObjectClass());
  }
  
  public NodeScriptClass getNodeScriptClass() {
    return yourLangClass;
  }
  
  public void setNodeScriptClass(NodeScriptClass klass) {
    yourLangClass = klass;
  }
  
  public NodeScriptObject getInstanceVariable(String name) {
    if (hasInstanceVariable(name))
      return instanceVariables.get(name);
    return NodeScriptRuntime.getNil();
  }

  public boolean hasInstanceVariable(String name) {
    return instanceVariables.containsKey(name);
  }
  
  public void setInstanceVariable(String name, NodeScriptObject value) {
    instanceVariables.put(name, value);
  }
  
  /**
    Call a method on the object.
  */
  public NodeScriptObject call(String method, NodeScriptObject arguments[]) throws NodeScriptException {
    return yourLangClass.lookup(method).call(this, arguments);
  }

  public NodeScriptObject call(String method) throws NodeScriptException {
    return call(method, new NodeScriptObject[0]);
  }
  
  /**
    Only false and nil are not true.
  */
  public boolean isTrue() {
    return !isFalse();
  }
  
  /**
    Only false and nil are false. This is overridden in ValueObject.
  */
  public boolean isFalse() {
    return false;
  }

  /**
    Only nil is nil. This is overridden in ValueObject.
  */
  public boolean isNil() {
    return false;
  }
  
  /**
    Convert to a Java object. This is overridden in ValueObject.
  */
  public Object toJavaObject() {
    return this;
  }
  
  public <T> T as(Class<T> clazz) throws TypeError {
    if (clazz.isInstance(this)){
      return clazz.cast(this);
    }
    throw new TypeError(clazz.getName(), this);
  }
  
  public String asString() throws TypeError {
    return as(ValueObject.class).getValueAs(String.class);
  }

  public Integer asInteger() throws TypeError {
    return as(ValueObject.class).getValueAs(Integer.class);
  }

  public Float asFloat() throws TypeError {
    return as(ValueObject.class).getValueAs(Float.class);
  }
}
