package com.example.securitytest.model.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldName;

import java.io.Serializable;


@Getter
@Setter
@Document
public class UserInfo implements Serializable {

    @Id
    @Field(nameType = FieldName.Type.KEY)
    private String id;

    @Field(nameType = FieldName.Type.KEY, write = Field.Write.ALWAYS)
    private String password;


    @Indexed(name = "username", background = true, unique = true)
    @Field(nameType = FieldName.Type.KEY, write = Field.Write.ALWAYS)
    private String username;


    @Field(nameType = FieldName.Type.KEY, write = Field.Write.ALWAYS)
    private String email;


}
