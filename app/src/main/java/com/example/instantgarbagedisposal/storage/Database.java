package com.example.instantgarbagedisposal.storage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public abstract class Database {
    protected DatabaseReference root = FirebaseDatabase.getInstance().getReference();
}
