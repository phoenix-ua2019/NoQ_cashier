package ua.lviv.iot.phoenix.noq_cashier.activities;

import android.app.Activity;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Useful {
    public static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    public static final DatabaseReference cafeRef = ref.child("cafes");
    public static final DatabaseReference orderRef = ref.child("orders");
    public static final DatabaseReference userRef = ref.child("authentication").child("users");
}
