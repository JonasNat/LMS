package com.switchfully.lms.services.dto.classGroup;

import com.switchfully.lms.domain.ClassGroup;
import com.switchfully.lms.repositories.ClassGroupRepository;
import com.switchfully.lms.repositories.CourseRepository;
import com.switchfully.lms.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClassGroupService {
    private final ClassGroupRepository classGroupRepository;
    private final UserRepository userRepository;
    private final ClassGroupMapper classGroupMapper;
    private final CourseRepository courseRepository;

    public ClassGroupService(ClassGroupRepository classGroupRepository, UserRepository userRepository, ClassGroupMapper classGroupMapper, CourseRepository courseRepository) {
        this.classGroupRepository = classGroupRepository;
        this.userRepository = userRepository;
        this.classGroupMapper = classGroupMapper;
        this.courseRepository = courseRepository;
    }

    public List<ClassGroup> getAllClassGroups () {
        return classGroupRepository.findAll();
    }

    public ClassGroup createClassGroup(CreateClassGroupDto classGroupToCreate) {
        ClassGroup classGroup = classGroupMapper.toClassGroup(classGroupToCreate);
        classGroupToCreate.students().forEach(student -> classGroup.getStudents().add(userRepository.findById(student.id()).orElseThrow()));
        classGroupToCreate.courses().forEach(course -> classGroup.getCourses().add(courseRepository.findById(course.id()).orElseThrow()));
        createStudentCodeLabs(classGroup);
        return classGroupRepository.save(classGroup);
    }

    private void createStudentCodeLabs (ClassGroup classGroup) {
        classGroup.getStudents().
    }

}
