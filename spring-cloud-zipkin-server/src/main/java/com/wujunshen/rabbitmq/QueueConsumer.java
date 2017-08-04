package com.wujunshen.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/4 <br>
 * Time:下午4:29 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
public class QueueConsumer extends EndPoint implements Runnable, Consumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueConsumer.class);

    QueueConsumer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    public void run() {
        try {
            //start consuming messages. Auto acknowledge messages.
            channel.basicConsume(endPointName, true, this);
        } catch (IOException e) {
            LOGGER.error("error is {}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Called when consumer is registered.
     */
    public void handleConsumeOk(String consumerTag) {
        LOGGER.info("Consumer {} registered", consumerTag);
    }

    /**
     * Called when new message is available.
     */
    public void handleDelivery(String consumerTag, Envelope env,
                               BasicProperties props, byte[] body) throws IOException {
        Map map = SerializationUtils.deserialize(body);
        LOGGER.info("Message Number {} received.", map.get("message number"));

    }

    @Override
    public void handleCancel(String consumerTag) {
        LOGGER.info("Consumer {} cancel", consumerTag);
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        LOGGER.info("Consumer {} cancel", consumerTag);
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        LOGGER.info("Consumer {} recover", consumerTag);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        LOGGER.info("Consumer {},{} shutdown", consumerTag, sig);
    }
}