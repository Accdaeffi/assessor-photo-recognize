package ru.itmo.iad.assessorphotorecognize.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.assessorphotorecognize.domain.dao.AssessorDao;
import ru.itmo.iad.assessorphotorecognize.domain.repository.AsessorRepository;
import ru.itmo.iad.assessorphotorecognize.service.AsessorService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AsessorServiceTest {

    @Mock
    private AsessorRepository repository;

    @InjectMocks
    private AsessorService asessorService;

    @Test
    void testGetOrCreateAssessor() {
        when(repository.findByTelegramId("1")).thenReturn(Optional.of(AssessorDao.builder()
                .honeypotCount(1)
                .build()));

        User user = new User();
        user.setId(1L);
        user.setUserName("test");

        var result = asessorService.getOrCreateAsessor(user);

        assertEquals(1, result.getHoneypotCount());

        verify(repository).findByTelegramId("1");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testEmptyGetOrCreateAssessor() {
        when(repository.save(any())).thenReturn(any());
        when(repository.findByTelegramId("1")).thenReturn(Optional.empty());

        User user = new User();
        user.setId(1L);
        user.setUserName("test");

        var result = asessorService.getOrCreateAsessor(user);

        verify(repository).findByTelegramId("1");
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testIncreaseHoneypotCounter() {
        when(repository.save(any())).thenReturn(null);
        when(repository.findByTelegramId("1")).thenReturn(Optional.of(AssessorDao.builder()
                        .honeypotCount(1)
                .build()));

        asessorService.increaseHoneypotCounter("1");

        verify(repository).findByTelegramId("1");
        verify(repository).save(any());
    }
}
