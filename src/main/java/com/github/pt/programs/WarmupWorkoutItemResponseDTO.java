package com.github.pt.programs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
class WarmupWorkoutItemResponseDTO {
    Long id;
    Long exercise_id;
    String exercise_name;
    Integer speed;
    Integer incline;
    Integer time_in_min;
}