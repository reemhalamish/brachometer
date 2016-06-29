package com.example.chai.brachometer;

import java.io.Serializable;

/**
 * Created by chai on 27/06/2016.
 *
 * Contact
 */
public class Contact implements Serializable, Comparable<Contact> {
    String name;
    String phone;
    Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int compareTo(Contact another) {
        return name.compareTo(another.name);
    }
}