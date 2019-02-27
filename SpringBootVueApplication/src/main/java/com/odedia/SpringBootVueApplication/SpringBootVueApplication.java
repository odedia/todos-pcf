package com.odedia;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class SpringBootVueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootVueApplication.class, args);
	}
}

@Entity
@Data
@NoArgsConstructor
class Todo {
	@Id
	@GeneratedValue
	private Long id;

	@NonNull
	private String title;

	private Boolean completed = false;
}

@RepositoryRestResource
interface TodoRepository extends JpaRepository<Todo, Long> {
}

@Component
class RestRepositoryConfigurator implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Todo.class);
	}
}


@Component
@Endpoint(id = "todos")
@RequiredArgsConstructor
class AccessLogActuator {
	@Autowired
	TodoRepository repo;

	@ReadOperation
	public AccessLogActuatorValues get() {
		return new AccessLogActuatorValues(repo.count());
	}
}

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class AccessLogActuatorValues {
	private final long total;
}

/**
 * Export metrics to Micrometer.
 */
@Configuration
@RequiredArgsConstructor
class AccessLogMicrometer {
	@Autowired
	TodoRepository repo;

	@Bean
	public Gauge accessLogCounter(MeterRegistry registry) {
		return Gauge.builder("todos.total", () -> repo.count()).tag("kind", "performance")
				.description("Todos total count").register(registry);
	}
}

