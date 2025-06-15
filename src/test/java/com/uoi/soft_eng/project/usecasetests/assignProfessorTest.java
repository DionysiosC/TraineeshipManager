/*
* Here is the implementation of the test case that corresponds to US19
*/
package com.uoi.soft_eng.project.usecasetests;

import com.uoi.soft_eng.project.services.CommitteeServiceImpl;
import com.uoi.soft_eng.project.factory.SupervisorAssignmentFactory;
import com.uoi.soft_eng.project.strategy.assignments.SupervisorAssignmentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;


public class assignProfessorTest {

    @InjectMocks
    private CommitteeServiceImpl committeeService;

    @Mock
    private SupervisorAssignmentFactory supervisorAssignmentFactory;

    @Mock
    private SupervisorAssignmentStrategy assignmentStrategy;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignSupervisorByLoad() {
        Integer positionId = 101;
        String strategy = "load";

        when(supervisorAssignmentFactory.create(strategy)).thenReturn(assignmentStrategy);

        committeeService.assignSupervisor(positionId, strategy);

        verify(supervisorAssignmentFactory).create(strategy);
        verify(assignmentStrategy).assign(positionId);
    }

    @Test
    void testAssignSupervisorByInterest() {
        Integer positionId = 202;
        String strategy = "interests";

        when(supervisorAssignmentFactory.create(strategy)).thenReturn(assignmentStrategy);

        committeeService.assignSupervisor(positionId, strategy);

        verify(supervisorAssignmentFactory).create(strategy);
        verify(assignmentStrategy).assign(positionId);
    }
}
