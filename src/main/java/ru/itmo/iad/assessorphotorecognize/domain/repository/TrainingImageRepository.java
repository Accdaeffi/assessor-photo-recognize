package ru.itmo.iad.assessorphotorecognize.domain.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ru.itmo.iad.assessorphotorecognize.domain.Dataset;
import ru.itmo.iad.assessorphotorecognize.domain.dao.TrainingImageDao;

public interface TrainingImageRepository extends MongoRepository<TrainingImageDao, ObjectId> {
	
	List<TrainingImageDao> findByDataset(Dataset dataset);

}
