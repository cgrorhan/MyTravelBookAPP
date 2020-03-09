package com.cagriorhan.mytravelbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import static com.cagriorhan.mytravelbook.MapsActivity.database;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> nameList=new ArrayList<>();
    static ArrayList<LatLng> locationList=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=findViewById(R.id.listView);


        try {
            MapsActivity.database=this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            Cursor cursor=MapsActivity.database.rawQuery("SELECT * FROM places",null);
            int nameIx=cursor.getColumnIndex("name");
            int latitudeIx=cursor.getColumnIndex("latitude");
            int longtitudeIx=cursor.getColumnIndex("longtitude");

            while(cursor.moveToNext()){
                String nameFromDb=cursor.getString(nameIx);
                String latitudeFromDb=cursor.getString(latitudeIx);
                String longtitudeFromDb=cursor.getString(longtitudeIx);
                nameList.add(nameFromDb);

                Double l1=Double.parseDouble(latitudeFromDb);
                Double l2=Double.parseDouble(longtitudeFromDb);

                LatLng location=new LatLng(l1,l2);
                locationList.add(location);
            }
            cursor.close();
        }catch (Exception e){

        }
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("position",position);


                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_place,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_place){
            Intent intent=new Intent(this,MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
