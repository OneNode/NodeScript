package nodescript.lang;

import java.io.*;

/**
  Bootstrapper.run() is called to initialize the runtime.
  Core classes are created and methods are added.
*/
public class Bootstrapper {
  static public Context run() {
    // Create core classes
    NodeScriptClass objectClass = new NodeScriptClass("Object");
    NodeScriptRuntime.objectClass = objectClass;
    // Each method sent or added on the root context of a script are evaled on the main object.
    NodeScriptObject main = new NodeScriptObject();
    NodeScriptRuntime.mainObject = main;
    NodeScriptClass classClass = new NodeScriptClass("Class");
    objectClass.setNodeScriptClass(classClass); // Object is a class
    classClass.setNodeScriptClass(classClass); // Class is a class
    main.setNodeScriptClass(objectClass);
    
    // Register core classes into the root context
    objectClass.setConstant("Object", objectClass);
    objectClass.setConstant("Class", classClass);
    // There is just one instance of nil, true, false, so we store those in constants.
    NodeScriptRuntime.nilObject = objectClass.newSubclass("NilClass").newInstance(null);
    NodeScriptRuntime.trueObject = objectClass.newSubclass("TrueClass").newInstance(true);
    NodeScriptRuntime.falseObject = objectClass.newSubclass("FalseClass").newInstance(false);
    NodeScriptClass stringClass = objectClass.newSubclass("String");
    NodeScriptClass numberClass = objectClass.newSubclass("Number");
    NodeScriptClass integerClass = numberClass.newSubclass("Integer");
    NodeScriptClass floatClass = numberClass.newSubclass("Float");
    NodeScriptClass exceptionClass = objectClass.newSubclass("Exception");
    exceptionClass.newSubclass("IOException");
    exceptionClass.newSubclass("TypeError");
    exceptionClass.newSubclass("MethodNotFound");
    exceptionClass.newSubclass("ArgumentError");
    exceptionClass.newSubclass("FileNotFound");
    
    // Add methods to core classes.
    
    //// Object
    objectClass.addMethod("print", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        for (NodeScriptObject arg : arguments) System.out.println(arg.toJavaObject());
        return NodeScriptRuntime.getNil();
      }
    });
    objectClass.addMethod("class", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        return receiver.getNodeScriptClass();
      }
    });
    objectClass.addMethod("eval", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        Context context = new Context(receiver);
        String code = arguments[0].asString();
        return context.eval(code);
      }
    });
    objectClass.addMethod("require", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        Context context = new Context();
        String filename = arguments[0].asString();
        try {
          return context.eval(new FileReader(filename));
        } catch (FileNotFoundException e) {
          throw new NodeScriptException("FileNotFound", "File not found: " + filename);
        }
      }
    });
    
    //// Class
    classClass.addMethod("new", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        NodeScriptClass self = (NodeScriptClass) receiver;
        NodeScriptObject instance = self.newInstance();
        if (self.hasMethod("initialize")) instance.call("initialize", arguments);
        return instance;
      }
    });
    classClass.addMethod("name", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        NodeScriptClass self = (NodeScriptClass) receiver;
        return new ValueObject(self.getName());
      }
    });
    classClass.addMethod("superclass", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        NodeScriptClass self = (NodeScriptClass) receiver;
        return self.getSuperClass();
      }
    });

    //// Exception
    exceptionClass.addMethod("initialize", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        if (arguments.length == 1) receiver.setInstanceVariable("message", arguments[0]);
        return NodeScriptRuntime.getNil();
      }
    });
    exceptionClass.addMethod("message", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        return receiver.getInstanceVariable("message");
      }
    });
    objectClass.addMethod("raise!", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        String message = null;
        if (receiver.hasInstanceVariable("message")) message = receiver.getInstanceVariable("message").asString();
        throw new NodeScriptException(receiver.getNodeScriptClass(), message);
      }
    });
    
    //// Integer
    integerClass.addMethod("+", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return new ValueObject(receiver + argument);
      }
    });
    integerClass.addMethod("-", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return new ValueObject(receiver + argument);
      }
    });
    integerClass.addMethod("*", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return new ValueObject(receiver * argument);
      }
    });
    integerClass.addMethod("/", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return new ValueObject(receiver / argument);
      }
    });
    integerClass.addMethod("<", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return NodeScriptRuntime.toBoolean(receiver < argument);
      }
    });
    integerClass.addMethod(">", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return NodeScriptRuntime.toBoolean(receiver > argument);
      }
    });
    integerClass.addMethod("<=", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return NodeScriptRuntime.toBoolean(receiver <= argument);
      }
    });
    integerClass.addMethod(">=", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return NodeScriptRuntime.toBoolean(receiver >= argument);
      }
    });
    integerClass.addMethod("==", new OperatorMethod<Integer>() {
      public NodeScriptObject perform(Integer receiver, Integer argument) throws NodeScriptException {
        return NodeScriptRuntime.toBoolean(receiver == argument);
      }
    });
    
    //// String
    stringClass.addMethod("+", new OperatorMethod<String>() {
      public NodeScriptObject perform(String receiver, String argument) throws NodeScriptException {
        return new ValueObject(receiver + argument);
      }
    });
    stringClass.addMethod("size", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        String self = receiver.asString();
        return new ValueObject(self.length());
      }
    });
    stringClass.addMethod("substring", new Method() {
      public NodeScriptObject call(NodeScriptObject receiver, NodeScriptObject arguments[]) throws NodeScriptException {
        String self = receiver.asString();
        if (arguments.length == 0) throw new ArgumentError("substring", 1, 0);
        int start = arguments[0].asInteger();
        int end = self.length();
        if (arguments.length > 1) end = arguments[1].asInteger();
        return new ValueObject(self.substring(start, end));
      }
    });
    
    // Return the root context on which everything will be evaled. By default, everything is evaled on the
    // main object.
    return new Context(main);
  }
}