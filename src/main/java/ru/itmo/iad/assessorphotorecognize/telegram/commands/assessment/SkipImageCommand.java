package ru.itmo.iad.assessorphotorecognize.telegram.commands.assessment;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.response.EditMessageCaptionResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;

@Service
@Scope("prototype")
public class SkipImageCommand extends AbsCommand {

	private final int messageId;

	public SkipImageCommand(int messageId) throws Exception {
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		EditMessageCaptionResponse response = new EditMessageCaptionResponse("Изображение пропущено!", messageId,
				new InlineKeyboardMarkup(new ArrayList<>()));

		return response;
	}

}
