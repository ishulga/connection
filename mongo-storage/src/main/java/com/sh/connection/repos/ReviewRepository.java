package com.sh.connection.repos;

import com.sh.connection.persistence.model.mongo.Review;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface ReviewRepository extends CrudRepository<Review, BigInteger> {

}
