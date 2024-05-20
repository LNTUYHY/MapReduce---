package edu.lngd.xtgl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.lngd.xtgl.collection.User;

@Service
public class UserService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public User test() {
        return mongoTemplate.findOne(new Query(), User.class);
    }

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User getById(String id) {
        return mongoTemplate.findById(id, User.class);
    }
}
