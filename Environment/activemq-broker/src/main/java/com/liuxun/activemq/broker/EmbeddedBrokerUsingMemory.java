package com.liuxun.activemq.broker;

import org.apache.activemq.broker.BrokerService;

public class EmbeddedBrokerUsingMemory {
    public void createEmbeddedMemoryBroker() throws Exception{
        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }

    public static void main(String[] ags) throws Exception{
        new EmbeddedBrokerUsingMemory().createEmbeddedMemoryBroker();
    }
}
