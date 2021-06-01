package com.example.arcusapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button arcusAccount,arcusbillers,arcusbillersList,getBillerdirectory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arcusAccount=findViewById(R.id.arcusAccount);
        arcusAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AccountInformation.class));
            }
        });

        arcusbillers=findViewById(R.id.arcusbillers);
        arcusbillers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DataBillers.class));
            }
        });


        arcusbillersList=findViewById(R.id.arcusbillersList);
        arcusbillersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GetBillList.class));
            }
        });

        getBillerdirectory=findViewById(R.id.getBillerdirectory);
        getBillerdirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GetBillerDirectory.class));
            }
        });
    }
}