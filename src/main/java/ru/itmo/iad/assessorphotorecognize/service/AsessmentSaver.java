package ru.itmo.iad.assessorphotorecognize.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.itmo.iad.assessorphotorecognize.domain.Label;
import ru.itmo.iad.assessorphotorecognize.domain.dao.ImageAssessmentDao;
import ru.itmo.iad.assessorphotorecognize.domain.repository.ImageAsessmentRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AsessmentSaver {

	private final ImageAsessmentRepository imageAsessmentRepository;

	private final AsessorService asessorService;

	public void saveAssessment(String userId, String photoId, Label label, boolean isHoneypot) {
		ImageAssessmentDao dao = ImageAssessmentDao.builder()
				._id(ObjectId.get())
				.by(userId)
				.imageId(new ObjectId(photoId))
				.label(label.toString())
				.dtCreated(new Date())
				.build();

		imageAsessmentRepository.save(dao);
		
		if (isHoneypot) {
			asessorService.increaseHoneypotCounter(userId);
		}
	}
}
