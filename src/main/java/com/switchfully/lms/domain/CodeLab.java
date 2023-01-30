package com.switchfully.lms.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CODE_LAB")
public class CodeLab {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_code_lab")
    @SequenceGenerator(name = "sequence_code_lab", sequenceName = "code_lab", allocationSize = 1)
    private long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID")
    private Course course;
    @ManyToMany(mappedBy = "codeLabs")
    private Set<User> students = new LinkedHashSet<>();


    public CodeLab(String name, Course course) {
        this.name = name;
        setCourse(course);
    }

    protected CodeLab() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Course getCourse() {
        return course;
    }

    public Set<User> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public void setCourse(Course course) {
        if (!course.getCodeLabs().contains(this)) {
            course.add(this);
        }
        this.course = course;
    }

    public boolean add(User student) {
        boolean added = students.add(student);
        if ( ! student.getCodeLabs().contains(this)) {
            student.add(this);
        }
        return added;
    }

    public boolean remove(User student) {
        boolean removed = students.remove(student);
        if (student.getCodeLabs().contains(this)) {
            student.remove(this);
        }
        return removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeLab codeLab)) return false;
        return name.equalsIgnoreCase(codeLab.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.toLowerCase().hashCode();
    }
}
