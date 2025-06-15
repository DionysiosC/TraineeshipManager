package com.uoi.soft_eng.project.services;

import com.uoi.soft_eng.project.factory.PositionsSearchFactory;
import com.uoi.soft_eng.project.factory.SupervisorAssignmentFactory;
import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.StudentRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import com.uoi.soft_eng.project.strategy.assignments.SupervisorAssignmentStrategy;
import com.uoi.soft_eng.project.strategy.positions.PositionsSearchStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommitteeServiceImplTest {

    @InjectMocks
    private CommitteeServiceImpl committeeService;

    @Mock
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PositionsSearchFactory positionsSearchFactory;

    @Mock
    private SupervisorAssignmentFactory supervisorAssignmentFactory;

    @Mock
    private PositionsSearchStrategy mockSearchStrategy;

    @Mock
    private SupervisorAssignmentStrategy mockSupervisorStrategy;

    private Student student;
    private TraineeshipPosition position;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setUsername("john_doe");
        student.setLookingForTraineeship(true);

        position = new TraineeshipPosition();
        position.setId(1);
        position.setAssigned(false);
    }

    @Test
    void testRetrievePositionsForApplicant() {
        when(positionsSearchFactory.create("default")).thenReturn(mockSearchStrategy);
        when(mockSearchStrategy.search("john_doe")).thenReturn(Arrays.asList(position));

        List<TraineeshipPosition> result = committeeService.retrievePositionsForApplicant("john_doe", "default");

        assertEquals(1, result.size());
        verify(positionsSearchFactory).create("default");
        verify(mockSearchStrategy).search("john_doe");
    }

    @Test
    void testRetrieveTraineeshipApplications() {
        when(studentRepository.retrieveTraineeshipApplications()).thenReturn(Arrays.asList(student));

        List<Student> result = committeeService.retrieveTraineeshipApplications();

        assertEquals(1, result.size());
        verify(studentRepository).retrieveTraineeshipApplications();
    }

    @Test
    void testAssignPositionWhenBothPresent() {
        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.of(position));
        when(studentRepository.findByUsername("john_doe")).thenReturn(Optional.of(student));

        committeeService.assignPosition(1, "john_doe");

        assertTrue(position.isAssigned());
        assertEquals(student, position.getStudent());
        assertEquals(position, student.getAssignedTraineeship());
        assertFalse(student.isLookingForTraineeship());

        verify(traineeshipPositionRepository).save(position);
        verify(studentRepository).save(student);
    }

    @Test
    void testAssignSupervisor_ByLoad() {
        when(supervisorAssignmentFactory.create("load")).thenReturn(mockSupervisorStrategy);
    
        committeeService.assignSupervisor(1, "load");
    
        verify(supervisorAssignmentFactory).create("load");
        verify(mockSupervisorStrategy).assign(1);
    }
    
    @Test
    void testAssignSupervisor_ByInterest() {
        when(supervisorAssignmentFactory.create("interests")).thenReturn(mockSupervisorStrategy);
    
        committeeService.assignSupervisor(1, "interests");
    
        verify(supervisorAssignmentFactory).create("interests");
        verify(mockSupervisorStrategy).assign(1);
    }


    @Test
    void testListAssignedTraineeships() {
        when(traineeshipPositionRepository.returnAllAssignedPositions()).thenReturn(Arrays.asList(position));

        List<TraineeshipPosition> result = committeeService.listAssignedTraineeships();

        assertEquals(1, result.size());
        verify(traineeshipPositionRepository).returnAllAssignedPositions();
    }

    @Test
    void testAssignPositionWhenStudentOrPositionNotPresent() {
        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.empty());
        when(studentRepository.findByUsername("john_doe")).thenReturn(Optional.of(student));

        committeeService.assignPosition(1, "john_doe");

        verify(traineeshipPositionRepository, never()).save(any());
        verify(studentRepository, never()).save(any());
    }
}
