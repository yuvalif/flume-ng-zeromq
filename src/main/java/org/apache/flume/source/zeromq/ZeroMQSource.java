package org.apache.flume.source.zeromq;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.FlumeException;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractEventDrivenSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.ZMQ;
import org.apache.flume.instrumentation.SourceCounter;

public class ZeroMQSource extends AbstractEventDrivenSource implements ZeroMQPuller {
    static final Logger LOG = LoggerFactory.getLogger(ZeroMQSource.class);
    private static final String CONF_PORT = "port";
    private static final String CONF_HOST = "hostname";
    private Integer port;
    private String hostname;
    ZMQ.Socket socket;
    ZMQ.Context context;
    private SourceCounter sourceCounter;
    //@override
    protected void doConfigure(Context context) throws FlumeException {		
        port = context.getInteger(CONF_PORT);
        // validate port number
        if (port < 1024 || port > 65535) {
            throw new IllegalArgumentException("Illegal Port Specified: "+ port);
        }
        // TODO: validate hostname
        hostname = context.getString(CONF_HOST);
        LOG.info("0MQ source successfully configured.");
        if (sourceCounter == null) {
            sourceCounter = new SourceCounter(getName());
        }
    }

    //@override
    protected void doStart() throws FlumeException {
        try {
            context = ZMQ.context(1);
            socket = context.socket(ZMQ.PULL);
            socket.bind("tcp://" + this.hostname + ":" + this.port);
            zeromqstart(socket);
            super.start();
            sourceCounter.start();
        } catch(Exception e) {
            System.out.println(e);
        }
        LOG.info("0MQ source successfully started.");
    }

    //@override
    protected void doStop() throws FlumeException {
        try {
            socket.close();
            context.term();
            sourceCounter.stop();
        } catch(Exception e) {
            System.out.println(e);
        }
        LOG.info("0MQ source successfully stopped.");
    }

    //@override
    public Status handleMessage(byte[] stringData) {
        Status status = Status.OK;	
        StringBuilder builder = new StringBuilder();
        List<Event> events = new ArrayList<Event>(1);
        builder.append(new String ( stringData ));
        events.add(EventBuilder.withBody(builder.toString().getBytes(StandardCharsets.UTF_8)));	

        sourceCounter.incrementAppendReceivedCount();
        sourceCounter.incrementEventReceivedCount();

        try {
            getChannelProcessor().processEventBatch(events);
            status = Status.OK;
        } catch (Exception excep) {
            status = Status.FAILED;
        }

        sourceCounter.incrementAppendAcceptedCount();
        sourceCounter.incrementEventAcceptedCount();

        return status;
    }
}

