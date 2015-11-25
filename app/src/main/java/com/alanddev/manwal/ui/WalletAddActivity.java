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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.model.Wallet;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.io.File;
import java.io.IOException;

public class WalletAddActivity extends AppCompatActivity {

    WalletController walletController;
    Utils utils;
    // Full path of image
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        walletController = new WalletController(this);
        walletController.open();
        utils = new Utils();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id==android.R.id.home){
            finish();
        }
        if (id == R.id.save){
            saveWallet();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * return Main Activity if user click back button;
     */

    public void returnMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void saveWallet() {
        EditText nameEdit   = (EditText)findViewById(R.id.txtName);
        EditText currEdit   = (EditText)findViewById(R.id.txtCurrency);
        EditText amountEdit   = (EditText)findViewById(R.id.txtAmount);

        String imageFileName = "";
        String nameWallet = nameEdit.getText().toString();
        String currency = currEdit.getText().toString();
        Double amount = 0.0;
        if (!amountEdit.getText().toString().equals("")) {
            amount = Double.parseDouble(amountEdit.getText().toString());
        }

        if (!imagePath.equals("")) {
            File image = new File(imagePath);
            imageFileName = image.getName();
            try {
                utils.copyFile(imagePath, Constant.PATH_IMG + "/" + imageFileName);
                }
            catch (IOException e){};

        }

        if (nameWallet.equals("")){
            Toast.makeText(this,"Chua co ten vi",Toast.LENGTH_LONG).show();
        } else if (currency.equals("")){
            Toast.makeText(this,"Chua chon loai dong tien",Toast.LENGTH_LONG).show();
        }else if (amount == 0.0){
            Toast.makeText(this,"Chua nhap so luong tien trong vi",Toast.LENGTH_LONG).show();
        }else {
            Wallet newWallet = new Wallet(nameWallet, amount, currency, imageFileName);
            //db.createWallet();
            walletController = new WalletController(getApplicationContext());
            walletController.open();
            walletController.create(newWallet);
            //returnMainActivity();
            finish();
        }
    }

    @Override
    protected void onResume() {
        if(walletController!=null) {
            walletController.open();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(walletController!=null) {
            walletController.close();
        }
        super.onPause();
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



}
