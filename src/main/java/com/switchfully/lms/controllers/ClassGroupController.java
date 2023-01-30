package com.switchfully.lms.controllers;

import com.switchfully.lms.domain.ClassGroup;
import com.switchfully.lms.services.dto.classGroup.ClassGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classGroups")
public class ClassGroupController {
    private final ClassGroupService classGroupService;

    public ClassGroupController(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ClassGroup> getAllClassGroups() {
        return classGroupService.getAllClassGroups();
    }
}
