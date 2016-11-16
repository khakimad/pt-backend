package com.github.pt.admin.exercise;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/exercise-category")
class AdminExerciseCategoryResource {

    private final AdminExerciseCategoryService exerciseCategoryService;
    
    @Autowired
    AdminExerciseCategoryResource(AdminExerciseCategoryService exerciseCategoryService) {
        this.exerciseCategoryService = exerciseCategoryService;
    }

    @GetMapping
    List<ExerciseCategoryResponseDTO> findAll() {
        return exerciseCategoryService.findAll();
    }
}