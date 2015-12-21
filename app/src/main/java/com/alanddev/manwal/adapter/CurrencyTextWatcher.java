package com.alanddev.manwal.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alanddev.manwal.util.Utils;

import java.math.BigDecimal;
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

        //String value = s.toString().replaceAll("[,.]", "");
        String value = Utils.getFormatCurrency(s.toString()) ;
        /*** Now the number of digits in price is limited to 10 ***/
//        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
//        String separator = String.valueOf(format.getDecimalFormatSymbols().getDecimalSeparator());
//        //NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
//        if (separator.equals(",")){
//            value = s.toString().replaceAll("[.]", "");
//        }else {
//            value = s.toString().replaceAll(",", "");
//        }
        //String value = s.toString().replaceAll("[,.]", "");
        //String cleanString = s.toString().replaceAll("[$,.]", "");

        if (value.length() > 10) {
            value = value.substring(0, 10);
        }
        String formattedPrice = getFormatedCurrency(value);
        //BigDecimal parsed = new BigDecimal(value).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        //String formattedPrice = NumberFormat.getCurrencyInstance().format(parsed);
        //String formattedPrice  = NumberFormat.getCurrencyInstance().format((Double.parseDouble(value)/100));

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
