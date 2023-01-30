package com.switchfully.lms.services.dto.classGroup;

import com.switchfully.lms.domain.ClassGroup;
import com.switchfully.lms.repositories.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ClassGroupMapper {
    private final UserRepository userRepository;

    public ClassGroupMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClassGroup toClassGroup (CreateClassGroupDto createdClassGroup) {
        return new ClassGroup(createdClassGroup.name(), userRepository.findById(createdClassGroup.coach().id()).orElseThrow());
    }
}
