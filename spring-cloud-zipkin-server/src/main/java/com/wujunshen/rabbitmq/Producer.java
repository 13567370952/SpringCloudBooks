package com.wujunshen.rabbitmq;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;


/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/4 <br>
 * Time:下午4:21 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
public class Producer extends EndPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    public Producer(String endPointName) throws IOException, TimeoutException {
        super(endPointName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicPublish("", endPointName, null, SerializationUtils.serialize(object));
    }
}