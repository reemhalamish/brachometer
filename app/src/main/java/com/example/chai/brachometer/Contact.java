package com.example.chai.brachometer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by chai on 27/06/2016.
 *
 * Contact
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Contact implements Serializable, Comparable<Contact> {
    @Getter @Setter @NonNull String name;
    @Getter @Setter @NonNull String phone;
    @Getter @Setter String firstName;
    @Getter @Setter String lastName;


    @Override
    public int compareTo(Contact another) {
        return name.compareTo(another.name);
    }

    public boolean contains(String text) {
        return name.toLowerCase().contains(text.toLowerCase()) || phone.toLowerCase().contains(text.toLowerCase());
    }
}