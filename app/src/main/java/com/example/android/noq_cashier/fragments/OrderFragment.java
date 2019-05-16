package com.example.android.noq_cashier.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.noq_cashier.R;
import com.example.android.noq_cashier.adapters.MealAdapter;
import com.example.android.noq_cashier.models.Meal;
import com.example.android.noq_cashier.models.Order;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);

        Order order = getArguments().getParcelable("order");
        System.out.println(order);
        ArrayList<Meal> meals = order.getCafe().getCafeMeals();
        String time = order.getTime();
        double sumPrice = order.getSum();

        MealAdapter mealAdapter = new MealAdapter(meals);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_order_meals);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price)).setText(String.format("%s ₴", sumPrice));

        return view;
    }

}
