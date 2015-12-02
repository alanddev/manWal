package com.alanddev.manwal.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.BudgetController;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtStartDate = (TextView)findViewById(R.id.txt_startdt);
        txtStartDate.setOnClickListener(this);
        txtEndDate = (TextView)findViewById(R.id.txt_enddt);
        txtEndDate.setOnClickListener(this);
        txtCate = (TextView)findViewById(R.id.txt_catename);
        txtCate.setOnClickListener(this);
        edtAmout = (EditText)findViewById(R.id.edt_amt);
        imgCate = (ImageView)findViewById(R.id.img_cate);

        Bundle bundle = getIntent().getExtras();
        long budgetId=0;
        if(bundle!=null){
            budgetId = bundle.getLong(MwSQLiteHelper.COLUMN_BUDGET_ID,0);
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
            budgetController = new BudgetController(this);
            budgetController.open();
            budget = budgetController.getBudgetById(budgetId);
            budgetController.close();
            txtStartDate.setText(Utils.changeDateStr2Str2(budget.getStartdate()));
            txtEndDate.setText(Utils.changeDateStr2Str2(budget.getEnddate()));
            txtCate.setText(budget.getCate_name());
            edtAmout.setText(budget.getAmount()+"");
            imgCate.setImageResource(getResources().getIdentifier("ic_category_" + budget.getCate_img(), "mipmap", getPackageName()));
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
            saveBudget();
        }

        if (id == R.id.action_delete) {
            openConfirmDialog();
        }

        if (id == R.id.action_edit) {
            if(true) {
                saveBudget();
                setResult(Constant.TRANS_DETAIL_UPDATE);
                finish();
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

    private void saveBudget() {
        String amount = edtAmout.getText().toString();
        if(amount.equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_amout_exist), Toast.LENGTH_LONG).show();
        }else if(Float.valueOf(amount)<=0){
            Toast.makeText(this, getResources().getText(R.string.check_amount_zero), Toast.LENGTH_LONG).show();
        }else {
            BudgetController controller = new BudgetController(this);
            controller.open();
            Budget budget = new Budget();
            budget.setAmount(Float.valueOf(edtAmout.getText().toString()));
            budget.setCate_id(category.getId());
            budget.setStartdate(Utils.changeDateStr2Str(txtStartDate.getText().toString()));
            budget.setEnddate(Utils.changeDateStr2Str(txtEndDate.getText().toString()));
            budget.setWallet_id(Utils.getWallet_id());
            Log.d("AAAAAAA", budget.getCate_name() + " " + budget.getCate_id() + " " + budget.getStartdate() + " ");
            controller.create(budget);
            controller.close();
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
                setResult(Constant.TRANS_DETAIL_UPDATE);
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
}
