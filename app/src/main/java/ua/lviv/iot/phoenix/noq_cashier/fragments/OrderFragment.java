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

import com.google.firebase.auth.FirebaseAuth;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.activities.BaseActivity;
import ua.lviv.iot.phoenix.noq_cashier.activities.Useful;
import ua.lviv.iot.phoenix.noq_cashier.adapters.MealAdapter;
import ua.lviv.iot.phoenix.noq_cashier.models.Meal;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;
import ua.lviv.iot.phoenix.noq_cashier.models.User;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private View view;
    private Order order;

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

        order = getArguments().getParcelable("order");
        ArrayList<Meal> meals = order.getCafe().getCafeMeals();
        String time = order.getTime();
        double sumPrice = order.getSum();

        MealAdapter mealAdapter = new MealAdapter(meals);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_order_meals);
        recyclerView.setAdapter(mealAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        new Useful(order, view.findViewById(R.id.user_name_in_FO), view.findViewById(R.id.order_number_in_FO)).setUser();

        ((TextView) view.findViewById(R.id.selected_time_show)).setText(time);
        ((TextView) view.findViewById(R.id.selected_price)).setText(String.format("%s ₴", sumPrice));

        acceptOrder = view.findViewById(R.id.accept_order);
        rejectOrder = view.findViewById(R.id.reject_order);

        currentActivity = (BaseActivity) getActivity();

        acceptOrder.setOnClickListener((View v) -> confirmationDialogCaller(true));

        rejectOrder.setOnClickListener((View v) -> confirmationDialogCaller(false));

        return view;
    }

    public void confirmationDialogCaller(boolean status) {
        confirmationDialog = new Dialog(currentActivity);
        confirmationDialog.setContentView(R.layout.confirmation_dialog);

        confirm = confirmationDialog.findViewById(R.id.confirm_button);
        decline = confirmationDialog.findViewById(R.id.decline_button);
        TextView confirmationMassage = confirmationDialog.findViewById(R.id.confirmation_message);

        confirm.setEnabled(true);
        decline.setEnabled(true);

        if (status) {
            confirmationMassage.setText("Ви впевнені, що хочете підтвердити замовлення?");
            confirm.setOnClickListener((View v) -> {
                setStatus(1);
                currentActivity.goToAcceptedOrderFragment(view);
            });
        } else {
            confirmationMassage.setText("Ви впевнені, що хочете відхилити замовлення?");
            confirm.setOnClickListener((View v) -> {
                setStatus(-1);
                currentActivity.goToNewOrdersFragment(view);
            });
        }
        decline.setOnClickListener((View v) -> confirmationDialog.cancel());

        confirmationDialog.show();
    }

    private void setStatus(int status) {
        order.setStatus(status);
        Useful.orderRef.child(order.getUid()).child(""+order.getUserPos()).setValue(order);
        Useful.orderRef.child(mAuth.getCurrentUser().getUid()).child(""+order.getPos()).setValue(order);
        confirmationDialog.cancel();
    }

}
