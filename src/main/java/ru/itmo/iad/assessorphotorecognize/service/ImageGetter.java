package ru.itmo.iad.assessorphotorecognize.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import ru.itmo.iad.assessorphotorecognize.domain.Dataset;
import ru.itmo.iad.assessorphotorecognize.domain.dao.TrainingImageDao;
import ru.itmo.iad.assessorphotorecognize.domain.dto.ImageDto;
import ru.itmo.iad.assessorphotorecognize.domain.repository.ImageAsessmentRepository;
import ru.itmo.iad.assessorphotorecognize.domain.repository.TrainingImageRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

@Service

@RequiredArgsConstructor
public class ImageGetter {

	private final TrainingImageRepository trainingImageRepository;

	private final ImageAsessmentRepository imageAsessmentRepository;

	private final GridFsTemplate gridFsTemplate;

	private final GridFsOperations operations;

	private final double LEAST_PICKED_PROCENT = 0.35;
	
	private final double HONEYPOT_BASE_PROCENT = 0.7;
	private final int MAXIMUM_HONEYPOT_COUNT = 10;

	public ImageDto getImage(int honeypotCount) throws IllegalStateException, IOException {
		Random r = new Random();
		
		if (honeypotCount < MAXIMUM_HONEYPOT_COUNT && r.nextDouble() < (HONEYPOT_BASE_PROCENT/(honeypotCount+1))) {
			return getHoneypot();
		} else {
			if (r.nextDouble() < LEAST_PICKED_PROCENT) {
				return getLeastPicked();
			} else {
				return getRandom();
			}
		}
	}

	private ImageDto getLeastPicked() throws IllegalStateException, IOException {
		Map<ObjectId, Integer> imagesAsessmentCount = new HashMap<>();

		trainingImageRepository.findByDataset(Dataset.test)
		.forEach(image -> imagesAsessmentCount.put(image.get_id(), 0));

		imageAsessmentRepository.findAll().forEach(asessment -> {
			if (imagesAsessmentCount.containsKey(asessment.getImageId())) {
				imagesAsessmentCount.put(asessment.getImageId(), imagesAsessmentCount.get(asessment.getImageId()) + 1);
			}
		});

		int minimumCount = 1000;
		int imagesWithMinimum = 0;

		for (Entry<ObjectId, Integer> entry : imagesAsessmentCount.entrySet()) {
			if (entry.getValue() < minimumCount) {
				minimumCount = entry.getValue();
				imagesWithMinimum = 0;
			} else if (entry.getValue() == minimumCount) {
				imagesWithMinimum++;
			}
		}
		
		Random random = new Random();
		int selectedImage = random.nextInt(imagesWithMinimum);
		ObjectId minId = null;
				
		for (Entry<ObjectId, Integer> entry : imagesAsessmentCount.entrySet()) {
			if (entry.getValue() == minimumCount) {
				if (selectedImage == 0) {
					minId = entry.getKey();
				} else {
					selectedImage--;
				}
			}
		}

		TrainingImageDao dao = trainingImageRepository.findById(minId).get();

		return convertDaoToDto(dao);
	}

	private ImageDto getRandom() throws IllegalStateException, IOException {
		List<TrainingImageDao> images = trainingImageRepository.findByDataset(Dataset.test);

		return convertDaoToDto(images.get(new Random().nextInt(images.size())));
	}
	
	private ImageDto getHoneypot() throws IllegalStateException, IOException {
		List<TrainingImageDao> images = trainingImageRepository.findByDataset(Dataset.train);

		return convertDaoToDto(images.get(new Random().nextInt(images.size())));
	}
	

	private ImageDto convertDaoToDto(TrainingImageDao dao) throws IllegalStateException, IOException {
		GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(dao.getFileId())));

		return new ImageDto(dao.get_id().toHexString(),
				dao.getDataset() == Dataset.train,
				operations.getResource(file).getInputStream());
	}

}
