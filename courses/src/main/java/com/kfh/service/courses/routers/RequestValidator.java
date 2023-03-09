package com.kfh.service.courses.routers;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

@Component
public class RequestValidator {

  private final Validator validator;

  public RequestValidator(Validator validator) {
    this.validator = validator;
  }

  public <T> Mono<T> validate(ServerRequest request, Class<T> bodyClass) {
    return request
        .bodyToMono(bodyClass)
        .switchIfEmpty(Mono.error(new ValidationException()))
        .map(body -> {
          Set<ConstraintViolation<T>> violations = validator.validate(body);
          if (!violations.isEmpty()) {
            throw new ValidationException(violations.toString());
          }
          return body;
        });
  }

  public static class ValidationException extends ResponseStatusException {
    public ValidationException() {
      super(HttpStatus.BAD_REQUEST, "Body must not be empty");
    }

    public ValidationException(String violations) {
      super(HttpStatus.BAD_REQUEST, violations);
    }
  }
}
