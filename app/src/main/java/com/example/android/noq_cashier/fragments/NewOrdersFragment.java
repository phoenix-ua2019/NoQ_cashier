package com.example.android.noq_cashier.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.noq_cashier.R;
import com.example.android.noq_cashier.activities.BaseActivity;
import com.example.android.noq_cashier.adapters.OrderAdapter;
import com.example.android.noq_cashier.listeners.RecyclerTouchListener;
import com.example.android.noq_cashier.models.Order;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrdersFragment extends Fragment {


    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private View view;

    ImageView plus, minus;
    Button chooseTimeBtn;
    Dialog quantityDialog;
    BaseActivity currentActivity;

    int commonSelectedAmount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_new_orders, container, false);

        recyclerView = view.findViewById(R.id.new_orders_recycler_view);
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        currentActivity = (BaseActivity) getActivity();

        recyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // row click listener
        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(currentActivity.getApplicationContext(),
                        recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Bundle b = new Bundle();
                        b.putParcelable("order", orderList.get(position));
                        setArguments(b);
                        currentActivity.b1(view);
                    }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("orders").child("Bikini Bottom");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               /* for (int i=0; i<dataSnapshot.getChildrenCount(); i++)
                    orderList.add(((List<Order>)((List<Map<String,Map>>) dataSnapshot.getValue()).get(i).get("cafe").get("cafeOrders")).get(0));
                orderAdapter.setList(orderList);
                orderAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                orderList.add(new Order((Map<String, ?>) dataSnapshot.getValue()));
                System.out.println(orderList);
                System.out.println(1000000);
                orderAdapter.setList(orderList);
                orderAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }
}


