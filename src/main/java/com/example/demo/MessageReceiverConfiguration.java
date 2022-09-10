package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MessageReceiverConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(
    LoadDatabase.class
  );

  private final String queueName = "demo-queue";
  private final String topicExchangeName = "demo-exchange";
  private final String directExchangeName = "direct";

  @Autowired
  RabbitTemplate rabbitTemplate;

  @Bean
  Queue queue() {
    logger.info("Creating a queue" + queueName);
    return new Queue(queueName, true);
  }

  @Bean
  TopicExchange exchange() {
    logger.info("Creating a topic exchange" + topicExchangeName);
    return new TopicExchange(topicExchangeName);
  }

  @Bean
  DirectExchange directExchange() {
    logger.info("Creating a direct exchange" + directExchangeName);
    return new DirectExchange(directExchangeName);
  }

  @Bean
  public Declarables bindings(
    Queue queue,
    TopicExchange topicExch,
    DirectExchange directExch
  ) {
    Binding foobar = BindingBuilder.bind(queue).to(topicExch).with("foo.bar.#");
    Binding foo = BindingBuilder.bind(queue).to(topicExch).with("foo.#");
    Binding direct = BindingBuilder.bind(queue).to(directExch).with("foo");
    return new Declarables(foobar, foo, direct);
  }

  @Bean
  SimpleMessageListenerContainer container(
    ConnectionFactory connectionFactory
  ) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setMessageListener(new Receiver());

    return container;
  }
}
