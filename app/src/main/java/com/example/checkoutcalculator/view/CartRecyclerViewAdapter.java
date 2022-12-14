package com.example.checkoutcalculator.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.checkoutcalculator.R;
import com.example.checkoutcalculator.viewmodel.CartItemDisplayInfo;

import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private List<CartItemDisplayInfo> cData;
    private LayoutInflater cInflater;
    private ItemClickListener cartClickListener;

    // data is passed into the constructor
    CartRecyclerViewAdapter(Context context, List<CartItemDisplayInfo> data) {
        this.cInflater = LayoutInflater.from(context);
        this.cData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = cInflater.inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItemDisplayInfo item = cData.get(position);
        holder.cartTextView.setText(item.productName);
        holder.cartPriceTextView.setText(String.format("Price: $%.2f", item.price));
        holder.cartSubtotalTextView.setText(String.format("Subtotal: $%.2f", item.subtotal));
        holder.cartItemQuantityView.setText(String.valueOf(item.quantity));
        holder.cartTaxableCheck.setChecked(item.taxable);

        holder.cartButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.onAddClick(view, item);
            }
        });

        holder.cartButtonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.onMinusClick(view, item);
            }
        });

        holder.cartButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.onRemoveClick(view, item);
            }
        });

        holder.cartTaxableCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickListener.onTaxableClick(view, item);
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return cData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cartTextView;
        TextView cartPriceTextView;
        TextView cartSubtotalTextView;
        EditText cartItemQuantityView;
        CheckBox cartTaxableCheck;

        Button cartButtonRemove;
        Button cartButtonAdd;
        Button cartButtonMinus;

        ViewHolder(View itemView) {
            super(itemView);

            cartTextView = itemView.findViewById(R.id.textview_cart_row_name);
            cartPriceTextView = itemView.findViewById(R.id.textview_cart_row_price);
            cartSubtotalTextView = itemView.findViewById(R.id.textview_cart_row_total);
            cartItemQuantityView = itemView.findViewById(R.id.editTextNumber_cart_row);
            cartTaxableCheck = itemView.findViewById(R.id.checkbox_cart_row);

            cartButtonRemove = itemView.findViewById(R.id.button_cart_row);
            cartButtonAdd = itemView.findViewById(R.id.button_cart_row_add);
            cartButtonMinus = itemView.findViewById(R.id.button_cart_row_minus);
        }
    }

    // convenience method for getting data at click position
    CartItemDisplayInfo getItem(int id) {
        return cData.get(id);
    }

    public void setCartClickListener(ItemClickListener cartClickListener) {
        this.cartClickListener = cartClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onAddClick(View view, CartItemDisplayInfo item);
        void onMinusClick(View view, CartItemDisplayInfo item);
        void onRemoveClick(View view, CartItemDisplayInfo item);
        void onTaxableClick(View view, CartItemDisplayInfo item);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setcData(List<CartItemDisplayInfo> cData) {
        notifyDataSetChanged();
        this.cData = cData;
    }
}