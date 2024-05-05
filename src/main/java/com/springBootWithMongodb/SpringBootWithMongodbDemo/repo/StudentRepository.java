package com.springBootWithMongodb.SpringBootWithMongodbDemo.repo;

import com.springBootWithMongodb.SpringBootWithMongodbDemo.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, Integer> {


}

