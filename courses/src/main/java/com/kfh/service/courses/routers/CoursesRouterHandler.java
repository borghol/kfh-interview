package com.kfh.service.courses.routers;

import java.util.Optional;

import com.kfh.service.courses.dto.CreateCourseRequest;
import com.kfh.service.courses.entities.Course;
import com.kfh.service.courses.repositories.CourseRepository;
import com.kfh.service.courses.routers.RequestValidator.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Service
public class CoursesRouterHandler {

  private final CourseRepository repository;
  private final RequestValidator validator;
  private final Logger LOG = LoggerFactory.getLogger(this.getClass());

  public CoursesRouterHandler(CourseRepository repository, RequestValidator validator) {
    this.repository = repository;
    this.validator = validator;
  }

  public Mono<ServerResponse> saveCourse(ServerRequest request) {
    String courseId = request.pathVariable(CoursesRouter.COURSE_ID);
    return validator.validate(request, CreateCourseRequest.class)
        .map(createRequest -> new Course(courseId, createRequest))
        .map(course -> {
          Optional<Course> savedCourse = repository.findById(courseId);
          return savedCourse
              .map(c -> c.update(course))
              .orElse(course);
        })
        .map(repository::save)
        .then(ServerResponse.ok().build())
        .onErrorResume(ValidationException.class, e -> ServerResponse.status(e.getStatus())
            .bodyValue(e.getReason()))
        .doOnError(err -> LOG.error("Failed to save course: {}", courseId, err));
  }

  public Mono<ServerResponse> deleteCourse(ServerRequest request) {
    String courseId = request.pathVariable(CoursesRouter.COURSE_ID);
    return Mono.just(courseId)
        .map(id -> {
          repository.deleteById(courseId);
          return id;
        })
        .then(ServerResponse.ok().build())
        .doOnError(err -> LOG.error("Failed to delete course: {}", courseId, err));
  }

  public Mono<ServerResponse> getCourse(ServerRequest request) {
    String courseId = request.pathVariable(CoursesRouter.COURSE_ID);
    return Mono.just(repository.findById(courseId))
        .flatMap(course -> ServerResponse.ok().bodyValue(course))
        .switchIfEmpty(ServerResponse.notFound().build())
        .doOnError(err -> LOG.error("Failed to get course: {}", courseId, err));
  }

  public Mono<ServerResponse> getCourses(ServerRequest request) {
    return Mono.just(repository.findAll())
        .flatMap(course -> ServerResponse.ok().bodyValue(course))
        .switchIfEmpty(ServerResponse.notFound().build())
        .doOnError(err -> LOG.error("Failed to get courses", err));
  }
}
