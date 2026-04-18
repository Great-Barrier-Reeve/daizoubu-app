package org.greatbarrierreeve.daizoubu.ui.order.cart;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.greatbarrierreeve.daizoubu.data.model.OrderItem;
import org.greatbarrierreeve.daizoubu.data.repository.CartRepository;

import java.util.List;


public class CartViewModel extends AndroidViewModel {

    private final CartRepository cartRepository = CartRepository.getInstance();
    private final MutableLiveData<String> bountyAmount = new MutableLiveData<>("0.00");
    private final SharedPreferences prefs;
    private String bountyInput = "";


    public CartViewModel(Application application) {

        super(application);

        // load saved bounty amount from shared preferences on initialisation
        prefs = application.getSharedPreferences("session", Context.MODE_PRIVATE);
        String amount = prefs.getString("bountyAmount", "0.00");
        setBountyAmount(amount);
        bountyInput = amount.replace(".", "").replaceAll("^0+", "");

    }


    // append digit to bounty input
    public void appendBountyInput(String digit) {

        // guard against overflow
        if (bountyInput.length() >= 8 ) { return; }

        // skip appending zero to zero value bounty input
        if (bountyInput.isEmpty() && digit.equals("0")) { return; }

        // append digit to bounty input
        bountyInput += digit;

    }


    // clear bounty input
    public void clearBountyInput() { bountyInput = ""; }


    // update bounty amount from bounty input
    public void confirmBountyInput() {

        String amount = bountyInput;

        if (amount.isEmpty()) {

            amount = "0.00";

        } else {

            // pad with zeroes to ensure 2dp format
            while (amount.length() < 3) { amount = "0" + amount; }

            // split dollars and cents place with dot
            amount = amount.substring(0, amount.length() - 2) + "." + amount.substring(amount.length() - 2);

        }

        // update bounty amount
        setBountyAmount(amount);

        // save bounty amount to shared preferences
        prefs.edit().putString("bountyAmount", amount).apply();

    }


    // delete last digit of bounty input
    public void deleteBountyDigit() {

        if (!bountyInput.isEmpty()) {

            bountyInput = bountyInput.substring(0, bountyInput.length() - 1);

        }

    }


    // getter method for bounty amount
    public LiveData<String> getBountyAmount() { return bountyAmount; }


    // getter method for bounty input
    public String getBountyInput() { return bountyInput; }


    // getter method for items
    public LiveData<List<OrderItem>> getItems() { return cartRepository.getItems(); }


    // remove item from cart
    public void remove(OrderItem item) { cartRepository.remove(item); }


    // setter method for bounty amount
    public void setBountyAmount(String amount) { bountyAmount.setValue(amount); }


    // submit order errand
    public void submitOrder() {

        cartRepository.clear();

    }

}
