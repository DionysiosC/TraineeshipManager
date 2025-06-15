package com.uoi.soft_eng.project.services;

import com.uoi.soft_eng.project.model.*;
import com.uoi.soft_eng.project.repository.EvaluationRepository;
import com.uoi.soft_eng.project.repository.ProfessorRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceImplTest {

    @InjectMocks
    private ProfessorServiceImpl professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    private Professor professor;
    private TraineeshipPosition position;
    private Evaluation evaluation;

    @BeforeEach
    void setUp() {
        professor = new Professor();
        professor.setUsername("prof_john");

        position = new TraineeshipPosition();
        position.setId(1);
        position.setSupervisor(professor);

        evaluation = new Evaluation(position, EvaluationType.PROFESSOR);
        evaluation.setMotivation(4);
        evaluation.setEfficiency(5);
        evaluation.setEffectiveness(3);
        evaluation.setCompanyScore(4);
    }

    @Test
    void testRetrieveProfileFound() {
        when(professorRepository.findByUsername("prof_john")).thenReturn(Optional.of(professor));

        Professor result = professorService.retrieveProfile("prof_john");

        assertNotNull(result);
        assertEquals("prof_john", result.getUsername());
        verify(professorRepository).findByUsername("prof_john");
    }

    @Test
    void testRetrieveProfileNotFound() {
        when(professorRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        Professor result = professorService.retrieveProfile("unknown");

        assertNull(result);
    }

    @Test
    void testSaveProfile() {
        professorService.saveProfile(professor);

        verify(professorRepository).save(professor);
    }

    @Test
    void testRetrieveAssignedPositions() {
        when(traineeshipPositionRepository.findBySupervisorUsername("prof_john"))
                .thenReturn(Arrays.asList(position));

        List<TraineeshipPosition> result = professorService.retrieveAssignedPositions("prof_john");

        assertEquals(1, result.size());
        verify(traineeshipPositionRepository).findBySupervisorUsername("prof_john");
    }

    @Test
    void testGetPositionByIdFound() {
        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.of(position));

        TraineeshipPosition result = professorService.getPositionById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testGetPositionByIdNotFound() {
        when(traineeshipPositionRepository.findById(999)).thenReturn(Optional.empty());

        TraineeshipPosition result = professorService.getPositionById(999);

        assertNull(result);
    }

    @Test
    void testSaveEvaluationCreatesNewEvaluation() {
        position.setEvaluations(new ArrayList<>());
        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.of(position));

        professorService.saveEvaluation(1, evaluation);

        verify(evaluationRepository).save(any(Evaluation.class));
        verify(traineeshipPositionRepository).save(position);
    }

    @Test
    void testSaveEvaluationUpdatesExisting() {
        Evaluation existing = new Evaluation(position, EvaluationType.PROFESSOR);
        existing.setMotivation(1);
        existing.setEfficiency(1);
        existing.setEffectiveness(1);
        existing.setCompanyScore(1);

        position.setEvaluations(new ArrayList<>(List.of(existing)));
        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.of(position));

        professorService.saveEvaluation(1, evaluation);

        assertEquals(4, existing.getMotivation());
        assertEquals(5, existing.getEfficiency());
        assertEquals(3, existing.getEffectiveness());
        assertEquals(4, existing.getCompanyScore());

        verify(evaluationRepository).save(existing);
        verify(traineeshipPositionRepository).save(position);
    }

    @Test
    void testSaveEvaluationThrowsIfPositionNotFound() {
        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            professorService.saveEvaluation(1, evaluation);
        });

        assertEquals("Traineeship position not found with ID: 1", exception.getMessage());
    }
}
