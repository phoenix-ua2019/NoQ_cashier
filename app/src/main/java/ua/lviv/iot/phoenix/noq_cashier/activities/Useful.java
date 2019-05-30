package ua.lviv.iot.phoenix.noq_cashier.activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ua.lviv.iot.phoenix.noq_cashier.models.User;


public class Useful implements ValueEventListener {
    User mUser;
    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference cafeRef = ref.child("cafes");
    public static final DatabaseReference orderRef = ref.child("orders");
    public static final DatabaseReference userRef = ref.child("authentication").child("users");

    public void setUser(String Uid) {
        userRef.child(Uid).addValueEventListener(this);
    }

    public User getUser() {
        return mUser;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        mUser = new User((HashMap<String, String>) dataSnapshot.getValue());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }
}
