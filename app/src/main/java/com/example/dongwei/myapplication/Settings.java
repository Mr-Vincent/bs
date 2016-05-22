package com.example.dongwei.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity  {
    public Toolbar mToolbar;
    private EditText ip;
    private Button bt_save;
    private String ip_input;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_settings);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        ip = (EditText) findViewById(R.id.ip_address);
        bt_save = (Button) findViewById(R.id.save);
        setSupportActionBar(mToolbar);
        //2个都为true才能显示返回键
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sp = getSharedPreferences("ip_address", Context.MODE_PRIVATE);
        if(sp!=null){
            String ip_address_sp = sp.getString("ip_address", "");
            if(!TextUtils.isEmpty(ip_address_sp)){
                ip.setText(ip_address_sp);
            }else{
                ip_input = ip.getText().toString();
            }
        }
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = sp.edit().putString("ip_address", ip_input).commit();
                if(flag){
                    Toast.makeText(Settings.this, "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
