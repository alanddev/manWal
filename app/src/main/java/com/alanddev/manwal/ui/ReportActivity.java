package com.alanddev.manwal.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.TransactionSumAdapter;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.TransactionSum;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    private TransactionController transactionController;
    private Date dateReport;
    private int typeReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_report);
//        ScrollView sv = new ScrollView(this);
//        sv.addView()
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transactionController = new TransactionController(this);
        transactionController.open();

        PieChart chartExpense = (PieChart)findViewById(R.id.report_chart_expense);
        PieChart chartIncome = (PieChart)findViewById(R.id.report_chart_income);
        //Date today = new Date();
        getData();


        ArrayList<TransactionSum> transExpense = new ArrayList<TransactionSum>();
        transExpense = getTransactions(Constant.CAT_TYPE_EXPENSE,typeReport,dateReport);
        setData(chartExpense,transExpense);
        ListView listViewTransExpense = (ListView)findViewById(R.id.list_transaction_expense);
        listViewTransExpense.setAdapter(new TransactionSumAdapter(this, transExpense));


        ArrayList<TransactionSum> transIncome = new ArrayList<TransactionSum>();
        transIncome = getTransactions(Constant.CAT_TYPE_INCOME,typeReport,dateReport);
        setData(chartIncome, transIncome);
        ListView listViewTransIncome = (ListView)findViewById(R.id.list_transaction_income);
        listViewTransIncome.setAdapter(new TransactionSumAdapter(this,transIncome));


        Utils.ListUtils.setDynamicHeight(listViewTransExpense);
        Utils.ListUtils.setDynamicHeight(listViewTransIncome);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
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
        if (id == R.id.save) {
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        Bundle b = getIntent().getExtras();
        if (b!=null) {
            typeReport = b.getInt(Constant.VIEW_TYPE, 0);
            String dateStr = b.getString(Constant.PUT_EXTRA_DATE);
            dateReport = Utils.changeStr2Date(dateStr, Constant.DATE_FORMAT_DB);
            Log.d("AAAAAAAAA", typeReport + " " + dateStr);
        }else{
            typeReport = Constant.VIEW_TYPE_DAY;
            dateReport = new Date();
        }
    }


    private ArrayList<TransactionSum>getTransactions(int type, int option, Date date){
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        switch (option){
            case Constant.VIEW_TYPE_DAY:
                trans = transactionController.getAmountCategoryTypeByDate(type, Utils.getWallet_id(), date);
                break;
            case Constant.VIEW_TYPE_WEEK:
                trans = transactionController.getAmountCategoryTypeByWeek(type, Utils.getWallet_id(), date);
                break;
            case Constant.VIEW_TYPE_MONTH:
                trans = transactionController.getAmountCategoryTypeByMonth(type, Utils.getWallet_id(), date);
                break;
            case Constant.VIEW_TYPE_YEAR:
                trans = transactionController.getAmountCategoryTypeByMonth(type,Utils.getWallet_id(),date);
                break;

        }
        return trans;
    }


    private void setData(PieChart chart, ArrayList<TransactionSum> trans){
        //float[] yData = { 5, 10, 15, 30, 40 };
        //String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };
        //String[] xData = { "ic_category", "Huawei", "LG", "Apple", "Samsung" };

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < trans.size(); i++)
            yVals.add(new Entry(trans.get(i).getAmount(), i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < trans.size(); i++)
            xVals.add(trans.get(i).getCatName());

        PieDataSet pieDataSet = new PieDataSet(yVals, "expenses");
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


}
