package com.example.twister.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharlukovich on 03.02.2016.
 */

public class EtaRepresentation {
    @SerializedName("trip_id")
    public String tripID;

    @SerializedName("estimated_arrival_window_begin")
    public String estimatedArrivalWindowBegin;

    @SerializedName("estimated_arrival_window_end")
    public String estimatedArrivalWindowEnd;
}