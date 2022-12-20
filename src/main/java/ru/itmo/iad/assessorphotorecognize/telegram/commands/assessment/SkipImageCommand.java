package ru.itmo.iad.assessorphotorecognize.telegram.commands.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.response.EditMessageCaptionResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.response.MultiResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;

import java.util.ArrayList;

@Service
@Scope("prototype")
public class SkipImageCommand extends AbsCommand {

    @Autowired
    ApplicationContext appContext;

    private final User user;
    private final int messageId;

    public SkipImageCommand(User user, int messageId) {
        this.user = user;
        this.messageId = messageId;
    }

    @Override
    public Response<?> execute() {
        // make list of responses
        var responses = new ArrayList<Response<?>>();
        responses.add(
            new EditMessageCaptionResponse("Изображение пропущено!", messageId,
                new InlineKeyboardMarkup(new ArrayList<>()))
        );
        responses.add(
            appContext.getBean(NextImageCommand.class, user).execute()
        );

        return new MultiResponse(responses);
    }

}
