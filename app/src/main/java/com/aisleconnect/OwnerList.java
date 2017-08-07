package com.aisleconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OwnerList extends AppCompatActivity {
    private int requestCodeForLogin = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_list);
        CheckLogin();
    }
    private void CheckLogin(){
        if(GlobalData.getInstance().Logindata.length() == 0){
            //never login turn
            Intent i = new Intent(this,MainActivity.class);
            this.startActivityForResult(i,requestCodeForLogin);
        }
    }
}
