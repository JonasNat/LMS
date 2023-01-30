package com.switchfully.lms.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "CLASS_GROUP")
public class ClassGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_class_group")
    @SequenceGenerator(name = "sequence_class_group", sequenceName = "sequence_class_group", allocationSize = 1)
    private long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COACH_ID")
    private User coach;

    @OneToMany(mappedBy = "classGroup", fetch = FetchType.LAZY)
    @OrderBy("name")
    private Set<User> students;
    @ManyToMany
    @JoinTable(
            name = "CLASS_GROUPS_COURSES",
            joinColumns = @JoinColumn(name = "CLASS_GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
    private Set<Course> courses = new LinkedHashSet<>();

    public ClassGroup(String name, User coach) {
        this.name = name;
        setCoach(coach);
    }

    protected ClassGroup() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCoach() {
        return coach;
    }

    public Set<User> getStudents() {
        return students;
    }

    public Set<Course> getCourses() {
        return Collections.unmodifiableSet(courses);
    }

    public void setCoach(User coach) {
        if (!coach.getClassGroups().contains(this)) {
            coach.add(this);
        }
        this.coach = coach;
    }

    public boolean add(User student) {
        var added = students.add(student);
        var oldClassGroup = student.getClassGroup();
        if (oldClassGroup != null && oldClassGroup != this) {
            oldClassGroup.students.remove(student);
        }
        if (this != oldClassGroup) {
            student.setClassGroup(this);
        }
        return added;
    }

    public boolean add(Course course) {
        boolean added = courses.add(course);
        if ( ! course.getClassGroups().contains(this)) {
            course.add(this);
        }
        return added;
    }

    public boolean remove(Course course) {
        boolean removed = courses.remove(course);
        if (course.getClassGroups().contains(this)) {
            course.remove(this);
        }
        return removed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassGroup that)) return false;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return name == null? 0 : name.toLowerCase().hashCode();
    }
}
