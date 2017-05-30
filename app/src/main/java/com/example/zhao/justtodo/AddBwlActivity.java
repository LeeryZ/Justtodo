package com.example.zhao.justtodo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Zhao on 2017/5/29.
 */

public class AddBwlActivity extends AppCompatActivity {
    private EditText etTitle=null,etContent=null;
    private Button btnSave=null;
    private DbHelper dbhelper;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bwl);
        dbhelper = new DbHelper(this, "db_bwl", null, 1);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues value = new ContentValues();
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                value.put("title",title);
                value.put("content",content);
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                long id = 0;
                long status = 0;

                if(bundle!=null){
                    id=bundle.getLong("_id");
                    status=db.update("tb_bwl",value,"_id=?",
                            new String[]{bundle.getLong("_id")+""});
                }else{
                    status=db.insert("tb_bwl",null,value);
                    id=status;
                }

                if(status!=-1){
                    Toast.makeText(AddBwlActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AddBwlActivity.this,"保存失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        bundle=this.getIntent().getExtras();
        if(bundle!=null){
            etTitle.setText(bundle.getString("title"));
            etContent.setText(bundle.getString("content"));
        }
    }
}
