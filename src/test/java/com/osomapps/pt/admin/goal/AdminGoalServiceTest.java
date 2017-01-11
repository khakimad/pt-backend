package com.osomapps.pt.admin.goal;

import com.osomapps.pt.ResourceNotFoundException;
import com.osomapps.pt.dictionary.DictionaryService;
import com.osomapps.pt.goals.Goal;
import com.osomapps.pt.goals.GoalParameter;
import com.osomapps.pt.goals.GoalParameterRepository;
import com.osomapps.pt.goals.GoalRepository;
import com.osomapps.pt.goals.GoalTypeRepository;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

@RunWith(MockitoJUnitRunner.class)
public class AdminGoalServiceTest {

    @Mock
    private GoalRepository goalRepository;
    @Mock
    private GoalParameterRepository goalParameterRepository;
    @Mock
    private DictionaryService dictionaryService;
    @Mock
    private GoalTypeRepository goalTypeRepository;

    @InjectMocks
    private AdminGoalService adminGoalService;

    @Test
    public void findAll() {
        when(goalRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(
                new Goal().setGoalParameters(Arrays.asList(new GoalParameter()))));
        List<GoalResponseDTO> goalResponseDTOs = adminGoalService.findAll();
        assertThat(goalResponseDTOs.size(), equalTo(1));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOne_not_found() {
        when(goalRepository.findOne(eq(1L))).thenReturn(null);
        adminGoalService.findOne(1L);
    }

    @Test
    public void findOne() {
        when(goalRepository.findOne(eq(1L))).thenReturn(
                new Goal().setGoalParameters(Arrays.asList(new GoalParameter())));
        GoalResponseDTO goalResponseDTO = adminGoalService.findOne(1L);
        assertThat(goalResponseDTO, notNullValue());
    }

    @Test
    public void create() {
        when(goalRepository.save((Goal) anyObject())).thenAnswer(i -> i.getArguments()[0]);
        GoalResponseDTO goalResponseDTO = adminGoalService.create(
            new GoalRequestDTO().setParameters(Arrays.asList(new GoalParameterRequestDTO()))
                    .setType(new GoalTypeRequestDTO().setId(1L)));
        assertThat(goalResponseDTO, notNullValue());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void update_not_found() {
        adminGoalService.update(1L,
            new GoalRequestDTO().setParameters(Arrays.asList(new GoalParameterRequestDTO())));
    }

    @Test
    public void update() {
        when(goalRepository.findOne(eq(1L))).thenReturn(
                new Goal().setGoalParameters(Arrays.asList(new GoalParameter())));
        when(goalRepository.save((Goal) anyObject())).thenAnswer(i -> i.getArguments()[0]);
        GoalResponseDTO goalResponseDTO = adminGoalService.update(1L,
            new GoalRequestDTO().setParameters(Arrays.asList(new GoalParameterRequestDTO()))
                .setType(new GoalTypeRequestDTO().setId(1L)));
        assertThat(goalResponseDTO, notNullValue());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_not_found() {
        adminGoalService.delete(1L);
    }

    @Test
    public void delete() {
        when(goalRepository.findOne(eq(1L))).thenReturn(
                new Goal().setGoalParameters(Arrays.asList(new GoalParameter())));
        GoalResponseDTO goalResponseDTO = adminGoalService.delete(1L);
        assertThat(goalResponseDTO, notNullValue());
    }
}
