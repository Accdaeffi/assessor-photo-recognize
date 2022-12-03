package ru.itmo.iad.photorecognize.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.iad.assessorphotorecognize.domain.Label;
import ru.itmo.iad.assessorphotorecognize.domain.repository.ImageAsessmentRepository;
import ru.itmo.iad.assessorphotorecognize.service.AsessmentSaver;
import ru.itmo.iad.assessorphotorecognize.service.AsessorService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AsessmentSaverTest {

    @Mock
    private ImageAsessmentRepository imageAsessmentRepository;

    @Mock
    private AsessorService asessorService;

    @InjectMocks
    private AsessmentSaver asessmentSaver;

    @Test
    void testHoneypot() {
        when(imageAsessmentRepository.save(any())).thenReturn(null);
        doNothing().when(asessorService).increaseHoneypotCounter(any());

        asessmentSaver.saveAssessment("user",
                "baa78b862120032000000000",
                Label.CITY, true);

        verify(imageAsessmentRepository).save(any());

        verify(asessorService).increaseHoneypotCounter("user");
    }

    @Test
    void testNotHoneypot() {
        when(imageAsessmentRepository.save(any())).thenReturn(null);

        asessmentSaver.saveAssessment("user", "baa78b862120032000000000",
                Label.CITY, false);

        verify(imageAsessmentRepository).save(any());

        verifyNoInteractions(asessorService);
    }
}
