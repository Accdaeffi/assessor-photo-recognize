package ru.itmo.iad.assessorphotorecognize.domain.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ru.itmo.iad.assessorphotorecognize.domain.dao.AssessorDao;

public interface AsessorRepository extends MongoRepository<AssessorDao, ObjectId> {

	Optional<AssessorDao> findByTelegramId(String telegramId);
	
}
