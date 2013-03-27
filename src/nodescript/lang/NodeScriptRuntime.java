package nodescript.lang;

/**
  Language runtime. Mostly helper methods for retrieving global values.
*/
public class NodeScriptRuntime {
  static NodeScriptClass objectClass;
  static NodeScriptObject mainObject;
  static NodeScriptObject nilObject;
  static NodeScriptObject trueObject;
  static NodeScriptObject falseObject;
  
  public static NodeScriptClass getObjectClass() {
    return objectClass;
  }

  public static NodeScriptObject getMainObject() {
    return mainObject;
  }

  public static NodeScriptClass getRootClass(String name) {
    // objectClass is null when boostrapping
    return objectClass == null ? null : (NodeScriptClass) objectClass.getConstant(name);
  }

  public static NodeScriptClass getExceptionClass() {
    return getRootClass("Exception");
  }
  
  public static NodeScriptObject getNil() {
    return nilObject;
  }
  
  public static NodeScriptObject getTrue() {
    return trueObject;
  }

  public static NodeScriptObject getFalse() {
    return falseObject;
  }
  
  public static NodeScriptObject toBoolean(boolean value) {
    return value ? NodeScriptRuntime.getTrue() : NodeScriptRuntime.getFalse();
  }
}
