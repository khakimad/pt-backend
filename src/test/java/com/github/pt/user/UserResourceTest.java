package com.github.pt.user;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    @Mock
    private UserService userService;    
    
    @InjectMocks
    private UserResource userResource;

    @Test
    public void findOne() {
        userResource.findOne("1");
        verify(userService).findOne(eq("1"));
    }

    public void update() throws Exception {
        userResource.update("1", new UserRequestDTO());
        verify(userService).updateUser(eq("1"), any(UserRequestDTO.class));
    }
}