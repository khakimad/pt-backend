package com.github.pt.admin.user;

import com.github.pt.ResourceNotFoundException;
import com.github.pt.programs.InProgram;
import com.github.pt.programs.InProgramRepository;
import com.github.pt.programs.InWarmupWorkoutItem;
import com.github.pt.programs.InWorkout;
import com.github.pt.programs.InWorkoutItem;
import com.github.pt.programs.InWorkoutItemReport;
import com.github.pt.programs.InWorkoutItemSetReport;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminUserProgramServiceTest {

    @Mock
    private InProgramRepository inProgramRepository;

    @InjectMocks
    private AdminUserProgramService adminUserProgramService;

    private InProgram getInProgram() {
        return new InProgram()
        .setInWorkouts(Arrays.asList(
                new InWorkout()
                    .setInWarmupWorkoutItems(Arrays.asList(new InWarmupWorkoutItem()
                    ))
                    .setInWorkoutItems(Arrays.asList(
                            new InWorkoutItem()
                    .setInWorkoutItemReports(Arrays.asList(
                            new InWorkoutItemReport()
                                    .setInWorkoutItemSetReports(Arrays.asList(
                                            new InWorkoutItemSetReport()))
                    ))))
        ));
    }

    @Test
    public void findAll() {
        when(inProgramRepository.findAll()).thenReturn(Arrays.asList(getInProgram()));
        List<UserProgramResponseDTO> responseDTOs = adminUserProgramService.findAll();
        assertThat(responseDTOs.size(), equalTo(1));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOne_not_found() {
        adminUserProgramService.findOne(1L);
    }

    @Test
    public void findOne() {
        when(inProgramRepository.findOne(eq(1L))).thenReturn(getInProgram());
        UserProgramResponseDTO responseDTO = adminUserProgramService.findOne(1L);
        assertThat(responseDTO, notNullValue());
    }

    @Test
    public void create() {
        when(inProgramRepository.save(any(InProgram.class))).thenAnswer(i -> i.getArguments()[0]);
        UserProgramResponseDTO responseDTO = adminUserProgramService.create(
                new UserProgramRequestDTO().setName("name")
                        .setWorkouts(Arrays.asList(
                                new UserWorkoutRequestDTO()
                                .setItems(Arrays.asList(new UserWorkoutItemRequestDTO()))
                        )));
        assertThat(responseDTO, notNullValue());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void update_not_found() {
        adminUserProgramService.update(1L, new UserProgramRequestDTO());
    }

    @Test
    public void update() {
        when(inProgramRepository.findOne(eq(1L))).thenReturn(getInProgram());
        when(inProgramRepository.save(any(InProgram.class))).thenAnswer(i -> i.getArguments()[0]);
        UserProgramResponseDTO responseDTO = adminUserProgramService.update(1L,
                new UserProgramRequestDTO().setName("name")
                        .setWorkouts(Arrays.asList(
                                new UserWorkoutRequestDTO()
                                .setItems(Arrays.asList(new UserWorkoutItemRequestDTO()))
                        )));
        assertThat(responseDTO, notNullValue());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_not_found() {
        adminUserProgramService.delete(1L);
    }

    @Test
    public void delete() {
        when(inProgramRepository.findOne(eq(1L))).thenReturn(getInProgram());
        UserProgramResponseDTO responseDTO = adminUserProgramService.delete(1L);
        assertThat(responseDTO, notNullValue());
    }

}
