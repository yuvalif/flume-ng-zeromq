# flume-ng-zeromq
Custom flume-ng source to pull data from ZeroMQ. Note that it assumes PUSH/PULL message scheme.

To build use `mvn package` (note that Java 8 is needed). 

## Install to flume-ng
This was tested with: flume-ng 1.8/1.7/1.6, , jermq 0.4.4. 

The generated JAR needs to be copied to the the plugin directory: 

`<flume-ng-home>/plugins.d/flume-ng-zeromq/lib`

The jeromq (zeromq inplementation in Java) JAR should also be added there as an external dependency: 

`<flume-ng-home>/plugins.d/flume-ng-zeromq/libext`

jeromq JAR could be fetched from here: http://central.maven.org/maven2/org/zeromq/jeromq/0.3.4/jeromq-0.3.4.jar,
or built from source https://github.com/zeromq/jeromq (version 0.4.4).

## Configuration Sample
```
a1.sources.r1.type = org.apache.flume.source.zeromq.ZeroMQSource
a1.sources.r1.port = 5555
a1.sources.r1.hostname = 127.0.0.1
```
