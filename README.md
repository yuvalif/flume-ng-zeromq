# flume-ng-zeromq
Custom [flume-ng](https://flume.apache.org/) source to pull data from [ZeroMQ](http://zeromq.org/). Note that it assumes PUSH/PULL message scheme.
To build use `mvn package` (note that Java 8 and [maven](https://maven.apache.org/) are needed).

## Install to flume-ng
This was tested with: flume-ng 1.8/1.7/1.6 and jermq 0.4.4/0.4.3.

The generated JAR needs to be copied to the the plugin directory: 

`<flume-ng-home>/plugins.d/flume-ng-zeromq/lib`

The jeromq (zeromq inplementation in Java) JAR should also be added there as an external dependency: 

`<flume-ng-home>/plugins.d/flume-ng-zeromq/libext`

jeromq JAR could be fetched from [here](http://central.maven.org/maven2/org/zeromq/jeromq/0.3.4/jeromq-0.3.4.jar (version 0.4.3)),
or built from [source](https://github.com/zeromq/jeromq (version 0.4.4)).

## Configuration Sample
```
a1.sources.r1.type = org.apache.flume.source.zeromq.ZeroMQSource
a1.sources.r1.port = 5555
a1.sources.r1.hostname = 127.0.0.1
```

## Test Node
> Note that it usese [vagrant](https://www.vagrantup.com/), which have to be first installed for the test node to work.

To start the test node first get into the `vagrant` directory, and then run `vagrant up`. To stop the node, run `vagrant halt` from the same place. 
Once the test node is up, you can login to it by using `vagrant ssh` (from the `vagrant` direcotry). Inside the node, use:
```
apache-flume-1.8.0-bin/bin/flume-ng agent --conf conf --conf-file example.conf --name a1 -Dflume.root.logger=DEBUG,consol -Dlog4j.configuration=file:///home/vagrant/log4j.properties
```
To execute the flume-ng agent with the zeromq configuration.

From a separate shell inside the node, run the `zmq-client.py` script in order to send data into flume.
