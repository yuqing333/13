package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private List<Map<String,Object>> list;

    private List<Item> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView)findViewById(R.id.lv_1);
//        initDataList();
//        String []datafrom={"number","img","username","energy"};
//        int[] datato={R.id.number,R.id.img,R.id.username,R.id.energy};
//        final SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.layout,datafrom,datato);
//        lv.setAdapter(adapter);
//
        mDataList = mockData();
       MyAdapter myAdapter= new MyAdapter();
       lv.setAdapter(myAdapter);
    }

    private List<Item> mockData(){
        List<Item> data = new ArrayList<>(1);
        Item item = new Item();
        item.has = true;
        item.num = 1;
        item.name = "Name";
        item.weight = "10.2kg";
        data.add(item);
        return data;
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int i) {
            return mDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout,viewGroup,false);
            }
            TextView numberTv = view.findViewById(R.id.number);
            TextView userNameTv = view.findViewById(R.id.username);
            TextView energyTv = view.findViewById(R.id.energy);

            Item item = mDataList.get(i);
            numberTv.setText(String.valueOf(item.num));
            userNameTv.setText(item.name);
            energyTv.setText(item.weight);
            return view;
        }
    }

    private static class Item {
        private int num;
        private String name;
        private String weight;
        private boolean has;
    }
}
