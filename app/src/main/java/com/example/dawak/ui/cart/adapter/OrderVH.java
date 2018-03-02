package com.example.dawak.ui.cart.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dawak.R;
import com.example.dawak.model.Order;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Fakhry on 02/03/2018.
 */

class OrderVH extends RecyclerView.ViewHolder {

    @BindView(R.id.medicineNameTV)
    TextView medicineNameTV;
    @BindView(R.id.quantityTV)
    TextView quantityTV;
    @BindView(R.id.messageTV)
    TextView messageTV;
    @BindView(R.id.messageL)
    LinearLayout messageL;

    public OrderVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Order order) {
        medicineNameTV.setText(order.getName());
        quantityTV.setText(String.valueOf(order.getQuantity()));

        int messageRes;
        int colorRes;

        if (order.getAvailable() == null) {
            messageRes = R.string.pending;
            colorRes = R.color.colorPending;
        } else if (order.getAvailable()) {
            messageRes = R.string.available;
            colorRes = R.color.colorAvailable;
        } else {
            messageRes = R.string.not_available;
            colorRes = R.color.colorNotAvailable;
        }

        messageTV.setText(itemView.getContext().getString(messageRes));
        messageL.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), colorRes));
    }
}
