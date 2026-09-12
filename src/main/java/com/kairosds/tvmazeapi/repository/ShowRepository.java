package com.kairosds.tvmazeapi.repository;

import com.kairosds.tvmazeapi.document.ShowDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShowRepository extends MongoRepository<ShowDocument, Long> {
}
