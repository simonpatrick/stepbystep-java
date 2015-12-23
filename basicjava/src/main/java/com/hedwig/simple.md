- compile java

```sh
javac HelloWorld.java
```

- Execute Java
```sh
jva -Xmx128M -Xms16M HelloWorld
```

## First Byte Code

- getstatic/putstatic
- ldc- load constant value on stack
- invokevirtual- call a concrete instance method
- return - return from a void method


## Compile logs 
```sh
java -XX:PrintCompilation HelloWorld.java
```

## JVM JIT
- Code is interpreted first
- After some threshold JIT fires
- Classic JVM went straight to client or server
- Tiered Compiler goes to client plus profiling and later serve


## OpenJDK tooks
- JIT Watcher

## Java Features
- final fields
- synchronized and volatile: volatile: false memory visibility,access ordering
- string switch
- lambda
- single-implementer interface
- transient objects