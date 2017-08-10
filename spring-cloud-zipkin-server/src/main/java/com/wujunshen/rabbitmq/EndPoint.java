package com.wujunshen.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * User:frankwoo(吴峻申) <br>
 * Date:2017/8/4 <br>
 * Time:下午4:20 <br>
 * Mail:frank_wjs@hotmail.com <br>
 */
public class EndPoint {
    Channel channel;
    String endPointName;
    private Connection connection;

    public EndPoint(String endpointName) throws IOException, TimeoutException {
        this.endPointName = endpointName;

        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();

        //getting a connection
        connection = factory.newConnection();

        //creating a channel
        channel = connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare(endpointName, false, false, false, null);
    }
}