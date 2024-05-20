package edu.lngd.xtgl.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.lngd.xtgl.collection.Action;

@Service
public class ActionService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public Action test() {
        return mongoTemplate.findOne(new Query(), Action.class);
    }

    public List<Action> findAll() {
        return mongoTemplate.findAll(Action.class);
    }

    public List<Action> getByUserId(String userId) {
        return mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), Action.class);
    }

    public Action insert(String userId, String productId, String type, long timeStamp) {
        Action action = new Action();
        action.setId(userId + "-" + productId + "-" + String.valueOf(timeStamp));
        action.setUserId(userId);
        action.setProductId(productId);
        action.setType(type);
        action.setTimeStamp(String.valueOf(timeStamp));
        return mongoTemplate.insert(action, "action");
    }
}
