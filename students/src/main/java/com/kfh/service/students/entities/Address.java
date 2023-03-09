package com.kfh.service.students.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_address")
public class Address {

  @JoinColumn(name = "student_id", nullable = false)
  @OneToOne
  private Student student;
}
