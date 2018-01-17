package com.example.administrator.testapplication;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity1 extends Activity {
    private ListView listView;
    private List<Map<String, String>> mp = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        listView = (ListView) findViewById(R.id.listview);
        simpleAdapter = new SimpleAdapter(this, mp, R.layout.contacts_list_layout, new String[]{"name", "phone"}, new int[]{R.id.tv_contact_name, R.id.tv_contact_phone});
        listView.setAdapter(simpleAdapter);

        //
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            Uri uriData = Uri.parse("content://com.android.contacts/raw_contacts/" + id + "/data");
            Cursor contactData = getContentResolver().query(uriData, null, null, null, null);
            String name = "";
            String phone = "";
            Map<String, String> map = new HashMap<>();
            while (contactData.moveToNext()) {
                String type = contactData.getString(contactData.getColumnIndex("mimetype"));
                if (type.equals("vnd.android.cursor.item/phone_v2")) {
                    phone = contactData.getString(contactData.getColumnIndex("data1"));
                } else if (type.equals("vnd.android.cursor.item/name")) {
                    name = contactData.getString(contactData.getColumnIndex("data1"));
                }
            }
            map.put("name", name);
            map.put("phone", phone);
            mp.add(map);
        }
    }
}
