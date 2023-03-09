package com.kfh.service.courses.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kfh.service.courses.dto.CreateCourseRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
@JsonInclude(Include.NON_NULL)
public class Course {
  @Id
  private String id;
  @Column(name = "name")
  private String name;

  @Column(name = "created_at")
  private Instant createdAt;
  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "updated_at")
  private Instant updatedAt;
  @Column(name = "updated_by")
  private String updatedBy;

  public Course(String id, CreateCourseRequest request) {
    this.id = id;
    this.name = request.getName();
    this.createdAt = this.updatedAt = Instant.now();
    this.createdBy = this.updatedBy = request.getUserId();
  }

  public Course update(Course c) {
    return this.toBuilder()
        .name(c.getName())
        .updatedAt(Instant.now())
        .updatedBy(c.getUpdatedBy())
        .build();
  }
}
