package com.alanddev.manwal.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.TrendAdapter;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.model.Trend;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

public class TrendActivity extends AppCompatActivity {

    Spinner spinnerType;
    Spinner spinnerTime;
    private TransactionController transactionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transactionController = new TransactionController(this);
        transactionController.open();

        ListView listViewTrend = (ListView)findViewById(R.id.list_transaction_trend);


        spinnerType = (Spinner) findViewById(R.id.spinner_type);
        spinnerTime = (Spinner) findViewById(R.id.spinner_time);
        setDataSpinner();

        BarChart chart = (BarChart) findViewById(R.id.chart);
        //setDataBarChart(chart);
        getData(chart, Constant.TREND_TYPE_EXPENSE, 1, 12, "2015",listViewTrend);


//        BarChart chart2 = new BarChart(this);
//        chart2.setMinimumHeight(200);
//        setDataCombinedChart(chart2);
//
//        ViewGroup viewGroup = (ViewGroup)chart.getParent();
//        viewGroup.removeView(chart);
//        viewGroup.addView(chart2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDataSpinner(){

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);

    }

    private void setDataBarChart(BarChart chart){
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        dataSets = new ArrayList<>();
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets.add(barDataSet1);
        //dataSets.add(barDataSet2);
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");

        BarData data = new BarData(xAxis,dataSets);
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();


    }

    private void setDataCombinedChart(BarChart chart){
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
        valueSet1.add(v1e6);

        dataSets = new ArrayList<>();
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets.add(barDataSet1);
        //dataSets.add(barDataSet2);
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("1");
        xAxis.add("2");
        xAxis.add("3");
        xAxis.add("4");
        xAxis.add("5");
        xAxis.add("6");

        BarData data = new BarData(xAxis,dataSets);
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();


    }


    private void getData(BarChart chart, int option, int monthBegin, int monthEnd, String year,ListView listView){
        ArrayList<String>xAxis = new ArrayList<>();
        for (int m = monthBegin; m <=monthEnd; m++){
            xAxis.add(Integer.toString(m));
        }

        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSetY = new ArrayList<>();
        ArrayList<Trend> trends = transactionController.getAmountTrendByMonths(option,Utils.getWallet_id(),monthBegin,monthEnd,year);
        listView.setAdapter(new TrendAdapter(this, trends));
        for (int i = 0; i<trends.size();i++){
            Trend trend = trends.get(i);
            valueSetY.add(new BarEntry(trend.getAmount(),i));
        }

        dataSets = new ArrayList<>();
        BarDataSet barDataSet1 = new BarDataSet(valueSetY, year);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets.add(barDataSet1);

        BarData data = new BarData(xAxis,dataSets);
        chart.setData(data);
        chart.setDescription("Trend");
        chart.animateXY(2000, 2000);
        chart.invalidate();

    }

}
