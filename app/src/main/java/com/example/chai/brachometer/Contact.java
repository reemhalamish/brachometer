package com.example.chai.brachometer;

import java.io.Serializable;

/**
 * Created by chai on 27/06/2016.
 */
public class Contact implements Serializable {
    String name;
    String phone;
    Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}