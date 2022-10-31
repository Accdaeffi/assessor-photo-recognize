package ru.itmo.iad.assessorphotorecognize.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ru.itmo.iad.assessorphotorecognize.domain.dao.ImageAssessmentDao;

public interface ImageAsessmentRepository extends MongoRepository<ImageAssessmentDao, ObjectId> {

}
