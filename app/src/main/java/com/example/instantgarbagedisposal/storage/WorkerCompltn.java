package com.example.instantgarbagedisposal.storage;

import com.google.android.gms.tasks.Task;

import java.util.HashMap;
import java.util.Map;

public class WorkerCompltn extends Database implements Persistance {

    private static final String USER_IMAGE_TAG = "image";
    public final String userImage;

    public WorkerCompltn(String userImage){
        this.userImage = userImage;
    }


    public void save(){
        Map<String, Object> data1 = new HashMap<>();
        data1.put(USER_IMAGE_TAG, userImage);

        root.child("User Feedback").push().setValue(data1);
    }
}
