package com.osomapps.pt.tokenemail;

import java.time.LocalDateTime;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;
import static org.junit.Assert.assertThat;

public class InUserEmailTest {
    @Test
    public void create() {
        assertThat(new InUserEmail(), notNullValue());
    }

    @Test
    public void createAllArgs() {
        assertThat(new InUserEmail(
                1L,
                null,
                LocalDateTime.now(),
                null,
                null,
                null,
                null), notNullValue());
    }

    @Test
    public void setters() {
        InUserEmail inUserEmail = new InUserEmail();
        inUserEmail.setId(1L);
        inUserEmail.setCreated(LocalDateTime.MAX);
        inUserEmail.setDevice_id("");
        assertThat(inUserEmail, notNullValue());
    }

    @Test
    public void getters() {
        InUserEmail inUserEmail = new InUserEmail();
        inUserEmail.getId();
        inUserEmail.getCreated();
        inUserEmail.getDevice_id();
        assertThat(inUserEmail, notNullValue());
    }
}
