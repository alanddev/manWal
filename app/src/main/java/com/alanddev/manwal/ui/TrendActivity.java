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
import com.alanddev.manwal.adapter.TransactionSumAdapter;
import com.alanddev.manwal.adapter.TrendAdapter;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.model.TransactionSum;
import com.alanddev.manwal.model.Trend;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

public class TrendActivity extends AppCompatActivity {

    Spinner spinnerType;
    Spinner spinnerToTime;
    Spinner spinnerFromTime;
    private TransactionController transactionController;
    BarChart chart;
    PieChart chartPie;
    ListView listViewTrend;
    ListView listViewTrendPie;
    String year;
    int toMonth;
    int fromMonth;
    int option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_trend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_trend));

        listViewTrend = (ListView)findViewById(R.id.list_transaction_trend);
        listViewTrendPie = (ListView)findViewById(R.id.list_transaction_trend_pie);

        year  = Utils.getYear();
        toMonth = 1;
        fromMonth = 12;
        option = Constant.TREND_TYPE_EXPENSE;

        spinnerType = (Spinner) findViewById(R.id.spinner_type);
        spinnerFromTime = (Spinner) findViewById(R.id.from_month);
        spinnerToTime = (Spinner) findViewById(R.id.to_month);

        setDataSpinner();


        chart = (BarChart) findViewById(R.id.chart);
        chartPie = (PieChart) findViewById(R.id.chartPie);

        transactionController = new TransactionController(this);
        transactionController.open();
        getData(chart, Constant.TREND_TYPE_EXPENSE, fromMonth, toMonth, year, listViewTrend);


        getDataPie(chartPie, Constant.TREND_TYPE_EXPENSE, fromMonth, toMonth, year, listViewTrendPie);
        //Utils.ListUtils.setDynamicHeight(listViewTrendPie);

//        BarChart chart2 = new BarChart(this);
//        chart2.setMinimumHeight(200);
//        setDataCombinedChart(chart2);
//
//        ViewGroup viewGroup = (ViewGroup)chart.getParent();
//        viewGroup.removeView(listViewTrend);
//        viewGroup.removeView(chart);
//        viewGroup.removeView(chartPie);
        //viewGroup.addView(chart2);

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
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals(getResources().getString(R.string.type_array_expense))) {
                    option = Constant.TREND_TYPE_EXPENSE;
                    getData(chart, option, fromMonth,toMonth,  year,listViewTrend);
                    getDataPie(chartPie, option, fromMonth, toMonth, year, listViewTrendPie);
                }else if(selectedItem.equals(getResources().getString(R.string.type_array_income))){
                    option = Constant.TREND_TYPE_INCOME;
                    getData(chart, option, fromMonth,toMonth, year,listViewTrend);
                    getDataPie(chartPie, option, fromMonth, toMonth, year, listViewTrendPie);
                }else if(selectedItem.equals(getResources().getString(R.string.type_array_balance))){
                    option = Constant.TREND_TYPE_BALANCE;
                    getData(chart, option, fromMonth,toMonth, year,listViewTrend);
                    getDataPie(chartPie, option, fromMonth, toMonth, year, listViewTrendPie);
                }
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



        //ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this,
        //        R.array.time_array, android.R.layout.simple_spinner_item);

        String values[] =   getResources().getStringArray(R.array.time_array);
        for (int i = 0; i <values.length;i++){
            values[i] = values[i] + "-" + year;
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, values);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromTime.setAdapter(adapter);

        spinnerFromTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String[] temps = selectedItem.split("-");
                String month = temps[0];
                fromMonth = Integer.valueOf(month);
                getData(chart, option, fromMonth, toMonth, year, listViewTrend);
                getDataPie(chartPie,option,fromMonth,toMonth,year,listViewTrendPie);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnerToTime.setAdapter(adapter);
        spinnerToTime.setSelection(11);
        //spinnerTime.setAdapter(adapterTime);
        spinnerToTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                String[] temps = selectedItem.split("-");
                String month = temps[0];
                toMonth = Integer.valueOf(month);
                getData(chart, option, fromMonth, toMonth, year, listViewTrend);
                getDataPie(chartPie, option, fromMonth, toMonth, year, listViewTrendPie);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getData(BarChart chart, int option, int monthBegin, int monthEnd, String year,ListView listView){
        ArrayList<String>xAxis = new ArrayList<>();
        for (int m = monthBegin; m <=monthEnd; m++){
            xAxis.add(Integer.toString(m));
        }

        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSetY = new ArrayList<>();
        ArrayList<Trend> trends = transactionController.getAmountTrendByMonths(option, Utils.getWallet_id(), monthBegin, monthEnd, year);
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
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        Utils.ListUtils.setDynamicHeight(listView);
    }

    private void getDataPie(PieChart chart, int option, int monthBegin, int monthEnd, String year,ListView listView){
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        if (option != Constant.TREND_TYPE_BALANCE) {
            trans = transactionController.getAmountCategoryTypeByMonths(option, Utils.getWallet_id(), monthBegin, monthEnd, year);
        }
        setData(chart,trans);
        listView.setAdapter(new TransactionSumAdapter(this, trans));
        Utils.ListUtils.setDynamicHeight(listView);
    }

    private void setData(PieChart chart, ArrayList<TransactionSum> trans){

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < trans.size(); i++)
            yVals.add(new Entry(trans.get(i).getAmount(), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < trans.size(); i++)
            xVals.add(trans.get(i).getCatName());

        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setDrawValues(false);


        PieData data = new PieData(xVals, pieDataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        chart.setData(data);
        decorationChart(chart, pieDataSet);

    }

    private void decorationChart(PieChart chart,PieDataSet dataSet){

        chart.getLegend().setEnabled(false);
        chart.setDescription("");
        chart.animateY(800, Easing.EasingOption.EaseInBounce);
        chart.setTouchEnabled(true);
        //chart.setDrawCenterText(false);
        //chart.setDrawSliceText(true);
        chart.setUsePercentValues(true);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColorTransparent(true);
        chart.setHoleRadius(7);
        chart.setTransparentCircleRadius(10);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);



        //chart.setOnChartValueSelectedListener(context);
        chart.invalidate(); // refresh
        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(transactionController!=null){
            transactionController.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(transactionController!=null){
            transactionController.open();
        }
    }
}
