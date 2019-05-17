package ua.lviv.iot.phoenix.noq_cashier.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.activities.BaseActivity;
import ua.lviv.iot.phoenix.noq_cashier.adapters.OrderAdapter;
import ua.lviv.iot.phoenix.noq_cashier.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;

import java.util.ArrayList;
import java.util.List;


public class AcceptedOrdersFragment extends Fragment {

    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private View view;
    private Order order;

    BaseActivity currentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_of_accepted_orders, container, false);

        try {
            order = getArguments().getParcelable("accepted order");
            orderList.add(order);
        } catch (NullPointerException e) {
            System.out.println("Vpalo");
        }



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
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(currentActivity.getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        orderAdapter.notifyDataSetChanged();
        return view;
    }


}