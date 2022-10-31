package ru.itmo.iad.assessorphotorecognize.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ru.itmo.iad.assessorphotorecognize.domain.Dataset;
import ru.itmo.iad.assessorphotorecognize.domain.dao.TrainingImageDao;
import ru.itmo.iad.assessorphotorecognize.domain.repository.TrainingImageRepository;
import ru.itmo.iad.assessorphotorecognize.telegram.Bot;

@Service
public class ImageSaver {

	@Autowired
	GridFsTemplate gridFsTemplate;

	@Autowired
	TrainingImageRepository trainingImageRepository;
	
	@Autowired
	Bot bot;

	public ObjectId saveTrainingImage(PhotoSize photo) {

		String filePath;

		if (photo.getFilePath() != null) {
			filePath = photo.getFilePath();
		} else {
			GetFile getFileMethod = new GetFile();
			getFileMethod.setFileId(photo.getFileId());

			try {
				org.telegram.telegrambots.meta.api.objects.File file = bot.execute(getFileMethod);

				filePath = file.getFilePath();
			} catch (TelegramApiException e) {
				filePath = null;
				e.printStackTrace();
			}
		}

		if (filePath != null) {
			try {
				File image = bot.downloadFile(filePath);

				ObjectId fileId = gridFsTemplate.store(new FileInputStream(image), image.getName());

				var trainingImage = TrainingImageDao.builder()._id(ObjectId.get()).fileId(fileId)
						.fileName(image.getName()).dataset(Dataset.test).dtCreated(new Date()).build();

				trainingImageRepository.save(trainingImage);

				return trainingImage.get_id();

			} catch (TelegramApiException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		return null;

	}

}