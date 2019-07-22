package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.R;

public class PhotoType extends AppCompatActivity {
boolean showButton=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_type);
        Button type1 = findViewById(R.id.type_single);
        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoType.this,PhotoPass.class);
                startActivity(intent);
            }
        });
        Button type2=findViewById(R.id.type_whole);
        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳出动画
                showButton();
            }
        });
        Button plate_c=findViewById(R.id.plate_c);
        plate_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoType.this,PhotoPassm.class);
                intent.putExtra("plate_type","圆盘食物");
                startActivity(intent);
            }
        });
        Button plate_s=findViewById(R.id.plate_s);
        plate_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoType.this,PhotoPassm.class);
                intent.putExtra("plate_type","方盘食物");
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

public void showButton(){
        Button x1=findViewById(R.id.plate_c);
        Button x2=findViewById(R.id.plate_s);
        if(showButton==false){
            x1.setVisibility(View.VISIBLE);
            x2.setVisibility(View.VISIBLE);
            showButton=true;
        }
        else{
            x1.setVisibility(View.INVISIBLE);
            x2.setVisibility(View.INVISIBLE);
            showButton=false;
        }
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
