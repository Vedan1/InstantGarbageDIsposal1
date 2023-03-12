package com.example.instantgarbagedisposal.storage;

import java.util.HashMap;
import java.util.Map;

public class Issue extends Database implements Persistance {

    private static final String LOCATION_TAG = "location";
    private static final String GARBAGE_TYPE_TAG = "garbage_type";
    private static final String IMAGE_TAG = "image";
    public final String location;
    public final String garbageType;
    public final String image;


    public Issue(String location, String garbageType, String image) {
        this.location = location;
        this.garbageType = garbageType;
        this.image = image;
    }

    @Override
    public void save() {
        Map<String, Object> data = new HashMap<>();
        data.put(LOCATION_TAG, location);
        data.put(GARBAGE_TYPE_TAG, garbageType);
        data.put(IMAGE_TAG, image);

        root.child("Issues").push().setValue(data);
    }
}
