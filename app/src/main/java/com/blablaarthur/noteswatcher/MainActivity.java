package com.blablaarthur.noteswatcher;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteAdapter notesAdapter;
    ListView notesListView;
    List<Note> notes = new ArrayList<Note>(0);
    int PERMISSION_REQUEST_CODE = 5;

    final Uri NOTES_URI = Uri.parse("content://com.blablaarthur.lab2.NotesProvider/Notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesAdapter = new NoteAdapter(this, notes);
        notesListView = (ListView) findViewById(R.id.notesListView);
        notesListView.setAdapter(notesAdapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    },
                    PERMISSION_REQUEST_CODE);

        }

        Calendar cal = Calendar.getInstance();



        Cursor cursor = getContentResolver().query(NOTES_URI, null, "datetime LIKE ?", new String[]{"%" + getDate(cal) + "%"}, null);
        while (cursor.moveToNext()) {
            Note n = new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5));
            notes.add(n);
        }
        notesAdapter.notifyDataSetChanged();
    }

    public static String getDate(Calendar calendar){
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        if(day.length() == 1)
            day = "0" + day;
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        if(month.length() == 1)
            month = "0" + month;
        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        if(hour.length() == 1)
            hour = "0" + hour;
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));
        if(minute.length() == 1)
            minute = "0" + minute;

        return  day + "." + month + "."
                + calendar.get(Calendar.YEAR);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        recreate();
    }
}
