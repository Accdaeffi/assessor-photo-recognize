package ru.itmo.iad.assessorphotorecognize.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.assessorphotorecognize.domain.dao.AssessorDao;
import ru.itmo.iad.assessorphotorecognize.domain.repository.AsessorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsessorService {

	private final AsessorRepository repository;
	
	public AssessorDao getOrCreateAsessor(User user) {
		Optional<AssessorDao> optionalAssessor = repository.findByTelegramId(user.getId().toString());
		
		if (optionalAssessor.isPresent()) {
			return optionalAssessor.get();
		} else {
			AssessorDao asessor = AssessorDao.builder()
					._id(ObjectId.get())
					.telegramId(user.getId().toString())
					.username(user.getUserName())
					.honeypotCount(0)
					.build();
			
			asessor = repository.save(asessor);
			return asessor;
		}
	}
	
	public void increaseHoneypotCounter(String telegramId) {
		Optional<AssessorDao> optionalAssessor = repository.findByTelegramId(telegramId);
		AssessorDao assessor = optionalAssessor.get();

		assessor.setHoneypotCount(assessor.getHoneypotCount()+1);
		
		repository.save(assessor);
	}

}
