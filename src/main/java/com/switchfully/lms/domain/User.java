package com.switchfully.lms.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_user")
    @SequenceGenerator(name = "sequence_user", sequenceName = "sequence_user", allocationSize = 1)
    private long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASS_GROUP_ID")
    private ClassGroup classGroup;

    @OneToMany(mappedBy = "coach")
    private Set<ClassGroup> classGroups;


    @ManyToMany
    @JoinTable(
            name = "STUDENT_CODE_LAB",
            joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CODE_LAB_ID"))
    private Set<CodeLab> codeLabs = new LinkedHashSet<>();

    public User(String name, String email, Role role, ClassGroup classGroup) {
        this.name = name;
        this.email = email;
        this.role = role;
        setClassGroup(classGroup);
        this.classGroups = new LinkedHashSet<>();
    }

    protected User() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public Set<CodeLab> getCodeLabs() {
        return Collections.unmodifiableSet(codeLabs);
    }

    public Set<ClassGroup> getClassGroups() {
        return Collections.unmodifiableSet(classGroups);
    }



    public void setClassGroup(ClassGroup classGroup) {
        if (!classGroup.getStudents().contains(this)) {
            classGroup.add(this);
        }
        this.classGroup = classGroup;
    }

    public boolean add(CodeLab codeLab) {
        boolean added = codeLabs.add(codeLab);
        if ( ! codeLab.getStudents().contains(this)) {
            codeLab.add(this);
        }
        return added;
    }

    public boolean add(ClassGroup classGroup) {
        boolean added = classGroups.add(classGroup);
        User oldCoach = classGroup.getCoach();
        if (oldCoach != null && oldCoach != this) {
            oldCoach.classGroups.remove(classGroup);
        }
        if (this != oldCoach) {
            classGroup.setCoach(this);
        }
        return added;
    }

    public boolean remove(CodeLab codeLab) {
        boolean removed = codeLabs.remove(codeLab);
        if (codeLab.getStudents().contains(this)) {
            codeLab.remove(this);
        }
        return removed;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email == null ? 0 : email.toLowerCase().hashCode();
    }
}
