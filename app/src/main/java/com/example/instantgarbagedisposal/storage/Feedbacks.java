package com.example.instantgarbagedisposal.storage;

import java.util.HashMap;
import java.util.Map;

public class Feedbacks extends Database implements Persistance{


    private static final String FEEDBACK_TAG = "feedback";
    public final String feedback;

    public Feedbacks(String feedback){
        this.feedback = feedback;
    }


    public void save(){
        Map<String, Object> data1 = new HashMap<>();
        data1.put(FEEDBACK_TAG, feedback);

        root.child("feedbacks").push().setValue(data1);
    }
}
