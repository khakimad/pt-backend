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
class TokenEmailSignupRequestDTO {
    UserSignupRequestDTO user;
    String password;
    String device_id;
}
