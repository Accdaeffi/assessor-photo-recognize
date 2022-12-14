package ru.itmo.iad.assessorphotorecognize.telegram.commands.general;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.itmo.iad.assessorphotorecognize.domain.Label;
import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.exception.NotEnoughParametersException;
import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;
import ru.itmo.iad.assessorphotorecognize.telegram.response.StringResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.texts.InfoTexts;

@Service
@Scope("prototype")
public class InfoCommand extends AbsCommand {

	private final Label label;

	public InfoCommand(String labelText) throws Exception {
		if (labelText != null) {
			this.label = Label.getByButtonText(labelText);
		} else {
			throw new NotEnoughParametersException();
		}
	}

	@Override
	public Response<?> execute() {
		return new StringResponse(InfoTexts.infoTexts.get(label));
	}

}
