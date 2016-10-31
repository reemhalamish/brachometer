package com.brachometer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by chai on 27/06/2016.
 *
 * Contact
 */
@Data
public class Contact implements Serializable, Comparable<Contact> {
    @Getter @Setter @NonNull private String name;
    @Getter @Setter @NonNull private String phone;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
        List<String> split = getSplitedName();
        this.firstName = split.get(0);
        this.lastName = split.get(split.size() - 1);
    }


    @Override
    public int compareTo(Contact another) {
        return name.compareTo(another.name);
    }

    public boolean contains(String text) {
        return name.toLowerCase().contains(text.toLowerCase()) || phone.toLowerCase().contains(text.toLowerCase());
    }

    public List<String> getSplitedName(){
        return Arrays.asList(name.split(" "));
    }
}