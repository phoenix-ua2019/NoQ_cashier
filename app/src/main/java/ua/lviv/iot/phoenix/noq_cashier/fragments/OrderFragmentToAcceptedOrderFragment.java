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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.activities.BaseActivity;
import ua.lviv.iot.phoenix.noq_cashier.activities.Useful;
import ua.lviv.iot.phoenix.noq_cashier.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq_cashier.adapters.OrderAdapter;
import ua.lviv.iot.phoenix.noq_cashier.listeners.RecyclerTouchListener;
import ua.lviv.iot.phoenix.noq_cashier.models.Meal;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;
import ua.lviv.iot.phoenix.noq_cashier.models.User;

public class OrderFragmentToAcceptedOrderFragment extends Fragment {

    private View view;
    private List<Order> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Order order;
    private MealAdapter mealAdapter;
    BaseActivity currentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order_fragment_to_accepted_order, container, false);

        order = getArguments().getParcelable("order");
        ArrayList<Meal> meals = order.getCafe().getCafeMeals();
        String time = order.getTime();
        double sumPrice = order.getSum();

        new Useful(order, view.findViewById(R.id.user_name_in_OFAO), view.findViewById(R.id.order_number_in_OFAO)).setUser();

        mealAdapter = new MealAdapter(meals);

        recyclerView = view.findViewById(R.id.recycler_order_meals_AO);

        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ((TextView) view.findViewById(R.id.selected_time_show_AO)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price_AO)).setText(String.format("%s ₴", sumPrice));

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
                b.putParcelable("order", orderList.get(position));
                setArguments(b);
                currentActivity.goToOrderFragmentFromAcceptedOrderFragment(view);
            }

            @Override
            public void onLongClick(View v, int position) {
            }

        }));
        return view;
    }
}
