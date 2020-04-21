package com.leon.repositories;

import com.leon.models.Usage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsageRepository extends MongoRepository<Usage, String>
{
    List<Usage> findByApp(String app);
    List<Usage> findByUser(String user);
}