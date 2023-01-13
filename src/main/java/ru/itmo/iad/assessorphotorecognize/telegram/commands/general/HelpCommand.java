package ru.itmo.iad.assessorphotorecognize.telegram.commands.general;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.assessorphotorecognize.service.MonitoringService;
import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@NoArgsConstructor
@Slf4j
public class HelpCommand extends AbsCommand {
    @Autowired
    private MonitoringService monitoringService;

    @Override
    public StringResponse execute() {
        try {
            monitoringService.incrementHelpCounter();
            String builder = "Описание классов с примерами:" +
                    System.lineSeparator() +
                    System.lineSeparator() +
                    "https://docs.google.com/document/d/1pG41a8pfhYZNnzaHU08J-A06_P2_QkFhOHgTluehetU";
            return new StringResponse(builder);
        } catch (Exception e) {
            monitoringService.incrementHelpErrorCounter();
            log.error("Error on help handling: ", e);
            return new StringResponse("error occurs...");
        }
    }
}
