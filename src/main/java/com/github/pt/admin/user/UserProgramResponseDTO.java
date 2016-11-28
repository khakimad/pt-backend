package com.github.pt.admin.user;

import java.util.List;
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
class UserProgramResponseDTO {
    
    Long id;
    String name;
    String type;
    List<UserWorkoutResponseDTO> workouts;
    
}
