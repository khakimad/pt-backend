package com.osomapps.pt.reportworkout;

import com.osomapps.pt.admin.program.*;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.runner.RunWith;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ReportWorkoutResourceTest {

    @Mock
    private ReportWorkoutService reportWorkoutService;    
    
    @InjectMocks
    private ReportWorkoutResource reportWorkoutResource;

    @Test
    public void findAll() throws Exception {
        reportWorkoutResource.findAll("");
        verify(reportWorkoutService).findAll(eq(""));
    }

    @Test
    public void create() {
        reportWorkoutResource.create("", new WorkoutReportRequestDTO());
        verify(reportWorkoutService).create(eq(""), any(WorkoutReportRequestDTO.class));
    }
}