package com.melik.loggingservice.repository;

import com.melik.loggingservice.model.LogMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 11.09.2023
 */

@Repository
public interface LogMessageRepository extends MongoRepository<LogMessage, Long> {
}
