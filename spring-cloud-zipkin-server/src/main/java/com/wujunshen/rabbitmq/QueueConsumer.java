package com.wujunshen.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/4 <br>
 * Time:下午4:29 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
@Slf4j
public class QueueConsumer extends EndPoint implements Runnable, Consumer {
    QueueConsumer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true, this);
        } catch (IOException e) {
            log.error("exception message is: {}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        log.info("Consumer {} registered", consumerTag);
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        Map map = SerializationUtils.deserialize(body);
        log.info("Message Number {} received.", map.get("message number"));

    }

    @Override
    public void handleCancel(String consumerTag) {
        log.info("Consumer {} cancel", consumerTag);
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        log.info("Consumer {} cancel", consumerTag);
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        log.info("Consumer {} recover", consumerTag);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        log.info("Consumer {},{} shutdown", consumerTag, sig);
    }
}