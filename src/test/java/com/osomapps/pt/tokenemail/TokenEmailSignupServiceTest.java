package com.osomapps.pt.tokenemail;

import com.osomapps.pt.UnauthorizedException;
import com.osomapps.pt.token.InUser;
import com.osomapps.pt.token.InUserLoginRepository;
import com.osomapps.pt.token.InUserRepository;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;

@RunWith(MockitoJUnitRunner.class)
public class TokenEmailSignupServiceTest {

    @Mock
    private SendEmailService sendEmailService;
    @Mock
    private InUserEmailRepository inUserEmailRepository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private InUserRepository inUserRepository;
    @Mock
    private InUserLoginRepository inUserLoginRepository;
    @Mock
    private DataurlValidator dataurlValidator;
    @Mock
    private NameValidator nameValidator;
    @InjectMocks
    private TokenEmailSignupService tokenEmailSignupService;

    @Test
    public void createInUserEmail() {
        tokenEmailSignupService.createInUserEmail(new TokenEmailSignupRequestDTO().setUser(
                new UserSignupRequestDTO().setEmail("test@mail.com")));
    }

    @Test(expected = UnauthorizedException.class)
    public void createInUserEmail_wrong_email() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object[] args = invocation.getArguments();
            ((Errors) args[1]).reject("emailValidator", "emailValidator");
            return null;
        }).when(emailValidator).validate(anyObject(), any(Errors.class));
        tokenEmailSignupService.createInUserEmail(new TokenEmailSignupRequestDTO().setUser(
                new UserSignupRequestDTO().setEmail("test@mail.com")));
    }

    @Test(expected = UnauthorizedException.class)
    public void createInUserEmail_wrong_dataurl() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object[] args = invocation.getArguments();
            ((Errors) args[1]).reject("dataurlValidator", "dataurlValidator");
            return null;
        }).when(dataurlValidator).validate(anyObject(), any(Errors.class));
        tokenEmailSignupService.createInUserEmail(new TokenEmailSignupRequestDTO().setUser(
                new UserSignupRequestDTO().setEmail("test@mail.com")));
    }

    @Test(expected = UnauthorizedException.class)
    public void createInUserEmail_wrong_name() {
        doAnswer((Answer<Void>) (InvocationOnMock invocation) -> {
            Object[] args = invocation.getArguments();
            ((Errors) args[1]).reject("dataurlValidator", "dataurlValidator");
            return null;
        }).when(nameValidator).validate(anyObject(), any(Errors.class));
        tokenEmailSignupService.createInUserEmail(new TokenEmailSignupRequestDTO().setUser(
                new UserSignupRequestDTO().setEmail("test@mail.com")));
    }

    @Test(expected = UnauthorizedException.class)
    public void createInUserEmail_already_registered() {
        when(inUserEmailRepository.findByLogin(anyString())).thenReturn(Arrays.asList(new InUserEmail()));
        tokenEmailSignupService.createInUserEmail(new TokenEmailSignupRequestDTO().setUser(
                new UserSignupRequestDTO().setEmail("test@mail.com")));
    }

    @Test
    public void createNewToken() {
        when(inUserRepository.save((InUser) anyObject())).thenAnswer(i -> i.getArguments()[0]);
        when(inUserEmailRepository.save((InUserEmail) anyObject())).thenAnswer(i -> i.getArguments()[0]);
        tokenEmailSignupService.createNewToken(new TokenEmailSignupRequestDTO().setUser(
                new UserSignupRequestDTO().setEmail("test@mail.com")), "");
    }
}