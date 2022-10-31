package ru.itmo.iad.assessorphotorecognize.telegram.commands.assessment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.assessorphotorecognize.domain.Label;
import ru.itmo.iad.assessorphotorecognize.domain.dao.AssessorDao;
import ru.itmo.iad.assessorphotorecognize.domain.dto.ImageDto;
import ru.itmo.iad.assessorphotorecognize.service.AsessmentSaver;
import ru.itmo.iad.assessorphotorecognize.service.AsessorService;
import ru.itmo.iad.assessorphotorecognize.service.ImageGetter;
import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.keyboards.ZeroLevelLabelKeyboard;
import ru.itmo.iad.assessorphotorecognize.telegram.response.EditMessageCaptionResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.response.MultiResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.response.PhotoResponse;
import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;
import ru.itmo.iad.assessorphotorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@Slf4j
public class RecordAsessmentCommand extends AbsCommand {

	@Autowired
	AsessmentSaver assessmentSaver;

	@Autowired
	AsessorService asessorService;

	@Autowired
	ImageGetter imageGetter;

	@Autowired
	ZeroLevelLabelKeyboard zeroLevelLabelKeyboard;

	private final User user;
	private final String photoId;
	private final Label label;
	private final boolean isHoneypot;
	private final int messageId;

	public RecordAsessmentCommand(User user, String argument, int messageId) throws Exception {
		this.user = user;
		String[] splittedArgument = argument.split(" ", 3);
		this.photoId = splittedArgument[0];
		this.label = Label.getByButtonCode(splittedArgument[1]);
		this.isHoneypot = Boolean.parseBoolean(splittedArgument[2]);
		this.messageId = messageId;
	}

	@Override
	public Response<?> execute() {
		List<Response<?>> responses = new ArrayList<Response<?>>();
		assessmentSaver.saveAssessment(user.getId().toString(), photoId, label, isHoneypot);

		var response = new EditMessageCaptionResponse(
			"Спасибо за идентификацию " + label.getButtonText() + " ✅",
			messageId,
			new InlineKeyboardMarkup(new ArrayList<>())
		);
		responses.add(response);

		try {
			AssessorDao asessor = asessorService.getOrCreateAsessor(user);
			ImageDto image = imageGetter.getImage(asessor.getHoneypotCount());

			responses.add(new PhotoResponse(image.getData(), image.getPhotoId(), null,
					zeroLevelLabelKeyboard.getKeyboard(image.getPhotoId(), image.isHoneypot())));
		} catch (Exception e) {
			log.error("Ошибка!", e);
			responses.add(new StringResponse("Ошибка получения фото!"));
		}

		return new MultiResponse(responses);

	}

}
