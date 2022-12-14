package ru.itmo.iad.assessorphotorecognize.domain.dao;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Document(collection = "image_assessment")
@Data
@Builder
public class ImageAssessmentDao {

	@Field
	ObjectId _id;

	@Field
	String by;

	@Field
	ObjectId imageId;

	@Field
	String label;

	@Field
	Date dtCreated;
}
