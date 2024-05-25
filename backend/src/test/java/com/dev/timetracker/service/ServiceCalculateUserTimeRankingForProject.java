package com.dev.timetracker.service;

import com.dev.timetracker.dto.report.DTOUserTime;
import com.dev.timetracker.entity.EntityTask;
import com.dev.timetracker.repository.RepositoryTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.dev.timetracker.service.ServiceMocks.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceCalculateUserTimeRankingForProject {

    @Mock
    private RepositoryTask repositoryTask;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    public void initializeTestData() {
        userMocks();
        projectMocks();
        taskMocks();
    }

    @Test
    void calculateUserTimeRankingForProject_ShouldReturnSortedRanking() {
        // Arrange

        List<EntityTask> tasks = Arrays.asList(task1, task2, task3);
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(tasks);

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertAll("result",
                () -> assertEquals(2, result.size(), "Tamanho da lista"),
                () -> assertEquals("Davi Costa", result.get(0).userName(), "Nome do usuário 1"),
                () -> assertEquals(6, result.get(0).totalHours(), "Total de horas do usuário 1"),
                () -> assertEquals("Maria Bonita", result.get(1).userName(), "Nome do usuário 2"),
                () -> assertEquals(1, result.get(1).totalHours(), "Total de horas do usuário 2")
        );

        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
    }
    @Test
    void calculateUserTimeRankingForProject_ShouldIgnoreInactiveTasks() {
        // Arrange
        // Modificar task1 para ser inativa
        task1.setActive(false);

        // Lista de tarefas inclui task1 inativa
        List<EntityTask> tasks = Arrays.asList(task2, task3);
        when(repositoryTask.findAllByProjectIdAndActiveTrue(1L)).thenReturn(tasks);

        // Act
        List<DTOUserTime> result = reportService.calculateUserTimeRankingForProject(1L);

        // Assert
        assertAll("result",
                () -> assertEquals("Davi Costa", result.get(0).userName(), "Nome do usuário"),
                () -> assertEquals(4, result.get(0).totalHours(), "Total de horas do usuário")
        );
        verify(repositoryTask, times(1)).findAllByProjectIdAndActiveTrue(1L);
    }

}
