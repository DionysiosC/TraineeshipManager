package com.uoi.soft_eng.project.services;

import com.uoi.soft_eng.project.model.Student;
import com.uoi.soft_eng.project.model.TraineeshipPosition;
import com.uoi.soft_eng.project.repository.StudentRepository;
import com.uoi.soft_eng.project.repository.TraineeshipPositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TraineeshipPositionRepository traineeshipPositionRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void saveProfileCallsRepositorySave() {
        Student student = new Student();
        student.setUsername("test_user");

        studentService.saveProfile(student);

        verify(studentRepository).save(student);
    }

    @Test
    void retrieveProfileReturnsStudentWhenFound() {
        Student student = new Student();
        student.setUsername("test_user");

        when(studentRepository.findByUsername("test_user")).thenReturn(Optional.of(student));

        Student result = studentService.retrieveProfile("test_user");

        assertNotNull(result);
        assertEquals("test_user", result.getUsername());
        verify(studentRepository).findByUsername("test_user");
    }

    @Test
    void retrieveProfileReturnsNullWhenStudentNotFound() {
        when(studentRepository.findByUsername("non_existing_user")).thenReturn(Optional.empty());

        Student result = studentService.retrieveProfile("non_existing_user");

        assertNull(result);
        verify(studentRepository).findByUsername("non_existing_user");
    }

    @Test
    void saveLogbookCallsRepositorySave() {
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(1);

        studentService.saveLogbook(position);

        verify(traineeshipPositionRepository).save(position);
    }
}
