package edu.lngd.xtgl.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document
public class Action {
    
    private String id;
    private String userId;
    private String productId;
    private String timeStamp;
    private String type;
}
