package com.github.pt.tokenemail;

import com.github.pt.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource("/application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TokenEmailResourceIntTest {
    
    @Autowired
    TokenEmailResource tokenEmailResource;

    @Test
    public void create() {
        TokenEmailResponseDTO user = tokenEmailResource.create(new TokenEmailRequestDTO(),
                new MockHttpServletRequest());
        assertThat(user, notNullValue());
    }

    @Test
    public void delete() {
        tokenEmailResource.delete("1", new MockHttpServletRequest());
    }
}
