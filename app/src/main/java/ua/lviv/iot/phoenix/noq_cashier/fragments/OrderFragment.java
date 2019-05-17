package ua.lviv.iot.phoenix.noq_cashier.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.activities.BaseActivity;
import ua.lviv.iot.phoenix.noq_cashier.activities.Useful;
import ua.lviv.iot.phoenix.noq_cashier.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq_cashier.models.Meal;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private View view;

    Button acceptOrder;
    Button rejectOrder;

    BaseActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order, container, false);

        Order order = getArguments().getParcelable("order");
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

        acceptOrder = view.findViewById(R.id.accept_order);
        rejectOrder = view.findViewById(R.id.reject_order);

        currentActivity = (BaseActivity) getActivity();

        acceptOrder.setOnClickListener((View v) -> {
            order.setStatus(1);
            Useful.orderRef.child("Bikini Bottom").child(""+order.getPos()).setValue(order);
            currentActivity.goToAcceptedOrderFragment(view);
        });

        rejectOrder.setOnClickListener((View v) -> {
            order.setStatus(-1);
            Useful.orderRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(""+order.getPos()).setValue(order);
            currentActivity.goToNewOrdersFragment(view);
        });





        return view;
    }

}
