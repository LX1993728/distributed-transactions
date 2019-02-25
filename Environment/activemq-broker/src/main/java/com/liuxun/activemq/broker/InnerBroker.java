package com.liuxun.activemq.broker;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;

/**
 * @author liuxun
 * 嵌入的broker
 */
public class InnerBroker {
    public static void broker1() throws Exception{
        final BrokerService broker = new BrokerService();
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }

    public static void broker2() throws Exception{
        String uri = "properties:broker.properties";
        final BrokerService broker= BrokerFactory.createBroker(new URI(uri));
        broker.setUseJmx(true);
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }

    public static void main(String[] args) throws Exception{
//        broker1();
        broker2();
    }
}
