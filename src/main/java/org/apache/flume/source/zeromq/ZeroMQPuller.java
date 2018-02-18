package org.apache.flume.source.zeromq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.zeromq.ZMQ;

public interface ZeroMQPuller {
		
	enum Status {
		OK,
		FAILED
	}
	
	public default void zeromqstart(ZMQ.Socket socket)
	{
		 ExecutorService executorService = null;
		 try
	     {
	           executorService = Executors.newFixedThreadPool(2);

	           while (!Thread.currentThread().isInterrupted())
	           {
	                handleMessage(socket.recv(0));
	           }
	     }
	     catch (Exception e)
	     {
	         System.out.println(e);
	     }
		 finally
		 {
			 executorService.shutdown();
		 }
	}
	
	public Status handleMessage(byte[] stringData);
}

