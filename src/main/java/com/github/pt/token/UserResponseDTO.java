package com.github.pt.token;

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
public class UserResponseDTO {
    Long id;
    String name;
    String avatar;    
    String gender;
    Integer age;
}
