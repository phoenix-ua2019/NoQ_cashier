package com.example.android.noq_cashier.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.noq_cashier.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptedOrdersFragment extends Fragment {


    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_of_accepted_orders, container, false);

        return view;
    }

}
