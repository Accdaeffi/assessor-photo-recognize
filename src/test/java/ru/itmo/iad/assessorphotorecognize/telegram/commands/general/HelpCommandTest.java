package ru.itmo.iad.assessorphotorecognize.telegram.commands.general;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.itmo.iad.assessorphotorecognize.service.MonitoringService;

import static com.mongodb.assertions.Assertions.assertFalse;

class HelpCommandTest {

    @Mock
    MonitoringService monitoringService;
    @InjectMocks
    private HelpCommand helpCommand;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.doNothing().when(monitoringService).incrementHelpCounter();
    }

    @Test
    void testResponse() {
        assertFalse(helpCommand.execute().getContent().isEmpty());
    }

}