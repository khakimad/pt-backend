package com.github.pt.programs;

import com.github.pt.user.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProgramService {
    private final InProgramRepository inProgramRepository;
    private final UserService userService;

    @Autowired
    ProgramService(InProgramRepository inProgramRepository,
            UserService userService) {
        this.inProgramRepository = inProgramRepository;
        this.userService = userService;
    }

    List<ProgramResponseDTO> getExamples(String token) {
        if (!token.isEmpty()) {
            userService.checkUserToken(token);
        }
        InProgram inProgram1 = new InProgram();
        inProgram1.setId(1L);
        inProgram1.setName("Test program 1");
        inProgram1.setD_program_type("test");
        InWorkout inWorkout11 = new InWorkout();
        inWorkout11.setId(1L);
        inWorkout11.setD_workout_name("Test workout 1");
        InWorkout inWorkout12 = new InWorkout();
        inWorkout12.setId(2L);
        inWorkout12.setD_workout_name("Test workout 2");
        inProgram1.setInWorkouts(Arrays.asList(inWorkout11, inWorkout12));
        InWarmupWorkoutItem inWarmupWorkoutItem111 = new InWarmupWorkoutItem();
        inWarmupWorkoutItem111.setId(1L);
        inWarmupWorkoutItem111.setD_exercise_id("810");
        inWarmupWorkoutItem111.setD_exercise_name("Treadmill 11");
        inWarmupWorkoutItem111.setIncline(4);
        inWarmupWorkoutItem111.setTime_in_min(10);
        inWorkout11.setInWarmupWorkoutItems(Arrays.asList(inWarmupWorkoutItem111));
        InWorkoutItem inWorkoutItem111 = new InWorkoutItem();
        inWorkoutItem111.setId(1L);
        inWorkoutItem111.setD_exercise_id("20");
        inWorkoutItem111.setD_exercise_name("Squat");
        inWorkoutItem111.setSets(3);
        inWorkoutItem111.setRepetitions(4);
        inWorkoutItem111.setWeight(10);
        InWorkoutItem inWorkoutItem112 = new InWorkoutItem();
        inWorkoutItem112.setId(2L);
        inWorkoutItem112.setD_exercise_id("30");
        inWorkoutItem112.setD_exercise_name("Front Squat");
        inWorkoutItem112.setSets(5);
        inWorkoutItem112.setRepetitions(6);
        inWorkoutItem112.setWeight(11);
        inWorkout11.setInWorkoutItems(Arrays.asList(inWorkoutItem111, inWorkoutItem112));
        InWarmupWorkoutItem inWarmupWorkoutItem121 = new InWarmupWorkoutItem();
        inWarmupWorkoutItem121.setId(1L);
        inWarmupWorkoutItem121.setD_exercise_id("810");
        inWarmupWorkoutItem121.setD_exercise_name("Treadmill 12");
        inWarmupWorkoutItem121.setIncline(4);
        inWarmupWorkoutItem121.setTime_in_min(10);
        inWorkout12.setInWarmupWorkoutItems(Arrays.asList(inWarmupWorkoutItem121));
        InWorkoutItem inWorkoutItem121 = new InWorkoutItem();
        inWorkoutItem121.setId(3L);
        inWorkoutItem121.setD_exercise_id("40");
        inWorkoutItem121.setD_exercise_name("Deadlift");
        inWorkoutItem121.setSets(6);
        inWorkoutItem121.setRepetitions(7);
        inWorkoutItem121.setWeight(12);
        InWorkoutItem inWorkoutItem122 = new InWorkoutItem();
        inWorkoutItem122.setId(4L);
        inWorkoutItem122.setD_exercise_id("50");
        inWorkoutItem122.setD_exercise_name("Sumo Deadlift");
        inWorkoutItem122.setSets(7);
        inWorkoutItem122.setRepetitions(8);
        inWorkoutItem122.setWeight(13);
        inWorkout12.setInWorkoutItems(Arrays.asList(inWorkoutItem121, inWorkoutItem122));

        InProgram inProgram2 = new InProgram();
        inProgram2.setId(2L);
        inProgram2.setName("Personal program 2");
        inProgram2.setD_program_type("personal");
        InWorkout inWorkout21 = new InWorkout();
        inWorkout21.setId(1L);
        inWorkout21.setD_workout_name("Personal workout 1");
        InWorkout inWorkout22 = new InWorkout();
        inWorkout22.setId(2L);
        inWorkout22.setD_workout_name("Personal workout 2");
        inProgram2.setInWorkouts(Arrays.asList(inWorkout21, inWorkout22));
        InWorkoutItem inWorkoutItem211 = new InWorkoutItem();
        inWorkoutItem211.setId(1L);
        inWorkoutItem211.setD_exercise_id("60");
        inWorkoutItem211.setD_exercise_name("Lunge");
        inWorkoutItem211.setSets(3);
        inWorkoutItem211.setRepetitions(4);
        inWorkoutItem211.setWeight(10);
        InWorkoutItem inWorkoutItem212 = new InWorkoutItem();
        inWorkoutItem212.setId(2L);
        inWorkoutItem212.setD_exercise_id("70");
        inWorkoutItem212.setD_exercise_name("Bulgarian Split-Squat");
        inWorkoutItem212.setSets(5);
        inWorkoutItem212.setRepetitions(6);
        inWorkoutItem212.setWeight(11);
        inWorkout21.setInWorkoutItems(Arrays.asList(inWorkoutItem211, inWorkoutItem212));
        InWorkoutItem inWorkoutItem221 = new InWorkoutItem();
        inWorkoutItem221.setId(3L);
        inWorkoutItem221.setD_exercise_id("80");
        inWorkoutItem221.setD_exercise_name("Nordic Hamstrings");
        inWorkoutItem221.setSets(6);
        inWorkoutItem221.setRepetitions(7);
        inWorkoutItem221.setWeight(12);
        InWorkoutItem inWorkoutItem222 = new InWorkoutItem();
        inWorkoutItem222.setId(4L);
        inWorkoutItem222.setD_exercise_id("90");
        inWorkoutItem222.setD_exercise_name("Hip Thrust");
        inWorkoutItem222.setSets(7);
        inWorkoutItem222.setRepetitions(8);
        inWorkoutItem222.setWeight(13);
        inWorkout22.setInWorkoutItems(Arrays.asList(inWorkoutItem221, inWorkoutItem222));

        InProgram inProgram3 = new InProgram();
        inProgram3.setId(3L);
        inProgram3.setName("General program 3");
        inProgram3.setD_program_type("general");
        InWorkout inWorkout31 = new InWorkout();
        inWorkout31.setId(1L);
        inWorkout31.setD_workout_name("General workout 1");
        InWorkout inWorkout32 = new InWorkout();
        inWorkout32.setId(2L);
        inWorkout32.setD_workout_name("General workout 2");
        inProgram3.setInWorkouts(Arrays.asList(inWorkout31, inWorkout32));
        InWorkoutItem inWorkoutItem311 = new InWorkoutItem();
        inWorkoutItem311.setId(1L);
        inWorkoutItem311.setD_exercise_id("100");
        inWorkoutItem311.setD_exercise_name("One-legged Hip Thrust");
        inWorkoutItem311.setSets(3);
        inWorkoutItem311.setRepetitions(4);
        inWorkoutItem311.setWeight(10);
        InWorkoutItem inWorkoutItem312 = new InWorkoutItem();
        inWorkoutItem312.setId(2L);
        inWorkoutItem312.setD_exercise_id("110");
        inWorkoutItem312.setD_exercise_name("Hyperextension");
        inWorkoutItem312.setSets(5);
        inWorkoutItem312.setRepetitions(6);
        inWorkoutItem312.setWeight(11);
        inWorkout31.setInWorkoutItems(Arrays.asList(inWorkoutItem311, inWorkoutItem312));
        InWorkoutItem inWorkoutItem321 = new InWorkoutItem();
        inWorkoutItem321.setId(3L);
        inWorkoutItem321.setD_exercise_id("120");
        inWorkoutItem321.setD_exercise_name("Legpress");
        inWorkoutItem321.setSets(6);
        inWorkoutItem321.setRepetitions(7);
        inWorkoutItem321.setWeight(12);
        InWorkoutItem inWorkoutItem322 = new InWorkoutItem();
        inWorkoutItem322.setId(4L);
        inWorkoutItem322.setD_exercise_id("130");
        inWorkoutItem322.setD_exercise_name("Standing Calf Raise ");
        inWorkoutItem322.setSets(7);
        inWorkoutItem322.setRepetitions(8);
        inWorkoutItem322.setWeight(13);
        inWorkout32.setInWorkoutItems(Arrays.asList(inWorkoutItem321, inWorkoutItem322));

        ProgramResponseDTO program1 = createProgramResponseDTO(inProgram1);
        ProgramResponseDTO program2 = createProgramResponseDTO(inProgram2);
        ProgramResponseDTO program3 = createProgramResponseDTO(inProgram3);
        return Arrays.asList(program1, program2, program3);
    }

    private ProgramResponseDTO createProgramResponseDTO(InProgram inProgram) {
        ProgramResponseDTO program = new ProgramResponseDTO();
        program.setId(inProgram.getId());
        program.setName(inProgram.getName());
        program.setType(inProgram.getD_program_type());
        program.setWorkouts(new ArrayList<>());
        for (InWorkout inWorkout : inProgram.getInWorkouts()) {
            WorkoutResponseDTO workout = new WorkoutResponseDTO();
            workout.setId(inWorkout.getId());
            workout.setName(inWorkout.getD_workout_name());
            workout.setItems(new ArrayList<>());
            program.getWorkouts().add(workout);
            if (inWorkout.getInWarmupWorkoutItems() != null && !inWorkout.getInWarmupWorkoutItems().isEmpty()) {
                InWarmupWorkoutItem inWarmupWorkoutItem = inWorkout.getInWarmupWorkoutItems().get(0);
                WarmupWorkoutItemResponseDTO warmupWorkoutItem = new WarmupWorkoutItemResponseDTO();
                warmupWorkoutItem.setId(inWarmupWorkoutItem.getId());
                warmupWorkoutItem.setExercise_id(Long.parseLong(inWarmupWorkoutItem.getD_exercise_id()));
                warmupWorkoutItem.setExercise_name(inWarmupWorkoutItem.getD_exercise_name());
                warmupWorkoutItem.setSpeed(inWarmupWorkoutItem.getSpeed());
                warmupWorkoutItem.setIncline(inWarmupWorkoutItem.getIncline());
                warmupWorkoutItem.setTime_in_min(inWarmupWorkoutItem.getTime_in_min());
                workout.setWarmup(warmupWorkoutItem);
            }
            for (InWorkoutItem inWorkoutItem : inWorkout.getInWorkoutItems()) {
                WorkoutItemResponseDTO workoutItem = new WorkoutItemResponseDTO();
                workoutItem.setId(inWorkoutItem.getId());
                workoutItem.setExercise_id(Long.parseLong(inWorkoutItem.getD_exercise_id()));
                workoutItem.setExercise_name(inWorkoutItem.getD_exercise_name());
                workoutItem.setSets(inWorkoutItem.getSets());
                workoutItem.setRepetitions(inWorkoutItem.getRepetitions());
                workoutItem.setWeight(inWorkoutItem.getWeight());
                workoutItem.setBodyweight(BooleanUtils.isTrue(inWorkoutItem.getBodyweight()));                
                workout.getItems().add(workoutItem);
            }
        }
        return program;
    }
}
