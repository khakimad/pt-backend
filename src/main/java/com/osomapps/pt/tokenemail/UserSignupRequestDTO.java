package com.osomapps.pt.tokenemail;

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
class UserSignupRequestDTO {
    String name;
    String email;
    String avatar_dataurl;
}
