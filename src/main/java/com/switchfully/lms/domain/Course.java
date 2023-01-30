package com.switchfully.lms.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "COURSE")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_course")
    @SequenceGenerator(name = "sequence_course", sequenceName = "sequence_course", allocationSize = 1)
    private long id;
    private String name;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CodeLab> codeLabs = new LinkedHashSet<>();
    @ManyToMany(mappedBy = "courses")
    private Set<ClassGroup> classGroups = new LinkedHashSet<>();

    public Course(String name) {
        this.name = name;
    }

    protected Course() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<CodeLab> getCodeLabs() {
        return codeLabs;
    }

    public Set<ClassGroup> getClassGroups() {
        return Collections.unmodifiableSet(classGroups);
    }

    public boolean add(CodeLab codeLab) {
        var added = codeLabs.add(codeLab);
        var oldCourse = codeLab.getCourse();
        if (oldCourse != null && oldCourse != this) {
            oldCourse.codeLabs.remove(codeLab);
        }
        if (this != oldCourse) {
            codeLab.setCourse(this);
        }
        return added;
    }

    public boolean add(ClassGroup classGroup) {
        boolean added = classGroups.add(classGroup);
        if ( ! classGroup.getCourses().contains(this)) {
            classGroup.add(this);
        }
        return added;
    }

    public boolean remove(ClassGroup classGroup) {
        boolean removed = classGroups.remove(classGroup);
        if (classGroup.getCourses().contains(this)) {
            classGroup.remove(this);
        }
        return removed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return name.equalsIgnoreCase(course.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.toLowerCase().hashCode();
    }
}
