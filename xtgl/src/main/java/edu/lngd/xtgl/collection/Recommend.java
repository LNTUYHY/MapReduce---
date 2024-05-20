package edu.lngd.xtgl.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Recommend {
    
    private String id;
    private String userId;
    private String productId;
    private String timeStampe;
}
