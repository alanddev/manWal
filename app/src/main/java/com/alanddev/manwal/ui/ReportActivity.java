package com.alanddev.manwal.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    public static final int REPORT_BY_DATE = 1;
    public static final int REPORT_BY_WEEK = 2;
    public static final int REPORT_BY_MONTH = 3;

    TransactionController transactionController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Date today = new Date();

        ArrayList<TransactionSum> transExpense = new ArrayList<TransactionSum>();
        transExpense = getTransactions(Constant.CAT_TYPE_EXPENSE,REPORT_BY_DATE,today);
        setData(chartExpense,transExpense);
        ListView listViewTransExpense = (ListView)findViewById(R.id.list_transaction_expense);
        listViewTransExpense.setAdapter(new TransactionSumAdapter(this, transExpense));


        ArrayList<TransactionSum> transIncome = new ArrayList<TransactionSum>();
        transIncome = getTransactions(Constant.CAT_TYPE_INCOME,REPORT_BY_DATE,today);
        setData(chartIncome, transIncome);
        ListView listViewTransIncome = (ListView)findViewById(R.id.list_transaction_income);
        listViewTransIncome.setAdapter(new TransactionSumAdapter(this,transIncome));


        ListUtils.setDynamicHeight(listViewTransExpense);
        ListUtils.setDynamicHeight(listViewTransIncome);


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

    private ArrayList<TransactionSum>getTransactions(int type, int option, Date date){
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        switch (option){
            case REPORT_BY_DATE:
                trans = transactionController.getAmountCategoryTypeByDate(type, Utils.getWallet_id(), date);
                break;
            case REPORT_BY_WEEK:
                trans = transactionController.getAmountCategoryTypeByWeek(type, Utils.getWallet_id(), date);
                break;
            case REPORT_BY_MONTH:
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

        chart.setData(data);
        decorationChart(chart);

    }


    private void decorationChart(PieChart chart){
        chart.getLegend().setEnabled(false);
        chart.setDescription("");
        chart.animateY(800, Easing.EasingOption.EaseInBounce);
        chart.setTouchEnabled(true);
        chart.setDrawCenterText(false);
        chart.setDrawSliceText(true);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColorTransparent(true);
        chart.setHoleRadius(7);
        chart.setTransparentCircleRadius(10);
        //chart.setOnChartValueSelectedListener(context);
        chart.invalidate(); // refresh
        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }


    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }
}
