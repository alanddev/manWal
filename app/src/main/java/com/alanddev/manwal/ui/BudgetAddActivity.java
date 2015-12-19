package com.alanddev.manwal.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyTextWatcher;
import com.alanddev.manwal.controller.BudgetController;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class BudgetAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtCate;
    private EditText edtAmout;
    private int choice=0;
    private Category category;
    private ImageView imgCate;
    private BudgetController budgetController;
    private Budget budget;
    private final int CREATE = 0;
    private final int EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_budget_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtStartDate = (TextView)findViewById(R.id.txt_startdt);
        txtStartDate.setOnClickListener(this);
        txtEndDate = (TextView)findViewById(R.id.txt_enddt);
        txtEndDate.setOnClickListener(this);
        txtCate = (TextView)findViewById(R.id.txt_catename);
        txtCate.setOnClickListener(this);
        edtAmout = (EditText)findViewById(R.id.edt_amt);
        edtAmout.addTextChangedListener(new CurrencyTextWatcher(edtAmout));

        imgCate = (ImageView)findViewById(R.id.img_cate);
        budgetController = new BudgetController(this);
        Bundle bundle = getIntent().getExtras();
        int budgetId=0;
        if(bundle!=null){
            budgetId = bundle.getInt(MwSQLiteHelper.COLUMN_BUDGET_ID, 0);
        }
        if(budgetId==0){
            CategoryController categoryController = new CategoryController(this);
            categoryController.open();
            category = categoryController.getByName(txtCate.getText().toString());
            categoryController.close();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            txtStartDate.setText(Utils.changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_PICKER));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            txtEndDate.setText(Utils.changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_PICKER));
        }else{
            budgetController.open();
            budget = budgetController.getBudgetById(budgetId);
            budgetController.close();
            txtStartDate.setText(Utils.changeDateStr2Str2(budget.getStartdate()));
            txtEndDate.setText(Utils.changeDateStr2Str2(budget.getEnddate()));
            txtCate.setText(budget.getCate_name());
            edtAmout.setText(budget.getAmount()+"");
            imgCate.setImageResource(getResources().getIdentifier("ic_category_" + budget.getCate_img(), "mipmap", getPackageName()));
            category = new Category();
            category.setId(budget.getCate_id());
            category.setName(budget.getCate_name());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(budget==null) {
            getMenuInflater().inflate(R.menu.menu_add, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_trans_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        if (id == R.id.save) {
            saveBudget(CREATE);
        }

        if (id == R.id.action_delete) {
            openConfirmDialog();
        }

        if (id == R.id.action_edit) {
            if(changeData()) {
                saveBudget(EDIT);
            }else{
                Toast.makeText(this, getResources().getText(R.string.check_change_data), Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==txtCate){
            Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
            intent.putExtra(Constant.PUT_EXTRA_BUDGET,true);
            startActivityForResult(intent, Constant.PICK_CATEGORY);
        }
        if(v==txtStartDate||v==txtEndDate){
            choice=v.getId();
            showDatePickerDialog(v);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        if(choice==R.id.txt_startdt){
            txtStartDate.setText(dateStr);
        }else if(choice==R.id.txt_enddt){
            txtEndDate.setText(dateStr);
        }

    }
    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                BudgetAddActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.PICK_CATEGORY)
        {
            if(data!=null) {
                category = new Category();
                String cateName = data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_NAME);
                category.setName(cateName);
                category.setId(data.getIntExtra(MwSQLiteHelper.COLUMN_CATE_ID, 0));
                txtCate.setText(cateName);
                String cateimg = data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_IMG);
                imgCate.setImageResource(getResources().getIdentifier("ic_category_" + cateimg, "mipmap", getPackageName()));
            }
        }
    }

    private void saveBudget(int type) {
        String amount = edtAmout.getText().toString();
        if (!amount.equals("")|| !amount.equals("0")) {
            amount = amount.replaceAll(",", "");
        }

        if(amount.equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_amout_exist), Toast.LENGTH_LONG).show();
        }else if(Float.valueOf(amount)<=0){
            Toast.makeText(this, getResources().getText(R.string.check_amount_zero), Toast.LENGTH_LONG).show();
        }else {
            Date startDt = Utils.changeStr2Date(txtStartDate.getText().toString(),Constant.DATE_FORMAT_PICKER);
            Date endDt = Utils.changeStr2Date(txtEndDate.getText().toString(),Constant.DATE_FORMAT_PICKER);
            if(endDt.compareTo(startDt)>0) {
                budgetController.open();
                Budget newBudget = new Budget();
                newBudget.setAmount(Float.valueOf(amount));
                newBudget.setCate_id(category.getId());
                newBudget.setStartdate(Utils.changeDateStr2Str(txtStartDate.getText().toString()));
                newBudget.setEnddate(Utils.changeDateStr2Str(txtEndDate.getText().toString()));
                newBudget.setWallet_id(Utils.getWallet_id());
                if (type == CREATE) {
                    budgetController.create(newBudget);
                } else if (type == EDIT) {
                    newBudget.setId(budget.getId());
                    budgetController.update(newBudget);
                }
                budgetController.close();
            }else{
                Toast.makeText(this, "Ngày bắt đầu trước ngày kết thúc", Toast.LENGTH_LONG).show();
            }
        }
        setResult(Constant.BUDGET_ADD_RESULT, new Intent());
        finish();
    }

    public void openConfirmDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.dialog_confirm_delete_trans));

        alertDialogBuilder.setPositiveButton(getString(R.string.dialog_yes_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                delete(budget.getId());
                setResult(Constant.BUDGET_ADD_RESULT, new Intent());
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.dialog_no_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //don't do anything
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

   private void delete(long budgetId){
        budgetController.open();
        budgetController.delete(budgetId);
        budgetController.close();
    }

    private Boolean changeData(){
        Float amount = Float.valueOf(edtAmout.getText().toString().replaceAll(",", ""));
        String catename = txtCate.getText().toString();
        String startDt = Utils.changeDateStr2Str(txtStartDate.getText().toString());
        String endDt = Utils.changeDateStr2Str(txtEndDate.getText().toString());
        if(amount.compareTo(budget.getAmount())==0&&catename.equals(budget.getCate_name())
                &&startDt.equals(budget.getStartdate())&&endDt.equals(budget.getEnddate())){
            return false;
        }
        return true;
    }
}
