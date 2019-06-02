package ua.lviv.iot.phoenix.noq_cashier.fragments;


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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.activities.BaseActivity;
import ua.lviv.iot.phoenix.noq_cashier.activities.Useful;
import ua.lviv.iot.phoenix.noq_cashier.adapters.OrderAdapter;
import ua.lviv.iot.phoenix.noq_cashier.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;

import java.util.ArrayList;
import java.util.List;


public class AcceptedOrdersFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private View view;

    BaseActivity currentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_accepted_orders, container, false);

        recyclerView = view.findViewById(R.id.accepted_orders_recycler_view);

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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                        recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle b = new Bundle();
                b.putParcelable("order", orderAdapter.getOrderList().get(position));
                setArguments(b);
                currentActivity.goToOrderFragmentFromAcceptedOrderFragment(view);
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        Useful.orderRef.child(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(s);
                System.out.println("Added!!!!!");
                int size = orderList.size();
                Order order = new Order(dataSnapshot.getValue())
                        .setPos(Integer.parseInt(dataSnapshot.getKey()));
                if (order.isDone()) {
                    orderList.add(order);
                    orderAdapter.notifyItemChanged(size);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(s);
                System.out.println("Changed!!!!!");
                Order order = new Order(dataSnapshot.getValue());
                int pos = orderList.indexOf(order);
                if (!order.isDone()) {
                    orderList.remove(order);
                    orderAdapter.notifyItemChanged(pos);
                }
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Deleted");
                Order order = new Order(dataSnapshot.getValue());
                int pos = orderList.indexOf(order);
                if (order.isDone()) {
                    orderList.remove(order);
                    orderAdapter.notifyItemChanged(pos);
                }
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