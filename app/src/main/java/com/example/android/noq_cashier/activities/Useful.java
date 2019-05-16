package com.example.android.noq_cashier.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Useful {
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    NavigationView nv;
    Activity that;

    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference cafeRef = ref.child("cafes");
    public static final DatabaseReference orderRef = ref.child("orders");
    public static final DatabaseReference userRef = ref.child("authentication").child("users");
    public static final String google_api_key =
            //"648378489334-138kgq7v5fiftdget7pp0vifahki0i2m.apps.googleusercontent.com";
            "648378489334-0kfava9v3evp4123h6bosi4ohojb3pkg.apps.googleusercontent.com";



}
