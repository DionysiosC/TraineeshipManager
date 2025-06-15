package com.uoi.soft_eng.project.services;

import com.uoi.soft_eng.project.model.Company;
import com.uoi.soft_eng.project.model.Evaluation;
import com.uoi.soft_eng.project.model.EvaluationType;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.CompanyRepository;
import com.uoi.soft_eng.project.repository.EvaluationRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @Mock
    private EvaluationRepository evaluationRepository;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void retrieveProfileReturnsCompanyWhenFound() {
        Company company = new Company();
        company.setUsername("test_company");

        when(companyRepository.findByUsername("test_company")).thenReturn(Optional.of(company));

        Company result = companyService.retrieveProfile("test_company");

        assertEquals("test_company", result.getUsername());
        verify(companyRepository).findByUsername("test_company");
    }

    @Test
    void retrieveProfileReturnsNullWhenCompanyNotFound() {
        when(companyRepository.findByUsername("non_existing")).thenReturn(Optional.empty());

        Company result = companyService.retrieveProfile("non_existing");

        assertNull(result);
        verify(companyRepository).findByUsername("non_existing");
    }

    @Test
    void saveProfileCallsRepositorySave() {
        Company company = new Company();
        company.setUsername("test_company");

        companyService.saveProfile(company);

        verify(companyRepository).save(company);
    }

    @Test
    void retrieveAvailablePositionsReturnsPositionsList() {
        List<TraineeshipPosition> positions = Arrays.asList(new TraineeshipPosition(), new TraineeshipPosition());

        when(traineeshipPositionRepository.findAvailablePositionsByCompanyUsername("test_company"))
            .thenReturn(positions);

        List<TraineeshipPosition> result = companyService.retrieveAvailablePositions("test_company");

        assertEquals(2, result.size());
        verify(traineeshipPositionRepository).findAvailablePositionsByCompanyUsername("test_company");
    }

    @Test
    void addPositionSavesPositionWhenCompanyExists() {
        Company company = new Company();
        company.setUsername("test_company");
        TraineeshipPosition position = new TraineeshipPosition();

        when(companyRepository.findByUsername("test_company")).thenReturn(Optional.of(company));

        companyService.addPosition("test_company", position);

        assertEquals(company, position.getCompany());
        verify(traineeshipPositionRepository).save(position);
    }

    @Test
    void addPositionThrowsExceptionWhenCompanyNotFound() {
        TraineeshipPosition position = new TraineeshipPosition();

        when(companyRepository.findByUsername("non_existing")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.addPosition("non_existing", position);
        });

        assertEquals("Company not found with name: non_existing", exception.getMessage());
        verify(traineeshipPositionRepository, never()).save(any());
    }

    @Test
    void deletePositionCallsRepositoryDelete() {
        companyService.deletePosition(1);

        verify(traineeshipPositionRepository).deleteById(1);
    }

    @Test
    void retrieveAssignedPositionsReturnsPositionsList() {
        List<TraineeshipPosition> positions = Arrays.asList(new TraineeshipPosition(), new TraineeshipPosition());

        when(traineeshipPositionRepository.findAssignedPositionsByCompanyUsername("test_company"))
            .thenReturn(positions);

        List<TraineeshipPosition> result = companyService.retrieveAssignedPositions("test_company");

        assertEquals(2, result.size());
        verify(traineeshipPositionRepository).findAssignedPositionsByCompanyUsername("test_company");
    }

    @Test
    void saveEvaluationUpdatesExistingEvaluation() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(1);

        Evaluation existingEvaluation = new Evaluation(position, EvaluationType.COMPANY);
        existingEvaluation.setMotivation(7);

        List<Evaluation> evaluations = new ArrayList<>();
        evaluations.add(existingEvaluation);
        position.setEvaluations(evaluations);

        Evaluation newEvaluation = new Evaluation();
        newEvaluation.setMotivation(9);
        newEvaluation.setEfficiency(8);
        newEvaluation.setEffectiveness(7);

        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.of(position));

        companyService.saveEvaluation(1, newEvaluation);

        assertEquals(9, existingEvaluation.getMotivation());
        assertEquals(8, existingEvaluation.getEfficiency());
        assertEquals(7, existingEvaluation.getEffectiveness());
        verify(evaluationRepository).save(existingEvaluation);
        verify(traineeshipPositionRepository).save(position);
    }

    @Test
    void saveEvaluationCreatesNewEvaluationWhenNotExists() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(1);
        position.setEvaluations(new ArrayList<>());

        Evaluation newEvaluation = new Evaluation();
        newEvaluation.setMotivation(9);
        newEvaluation.setEfficiency(8);
        newEvaluation.setEffectiveness(7);

        when(traineeshipPositionRepository.findById(1)).thenReturn(Optional.of(position));

        companyService.saveEvaluation(1, newEvaluation);

        verify(evaluationRepository).save(any(Evaluation.class));
        verify(traineeshipPositionRepository).save(position);
    }

    @Test
    void saveEvaluationThrowsExceptionWhenPositionNotFound() {
        Evaluation evaluation = new Evaluation();
        evaluation.setMotivation(9);

        when(traineeshipPositionRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.saveEvaluation(999, evaluation);
        });

        assertEquals("Traineeship position not found with ID: 999", exception.getMessage());
        verify(evaluationRepository, never()).save(any());
        verify(traineeshipPositionRepository, never()).save(any());
    }
}