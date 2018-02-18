# flume-ng-zeromq
Custom flume source to pull data from ZeroMQ

To build use `mvn package` (note that java 8 is needed). This was tested with: flume-ng 1.8, jermq 0.4.3

The generated jar needs to be copied to: `<flume-ng>/plugins.d/flume-ng-zeromq/lib`

The jeromq jar (zeromq inplementation in java) should also be added there as an external dependency: `<flume-ng>/plugins.d/flume-ng-zeromq/libext`

## Configuration Sample
```
a1.sources.r1.type = org.apache.flume.source.zeromq.ZeroMQSource
a1.sources.r1.port = 5555
a1.sources.r1.hostname = 127.0.0.1
```


