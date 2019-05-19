package ua.lviv.iot.phoenix.noq_cashier.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.activities.BaseActivity;
import ua.lviv.iot.phoenix.noq_cashier.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq_cashier.models.Meal;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private View view;

    Button acceptOrder;
    Button rejectOrder;
    Button confirm;
    Button decline;

    Dialog confirmationDialog;

    BaseActivity currentActivity;

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

        acceptOrder = (Button) view.findViewById(R.id.accept_order);
        rejectOrder = (Button) view.findViewById(R.id.reject_order);

        currentActivity = (BaseActivity) getActivity();

        acceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialogCaller(order, true);
            }
        });

        rejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //setArguments(b);
                confirmationDialogCaller(order, false);
            }
        });

        return view;
    }

    public void confirmationDialogCaller(Order order, boolean status) {
        confirmationDialog = new Dialog(currentActivity);
        confirmationDialog.setContentView(R.layout.acception_dialog);

        confirm = confirmationDialog.findViewById(R.id.confirm_button);
        decline = confirmationDialog.findViewById(R.id.decline_button);
        TextView confirmationMassage = confirmationDialog.findViewById(R.id.confirmation_message);

        confirm.setEnabled(true);
        decline.setEnabled(true);

        if (status) {
            confirmationMassage.setText("Ви впевнені, що хочете підтвердити замовлення?");
            confirm.setOnClickListener((View v) -> {
                Bundle b = new Bundle();
                order.setStatus(true);
                b.putParcelable("accepted order", order);
                setArguments(b);
                currentActivity.goToAcceptedOrderFragment(view);
                confirmationDialog.cancel();
            });

            decline.setOnClickListener((View v) -> {
                confirmationDialog.cancel();
            });

        } else {
            confirmationMassage.setText("Ви впевнені, що хочете відхилити замовлення?");
            confirm.setOnClickListener((View v) -> {
                order.setStatus(false);
                currentActivity.goToNewOrdersFragment(view);
                confirmationDialog.cancel();
            });

            decline.setOnClickListener((View v) -> {
                confirmationDialog.cancel();
            });

        }


        confirmationDialog.show();
    }

}
