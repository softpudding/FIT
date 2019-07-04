package com.example.FIT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.FIT.R;

public class RecordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_record);
        Button myReturn = (Button) findViewById(R.id.record_return);

        myReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(RecordDetailActivity.this, MainActivity.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });


    }

}
