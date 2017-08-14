package com.wujunshen.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/4 <br>
 * Time:下午4:55 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class ProducerTest {
    private QueueConsumer consumer;
    private Producer producer;

    @Before
    public void setUp() throws Exception {
        consumer = new QueueConsumer("queue");
        producer = new Producer("queue");

        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }

    @After
    public void tearDown() throws Exception {
        consumer = null;
        producer = null;
    }

    @Test
    public void sendMessage() throws Exception {
        for (int i = 0; i < 1000000; i++) {
            Map<String, Integer> message = new HashMap<>();
            message.put("message number", i);
            producer.sendMessage((Serializable) message);
            log.info("Message Number {} sent.", i);
        }
    }
}