/*
* Here is the implementation of the test case that corresponds to US17 & US18
*/
package com.uoi.soft_eng.project.usecasetests;
import com.uoi.soft_eng.project.services.CommitteeServiceImpl;
import com.uoi.soft_eng.project.factory.PositionsSearchFactory;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.strategy.positions.PositionsSearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class assignStudentTest {

    @InjectMocks
    private CommitteeServiceImpl committeeService;

    @Mock
    private PositionsSearchFactory positionsSearchFactory;

    @Mock
    private PositionsSearchStrategy positionsSearchStrategy;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private TraineeshipPosition createMockPosition(int id, String title) {
        TraineeshipPosition pos = new TraineeshipPosition();
        pos.setId(id);
        pos.setTitle(title);
        pos.setAssigned(false);
        return pos;
    }

    @Test
    void testRetrievePositionsByInterests() {
        // Mock student with interests
        String studentUsername = "student_interests";
        String strategy = "interests";

        List<TraineeshipPosition> mockResults = Arrays.asList(
                createMockPosition(1, "Java Developer"),
                createMockPosition(2, "Backend Engineer")
        );

        when(positionsSearchFactory.create(strategy)).thenReturn(positionsSearchStrategy);
        when(positionsSearchStrategy.search(studentUsername)).thenReturn(mockResults);

        List<TraineeshipPosition> result = committeeService.retrievePositionsForApplicant(studentUsername, strategy);

        assertEquals(mockResults, result);
        verify(positionsSearchFactory).create(strategy);
        verify(positionsSearchStrategy).search(studentUsername);
    }

    @Test
    void testRetrievePositionsByLocation() {
        // Mock student with location preference
        String studentUsername = "student_location";
        String strategy = "location";

        List<TraineeshipPosition> mockResults = Arrays.asList(
                createMockPosition(3, "Frontend Developer"),
                createMockPosition(4, "Mobile App Developer")
        );

        when(positionsSearchFactory.create(strategy)).thenReturn(positionsSearchStrategy);
        when(positionsSearchStrategy.search(studentUsername)).thenReturn(mockResults);

        List<TraineeshipPosition> result = committeeService.retrievePositionsForApplicant(studentUsername, strategy);

        assertEquals(mockResults, result);
        verify(positionsSearchFactory).create(strategy);
        verify(positionsSearchStrategy).search(studentUsername);
    }

    @Test
    void testRetrievePositionsByInterestsAndLocation() {
        // Mock student with both interests and location
        String studentUsername = "student_both";
        String strategy = "both";

        List<TraineeshipPosition> mockResults = Arrays.asList(
                createMockPosition(5, "Full Stack Developer")
        );

        when(positionsSearchFactory.create(strategy)).thenReturn(positionsSearchStrategy);
        when(positionsSearchStrategy.search(studentUsername)).thenReturn(mockResults);

        List<TraineeshipPosition> result = committeeService.retrievePositionsForApplicant(studentUsername, strategy);

        assertEquals(mockResults, result);
        verify(positionsSearchFactory).create(strategy);
        verify(positionsSearchStrategy).search(studentUsername);
    }
}