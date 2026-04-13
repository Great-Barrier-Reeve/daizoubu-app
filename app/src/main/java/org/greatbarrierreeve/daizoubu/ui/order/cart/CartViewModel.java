package org.greatbarrierreeve.daizoubu.ui.order.cart;


import androidx.lifecycle.ViewModel;

import java.math.BigDecimal;


public class CartViewModel extends ViewModel {

    private String bountyInput = "";


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


    // delete last digit of bounty input
    public void deleteBountyDigit() {

        if (!bountyInput.isEmpty()) {

            bountyInput = bountyInput.substring(0, bountyInput.length() - 1);

        }

    }


    // return big decimal object of bounty amount
    public BigDecimal getBountyAmount() { return bountyInput.isEmpty() ? BigDecimal.ZERO : new BigDecimal(bountyInput); }


    // getter method for bounty input
    public String getBountyInput() { return bountyInput; }

}
