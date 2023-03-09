package com.kfh.service.courses.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CoursesRouter {

  public static final String COURSE_ID = "id";
  public static final String COURSE_ID_ROUTE = "/{%s}".formatted(COURSE_ID);

  private final CoursesRouterHandler routerHandler;

  public CoursesRouter(CoursesRouterHandler routerHandler) {
    this.routerHandler = routerHandler;
  }

  @Bean
  public RouterFunction<ServerResponse> courseRoutes() {
    return RouterFunctions
      .route(PUT(COURSE_ID_ROUTE).and(accept(MediaType.APPLICATION_JSON)), routerHandler::saveCourse)
      .andRoute(GET(COURSE_ID_ROUTE), routerHandler::getCourse)
      .andRoute(GET("/"), routerHandler::getCourses)
      .andRoute(DELETE(COURSE_ID_ROUTE).and(accept(MediaType.APPLICATION_JSON)), routerHandler::deleteCourse);
  }
}
