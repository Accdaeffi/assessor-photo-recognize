package ru.itmo.iad.assessorphotorecognize.telegram.commands.photos;

import java.util.Comparator;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import lombok.extern.slf4j.Slf4j;
import ru.itmo.iad.assessorphotorecognize.service.ImageSaver;
import ru.itmo.iad.assessorphotorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.assessorphotorecognize.telegram.response.Response;
import ru.itmo.iad.assessorphotorecognize.telegram.response.StringResponse;

@Service
@Scope("prototype")
@Slf4j
public class SaveNewPhotoToTrainSetCommand extends AbsCommand {

	@Autowired
	ImageSaver imageSaver;

	private final List<PhotoSize> photoSizes;

	public SaveNewPhotoToTrainSetCommand(List<PhotoSize> photoSizes) {
		this.photoSizes = photoSizes;
	}

	@Override
	public Response<?> execute() {

		log.info("Received photo to save!");

		PhotoSize photo = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

		ObjectId id = imageSaver.saveTrainingImage(photo);

		if (id != null) {
			return new StringResponse(id.toString());
		} else {
			return new StringResponse("Ошибка!");
		}
	}

}
