package com.migration.demo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    private int id;
    private String name;
    private Date birthDate;
}
