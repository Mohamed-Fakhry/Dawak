package com.example.dawak.data;

import com.example.dawak.model.Pharmacy;

/**
 * Created by Mohamed Fakhry on 27/02/2018.
 */

public class PharmacyResponed {

    private Pharmacy details;
    private String g;

    public PharmacyResponed() {
    }

    public Pharmacy getDetails() {
        return details;
    }

    public String getG() {
        return g;
    }

    @Override
    public String toString() {
        return "PharmacyResponed{" +
                "details=" + details +
                ", g='" + g + '\'' +
                '}';
    }
}
