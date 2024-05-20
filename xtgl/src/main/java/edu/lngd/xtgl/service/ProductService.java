package edu.lngd.xtgl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.lngd.xtgl.collection.Product;

@Service
public class ProductService {
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    public Object test() {
        return mongoTemplate.findOne(new Query(Criteria.where("off").exists(false)), Product.class);
    }

    public List<Product> findAll() {
        return mongoTemplate.find(new Query(Criteria.where("off").exists(false)), Product.class);
    }

    public Product getById(String Id) {
        return mongoTemplate.findById(Id, Product.class);
    }
}
