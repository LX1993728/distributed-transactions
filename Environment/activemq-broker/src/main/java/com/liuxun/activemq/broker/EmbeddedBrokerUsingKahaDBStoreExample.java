package com.liuxun.activemq.broker;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.kahadb.KahaDBStore;

import java.io.File;

/**
 * 使用kahaDB 来进行消息的持久化存储
 */
public class EmbeddedBrokerUsingKahaDBStoreExample {
    BrokerService createEmbeddedBroker() throws Exception{
        BrokerService broker = new BrokerService();
        File dataFileDir = new File("target/amq-in-action/kahadb");
        KahaDBStore kaha = new KahaDBStore();
        kaha.setDirectory(dataFileDir);
        // Using a bigger journal file
        kaha.setJournalMaxFileLength(1024*100);
        // small batch means more frequent and smaller writes 小批量意味着更加频繁和更小的写入
        kaha.setIndexWriteBatchSize(100);
        // do the index write in a separate thread
        kaha.setEnableIndexWriteAsync(true);
        broker.setPersistenceAdapter(kaha);
        // create a transport connector
        broker.addConnector("tcp://localhost:61616");
        // start the broker;
        broker.start();
        return broker;
    }

    public static void main(String[] args) throws Exception{
        new EmbeddedBrokerUsingKahaDBStoreExample().createEmbeddedBroker();
    }
}
