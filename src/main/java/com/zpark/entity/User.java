package com.zpark.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
    private String salt;
}
