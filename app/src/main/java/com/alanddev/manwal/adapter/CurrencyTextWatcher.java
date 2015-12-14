package com.alanddev.manwal.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by TD.LONG on 12/14/2015.
 */
public class CurrencyTextWatcher implements TextWatcher {

    private EditText amountEdit;

    public CurrencyTextWatcher(){

    }

    public CurrencyTextWatcher(EditText editText){
        amountEdit = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        /***
         * No need to continue the function if there is nothing to
         * format
         ***/
        if (s.length() == 0){
            return;
        }

        /*** Now the number of digits in price is limited to 10 ***/
        String value = s.toString().replaceAll(",", "");
        if (value.length() > 10) {
            value = value.substring(0, 10);
        }
        String formattedPrice = getFormatedCurrency(value);
        if (!(formattedPrice.equalsIgnoreCase(s.toString()))) {
            /***
             * The below given line will call the function recursively
             * and will ends at this if block condition
             ***/
            amountEdit.setText(formattedPrice);
            amountEdit.setSelection(amountEdit.length());
        }
    }

    /**
     *
     * @param value not formated amount
     * @return Formated string of amount (###,##,##,###).
     */
    public static String getFormatedCurrency(String value) {
        try {
            NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
            return formatter.format(Double.parseDouble(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
