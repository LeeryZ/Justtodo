package com.example.zhao.justtodo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DbHelper dbhelper;
    private SQLiteDatabase db;
    SimpleCursorAdapter adapter = null;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbhelper=new DbHelper(this, "db_bwl", null, 1);
        db = dbhelper.getReadableDatabase();
        Cursor cursor=db.query("tb_bwl", new String[]{"_id","title","content"}, null, null, null,null,null);
        listView=(ListView)findViewById(R.id.listview_bwllist);
        adapter=new SimpleCursorAdapter(this, R.layout.list_item_bwl, cursor,new String[]{"title","content"},
         new int[]{R.id.title,R.id.content});
        listView.setAdapter(adapter);
        this.registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView)view.findViewById(R.id.title)).getText().toString();
                String content=((TextView)view.findViewById(R.id.content)).getText().toString();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,AddBwlActivity.class);
                Bundle bundle=new Bundle();
                bundle.putLong("_id",id);
                bundle.putString("title",title);
                bundle.putString("content",content);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Button addbtn=(Button)findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddBwlActivity.class);
                startActivity(intent);
            }
        });
    }//        menu.setHeaderIcon(R.drawable.alarm);

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,3,0,"修改");
        menu.add(0,4,0,"删除");
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case 3:
                String title=((TextView)menuInfo.targetView.findViewById(R.id.title)).getText().toString();
                String content=((TextView)menuInfo.targetView.findViewById(R.id.content)).getText().toString();
                Intent intent=new Intent();
                intent.setClass(this,AddBwlActivity.class);
                Bundle bundle=new Bundle();
                bundle.putLong("_id",menuInfo.id);
                bundle.putString("title",title);
                bundle.putString("content",content);
                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case 4:
                dbhelper=new DbHelper(this,"db_bwl",null,1);
                db=dbhelper.getWritableDatabase();
                int status=db.delete("tb_bwl","_id=?",new String[]{""+ menuInfo.id});
                if(status!=-1){
                    Cursor cursor=db.query("tb_bwl",new String[]{"_id","title","content"},null,null,null,null,null);
                    adapter.changeCursor(cursor);
                    Toast.makeText(this,"删除成功",Toast.LENGTH_LONG).show();
                }
                else
                Toast.makeText(this,"删除失败",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }
    @Override
    protected void onResume(){
        super.onResume();
        Cursor cursor=db.query("tb_bwl",new String[]{"_id","title","content"},null,null,null,null,null);
        adapter.changeCursor(cursor);
    }
}
