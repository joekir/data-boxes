# data boxes

## Overview

**What is the problem?**

_I described a lot of this detail in the following Blog posts:_
* https://www.josephkirwin.com/2022/05/14/protecting-data-in-a-runtime-environment/
* https://www.josephkirwin.com/2022/05/24/part-2-protecting-data-in-a-runtime-environment/

## Implementation(s)

Trying out with Java firstly due to the ease of runtime introspection.

| Approach  | Link  | Notes   |
|---|---|---|
| Using manifold.systems to extend classes at compile time | [manifold.systems](https://github.com/manifold-systems/manifold/tree/master/manifold-deps-parent/manifold-ext#arithmetic-operators)  | It does work, though it could be a lot of maintainence to do these wrappers for all basic data classes |
| Dynamic Proxy Classes | [Core JavaSE](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html) | Only works on interfaces, stuff like [String](https://docs.oracle.com/javase/9/docs/api/java/lang/String.html) only implements `CharSequence` which is insufficient coverage |
| Javassist | [ProxyFactory](https://www.javassist.org/html/javassist/util/proxy/ProxyFactory.html) | Works on classes, not just interfaces, however cannot intercept final classes e.g.`String`, `Integer`, which is exactly what we wanted it to do. |

## Building 

```
$ bazel clean --expunge
$ bazel build //...
```

## Running

#### Running the golang authorization service
```
$ bazel run //authorizer
```

#### Running the Java client code
```
$ bazel run //java-wrappers:example
```

You'll see that the client was able to access the data and the server logs something like 
```
Starting Authorizer Service at localhost:9000
2022/08/07 13:53:32 IsAuthorized called: authContext:"foo"  dataType:"String"  verb:"READ"
```

N.B the eventual ideal (perf and security) is to use GRPC over IPC not over TCP/IP, but for now I'm saving that complexity for later!