package liuxun.zoo.server;

import org.apache.curator.test.TestingServer;

import java.io.File;

/**
 * @apiNote 单机版zookeeper测试服务器 暂时为dubbo提供注册中心支撑
 * @author liuxun
 */
public class CuratorZooServer {
    public static void main(String[] args) throws Exception{
        new TestingServer(2181,new File("./data")).start();
    }
}
