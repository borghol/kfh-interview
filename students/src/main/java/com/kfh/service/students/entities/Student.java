package com.kfh.service.students.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

  @Id
  @Column(name = "student_id")
  private String id;

  @OneToMany(mappedBy = "student")
  private Set<StudentName> names;

  @Column
  private String email;

  @OneToOne(mappedBy = "student")
  private Address address;

}
