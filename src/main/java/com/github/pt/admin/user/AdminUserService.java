package com.github.pt.admin.user;

import com.github.pt.ResourceNotFoundException;
import com.github.pt.UnauthorizedException;
import com.github.pt.token.InUser;
import com.github.pt.token.InUserFacebook;
import com.github.pt.token.InUserFacebookRepository;
import com.github.pt.token.InUserRepository;
import com.github.pt.tokenemail.EmailValidator;
import com.github.pt.tokenemail.InUserEmail;
import com.github.pt.tokenemail.InUserEmailRepository;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.MapBindingResult;

@Service
class AdminUserService {
    
    private final InUserRepository inUserRepository;
    private final InUserEmailRepository inUserEmailRepository;
    private final InUserFacebookRepository inUserFacebookRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;
    
    AdminUserService(InUserRepository inUserRepository,
            InUserEmailRepository inUserEmailRepository,
            InUserFacebookRepository inUserFacebookRepository,
            PasswordEncoder passwordEncoder,
            EmailValidator emailValidator) {
        this.inUserRepository = inUserRepository;
        this.inUserEmailRepository = inUserEmailRepository;
        this.inUserFacebookRepository = inUserFacebookRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailValidator = emailValidator;
    }

    List<UserResponseDTO> findAll() {
        return inUserRepository.findAll(sortByIdAsc()).stream().map(
                AdminUserService::inUserToDto
        ).collect(Collectors.toList());
    }

    private static UserResponseDTO inUserToDto(InUser inUser) {
        final String userName;
        final String userEmail;
        if (inUser.getInUserFacebooks().isEmpty()) {
            if (inUser.getInUserEmails().isEmpty()) {
                userName = "?";
                userEmail = "?";
            } else {
                userName = inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1).getUser_name();
                userEmail = inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1).getLogin();
            }
        } else {
            userName = inUser.getInUserFacebooks().get(inUser.getInUserFacebooks().size() - 1).getUser_name();
            userEmail = "Facebook user";
        }
        return UserResponseDTO.builder()
                .id(inUser.getId())
                .email(userEmail)
                .name(userName)
                .build();
    }

    private Sort sortByIdAsc() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    UserResponseDTO findOne(Long id) {
        final InUser inUser = inUserRepository.findOne(id);
        if (inUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        return inUserToDto(inUser);
    }

    UserResponseDTO create(UserRequestDTO userRequestDTO) {
        final InUser inUser = new InUser();
        final InUserEmail inUserEmail = new InUserEmail();
        final MapBindingResult errors = new MapBindingResult(new HashMap<>(), String.class.getName());
        emailValidator.validate(userRequestDTO.getEmail(), errors);
        if (errors.hasErrors()) {
            throw new UnauthorizedException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        inUserEmail.setLogin(userRequestDTO.getEmail());
        inUserEmail.setUser_name(userRequestDTO.getName());
        inUserEmail.setPassword(passwordEncoder.encode("Qwerty+1"));
        final InUser savedInUser = inUserRepository.save(inUser);
        inUserEmail.setInUser(savedInUser);
        inUserEmailRepository.save(inUserEmail);
        return UserResponseDTO.builder()
                .id(savedInUser.getId())
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .build();
    }

    UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        final InUser inUser = inUserRepository.findOne(id);
        if (inUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        if (inUser.getInUserEmails().isEmpty()) {
            final InUserFacebook inUserFacebook = inUser.getInUserFacebooks().get(inUser.getInUserFacebooks().size() - 1);
            inUserFacebook.setUser_name(userRequestDTO.getName());
            inUserFacebookRepository.save(inUserFacebook);
        } else {
            final MapBindingResult errors = new MapBindingResult(new HashMap<>(), String.class.getName());
            emailValidator.validate(userRequestDTO.getEmail(), errors);
            if (errors.hasErrors()) {
                throw new UnauthorizedException(errors.getAllErrors().get(0).getDefaultMessage());
            }
            inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1).setUser_name(userRequestDTO.getName());
            inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1).setLogin(userRequestDTO.getEmail());
            inUserEmailRepository.save(inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1));
        }
        return UserResponseDTO.builder()
                .id(inUser.getId())
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .build();
    }

    UserResponseDTO delete(Long id) {
        final InUser inUser = inUserRepository.findOne(id);
        if (inUser == null) {
            throw new ResourceNotFoundException("User with id " + id + " not found.");
        }
        final String name = inUser.getInUserEmails().isEmpty() ?
                        "?" : inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1).getUser_name();
        final String email = inUser.getInUserEmails().isEmpty() ?
                        "?" : inUser.getInUserEmails().get(inUser.getInUserEmails().size() - 1).getLogin();
        inUserRepository.delete(id);
        return UserResponseDTO.builder()
                .id(inUser.getId())
                .email(email)
                .name(name)
                .build();
    }
}
