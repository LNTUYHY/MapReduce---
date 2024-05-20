package edu.lngd.xtgl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.lngd.xtgl.collection.Recommend;

@Service
public class RecommendService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public Recommend test() {
        return mongoTemplate.findOne(new Query(), Recommend.class);
    }

    public List<Recommend> findAll() {
        return mongoTemplate.findAll(Recommend.class);
    }

    public List<Recommend> getRecommendList(String userId) {
        return mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), Recommend.class);
    }
}
