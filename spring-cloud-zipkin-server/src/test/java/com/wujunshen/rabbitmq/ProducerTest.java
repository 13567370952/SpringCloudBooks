package com.wujunshen.rabbitmq;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/4 <br>
 * Time:下午4:55 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
public class ProducerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerTest.class);

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
            LOGGER.info("Message Number {} sent.", i);
        }
    }
}