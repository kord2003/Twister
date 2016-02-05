package com.example.twister.model;

/**
 * Created by sharlukovich on 03.02.2016.
 */
public class Eta {
    public String tripID;

    public String estimatedArrivalWindowBegin;

    public String estimatedArrivalWindowEnd;

    public Eta(String tripID, String estimatedArrivalWindowBegin, String estimatedArrivalWindowEnd) {
        this.tripID = tripID;
        this.estimatedArrivalWindowBegin = estimatedArrivalWindowBegin;
        this.estimatedArrivalWindowEnd = estimatedArrivalWindowEnd;
    }

    @Override
    public String toString() {
        return "Eta{" +
                "tripID='" + tripID + '\'' +
                ", estimatedArrivalWindowBegin='" + estimatedArrivalWindowBegin + '\'' +
                ", estimatedArrivalWindowEnd='" + estimatedArrivalWindowEnd + '\'' +
                '}';
    }
}