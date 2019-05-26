package ua.lviv.iot.phoenix.noq_cashier.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.lviv.iot.phoenix.noq_cashier.R;
import ua.lviv.iot.phoenix.noq_cashier.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<Order> orderList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderPrice, time;

        public MyViewHolder(View view) {
            super(view);
            orderPrice = view.findViewById(R.id.price_for_NO);
            time = view.findViewById(R.id.time_of_order_for_NO);
        }
    }

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_new_orders_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        System.out.println(position);
        Order order = orderList.get(position);
        System.out.println(order);
        holder.orderPrice.setText(String.format("%s ₴", order.getSum()));
        holder.time.setText(order.getTime());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    /*public void setList(List<Order> list) {
        orderList = list;
        notifyDataSetChanged();
    }*/
    public List<Order> getOrderList() {
        return orderList;
    }

    /*public void set(int pos, Order o) {
        orderList.set(pos, o);
        notifyItemChanged(pos);
    }

    public void add(Order o) {
        orderList.add(o);
        notifyItemChanged(orderList.size());
    }

    public void remove(Order o) {
        int i = orderList.indexOf(o);
        orderList.remove(o);
        notifyItemChanged(i);
    }*/
}
