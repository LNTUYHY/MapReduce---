package edu.lngd.xtgl.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document
public class User {
    
    private String id;
    private String name;
}
