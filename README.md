# Custom flume source to pull data from ZeroMQ

To build use <pre>mvn package</pre> (note that java 8 is needed). This was tested with: flume-ng 1.8, jermq 0.4.3

The generated jar needs to be copied as a flume-ng plugin: <pre><flume-mg>/plugins.d/flume-ng-zeromq/lib</pre>

The jeromq jar (zeromq inplementation in java) should also be added there as an external dependency: <pre><flume-mg>/plugins.d/flume-ng-zeromq/lib</pre>

## Configuration Sample
a1.sources.r1.type = org.apache.flume.source.zeromq.ZeroMQSource
a1.sources.r1.port = 5555
a1.sources.r1.hostname = 127.0.0.1


