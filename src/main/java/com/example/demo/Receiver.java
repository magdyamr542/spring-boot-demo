package com.example.demo;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Component;

@Component
public class Receiver implements MessageListener {

  private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }

  @Override
  public void onMessage(Message message) {
    String theMessage = new String(message.getBody());
    MessageProperties props = message.getMessageProperties();
    props.getConsumerQueue();

    logger.info("-----");
    logger.info("Recv msg:" + theMessage);
    logger.info("-----");
    latch.countDown();
  }
}
