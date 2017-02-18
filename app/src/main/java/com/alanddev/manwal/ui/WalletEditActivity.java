package com.alanddev.manwal.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyTextWatcher;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.model.Wallet;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WalletEditActivity extends AppCompatActivity {

    WalletController walletController;
    TransactionController transactionController;
    Utils utils;
    // Full path of image
    String imagePath = "";
    int walletId;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_wallet_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_wallet_edit));
        transactionController = new TransactionController(this);
        transactionController.open();
        walletController = new WalletController(this);
        walletController.open();
        utils = new Utils();
        EditText amountEdit   = (EditText)findViewById(R.id.txtAmount);
        amountEdit.addTextChangedListener(new CurrencyTextWatcher(amountEdit));

        getData();

       /* mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_wallet, menu);
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
        if (id == R.id.save){
            saveWallet();
        }

        return super.onOptionsItemSelected(item);
    }


    public void saveWallet() {
        EditText nameEdit   = (EditText)findViewById(R.id.txtName);
        EditText currEdit   = (EditText)findViewById(R.id.txtCurrency);
        EditText amountEdit   = (EditText)findViewById(R.id.txtAmount);
        CheckBox chooseCB = (CheckBox)findViewById(R.id.choose);


        String nameWallet = nameEdit.getText().toString();
        String currency = currEdit.getText().toString();
        float amount = 0.0f;
        String sAmount = amountEdit.getText().toString();

        if (!sAmount.equals("")|| !sAmount.equals("0")) {
//            sAmount = sAmount.replaceAll(",", "");
            sAmount = Utils.getFormatCurrency(sAmount);
            amount = Float.valueOf(sAmount);
        }


        if (nameWallet.equals("")){
            Toast.makeText(this,getResources().getString(R.string.warning_wallet_name),Toast.LENGTH_LONG).show();
        } else if (currency.equals("")){
            Toast.makeText(this,getResources().getString(R.string.warning_wallet_cur),Toast.LENGTH_LONG).show();
        }else {
            //db.createWallet();
            walletController = new WalletController(getApplicationContext());
            walletController.open();

            Wallet editWallet = walletController.getId(walletId) ;
            editWallet.setName(nameWallet);
            editWallet.setAmount(amount);
            editWallet.setCurrency(currency);
            String imageFileName = "";
            if (!imagePath.equals("") && !imagePath.equals(editWallet.getImage())) {
                File image = new File(imagePath);
                imageFileName = image.getName();
                try {
                    utils.copyFile(imagePath, Constant.PATH_IMG + "/" + imageFileName);
                }
                catch (IOException e){};
                editWallet.setImage(imageFileName);
            }
            walletController.update(editWallet);

            if (chooseCB.isChecked()){
                utils.setSharedPreferencesValue(this,Constant.WALLET_ID,walletId);
                Utils.setWallet_id(walletId);
            }

            float amountWallet = transactionController.getAmountByWallet(walletId);
            // new - old
            amountWallet = amount - amountWallet ;

            if (amountWallet < 0) {
                transactionController.createTransactionDefault(this,walletId, amountWallet, Constant.CAT_WALLET_ADD_EXPENSE,getResources().getString(R.string.title_transaction_wallet_edit));
            }else{
                transactionController.createTransactionDefault(this,walletId, amountWallet, Constant.CAT_WALLET_ADD_INCOME,getResources().getString(R.string.title_transaction_wallet_edit));
            }
            transactionController.close();
            walletController.close();
            finish();
        }
    }

    @Override
    protected void onResume() {
        if(walletController!=null) {
            walletController.open();
        }
        if(transactionController!=null) {
            transactionController.open();
        }
        //mAdView.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(walletController!=null) {
            walletController.close();
        }
        if(transactionController!=null) {
            transactionController.close();
        }
        //mAdView.pause();
        super.onPause();
    }

    public void getData(){
        Bundle b = getIntent().getExtras();
        walletId = b.getInt("wallet_id", 0);
        String name = b.getString("wallet_name");
        String currency = b.getString("wallet_currency");
        String imagePath = b.getString("wallet_image");
        double amount = b.getDouble("wallet_amount", 0.0);
        int choose = b.getInt("wallet_choose", 0);

        ImageView imgWallet = (ImageView)findViewById(R.id.imageWallet);
        if (!imagePath.equals("")){
            imgWallet.setImageBitmap(BitmapFactory.decodeFile(Constant.PATH_IMG + "/" + imagePath));
        }else{
            imgWallet.setImageResource(R.mipmap.wallet);
        }


        TextView tvCurrency = (TextView) findViewById(R.id.txtCurrency);
        tvCurrency.setText(currency);

        CheckBox chooseCB = (CheckBox)findViewById(R.id.choose);
        chooseCB.setChecked(choose==1?true:false);

        ImageView imageCurrency = (ImageView) findViewById(R.id.imageCurrency);
        Resources res = getResources();
        String srcImg = "ic_currency_" + currency.toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getPackageName());
        Drawable image = getResources().getDrawable(id);
        imageCurrency.setImageDrawable(image);


        TextView tvName = (TextView) findViewById(R.id.txtName );
        tvName.setText(name);

        TextView tvAmount = (TextView) findViewById(R.id.txtAmount );
        amount= transactionController.getAmountByWallet(walletId);
        tvAmount.setText(Double.toString(amount));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
        //if (requestCode == Constant.CUR_WALLET_REQUEST) {
            case Constant.CUR_WALLET_REQUEST:
                if (data != null) {
                    String name = data.getStringExtra("cur_code");
                    String code = data.getStringExtra("cur_code");

                    ImageView imageCurrency = (ImageView) findViewById(R.id.imageCurrency);
                    Resources res = getResources();
                    String srcImg = "ic_currency_" + code.toLowerCase();
                    int id = res.getIdentifier(srcImg, "mipmap", getPackageName());
                    Drawable image = getResources().getDrawable(id);
                    imageCurrency.setImageDrawable(image);

                    TextView tvName = (TextView) findViewById(R.id.txtCurrency);
                    tvName.setText(name);
                }
                break;
            case Constant.GALLERY_WALLET_REQUEST :
                if (data !=null){
                    Uri selectedImageUri = data.getData();
                    imagePath = utils.getRealPathFromURI(this,selectedImageUri);
                    //Toast.makeText(this,path,Toast.LENGTH_LONG).show();
                    File image = new File(imagePath);
                    ImageView imgWallet = (ImageView)findViewById(R.id.imageWallet);
                    imgWallet.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                }
                break;
            default:
                break;
        }
    }


    public void onClickCurrency(View v){
        Intent intent = new Intent(this, ListCurrencyActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, Constant.CUR_WALLET_REQUEST);
        //finish();
    }


    public void clickChooseImage(View v){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent , Constant.GALLERY_WALLET_REQUEST );
    }

    @Override
    protected void onDestroy() {
        //mAdView.destroy();
        super.onDestroy();
    }




}
