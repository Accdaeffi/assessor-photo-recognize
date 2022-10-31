package ru.itmo.iad.assessorphotorecognize.domain.dao;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;
import ru.itmo.iad.assessorphotorecognize.domain.Dataset;

@Document(collection = "training_image")
@Data
@Builder
public class TrainingImageDao {

    @Id
    @Field
    private ObjectId _id;

    @Field
    private ObjectId fileId;

    @Field
    private String fileName;

    @Field
    private Dataset dataset;

    @Field
    private String label;

    @Field
    private Date dtCreated;
}
