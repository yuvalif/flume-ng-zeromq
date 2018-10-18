# based on: https://learning-0mq-with-pyzmq.readthedocs.io/en/latest/pyzmq/patterns/pushpull.html
# this is just used to test the zmq-client.py file, not needed for testing flume

import time
import zmq
import random

def consumer():
    consumer_id = random.randrange(1,10005)
    print "I am consumer #%s" % (consumer_id)
    context = zmq.Context()
    # recieve work
    consumer_receiver = context.socket(zmq.PULL)
    consumer_receiver.connect("tcp://127.0.0.1:5555")
   
    while True:
        work = consumer_receiver.recv_json()
        data = work['num']
        result = { 'consumer' : consumer_id, 'num' : data}
        print(result)

consumer()

