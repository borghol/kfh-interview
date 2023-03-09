// package com.kfh.service.courses;

// import java.util.HashMap;
// import java.util.Map;
// import java.util.Optional;

// import com.kfh.service.courses.dto.CreateCourseRequest;
// import com.kfh.service.courses.entities.Course;
// import com.kfh.service.courses.repositories.CourseRepository;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.reactive.server.WebTestClient;

// @SpringBootTest
// @AutoConfigureWebTestClient
// public class CourseRouterTests {

//   @MockBean
//   private CourseRepository courseRepository;

//   @Autowired
//   public WebTestClient webTestClient;

//   private Map<String, Course> courses = new HashMap<>();

//   @BeforeEach
//   public void beforeEach() {
//     Mockito.when(courseRepository.save(Mockito.any(Course.class)))
//         .then(i -> {
//           Course c = (Course) i.getArgument(0);
//           courses.put(c.getId(), c);
//           return c;
//         });

//     Mockito.when(courseRepository.findById(Mockito.any(String.class)))
//         .then(i -> {
//           String id = (String) i.getArgument(0);
//           return Optional.ofNullable(courses.get(id));
//         });

//     Mockito.doAnswer(i -> {
//       String id = (String) i.getArgument(0);
//       courses.remove(id);
//       return id;
//     }).when(courseRepository).deleteById(Mockito.any(String.class));
//   }


//   @Test
//   void itCreatesACourse() {
//     CreateCourseRequest request = CreateCourseRequest.builder()
//       .name("MAT101")
//       .userId("USERID")
//       .build();

//     webTestClient.put()
//       .uri("/MAT101")
//       .bodyValue(request)
//       .exchange()
//       .expectStatus()
//       .isOk();

//     webTestClient
//       .get()
//       .uri("/MAT101")
//       .exchange()
//       .expectStatus()
//       .isOk()
//       .expectBody(Course.class)
//       .value(c -> {
//         Assertions.assertEquals(request.getName(), c.getName());
//       });
//   }

// }
