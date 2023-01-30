package com.switchfully.lms.services.dto.classGroup;

import com.switchfully.lms.services.dto.course.CourseIdDto;
import com.switchfully.lms.services.dto.user.UserIdDto;

import java.util.Set;

public record CreateClassGroupDto(String name, UserIdDto coach, Set<CourseIdDto> courses, Set<UserIdDto> students) {
}
