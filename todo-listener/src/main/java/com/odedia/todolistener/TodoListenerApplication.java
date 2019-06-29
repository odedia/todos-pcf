package com.odedia.todolistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
public class TodoListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListenerApplication.class, args);
	}

}

@EnableBinding(Sink.class)
@Slf4j
class CoffeeDrinker {

    @StreamListener(value = Sink.INPUT)
    private void completedEvent(String completedTask) {
        log.info("Received a completed task: {}", completedTask);
    }

}