package com.blablaarthur.noteswatcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Артур on 16.11.2016.
 */

class NoteAdapter extends ArrayAdapter<Note> {

    List<Note> notes;
    //ItemFilter mFilter = new ItemFilter();
    Context c;
    LayoutInflater notesInflater;

    public NoteAdapter(Context context, List<Note> notes) {
        super(context, R.layout.note_list_element, notes);
        c = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (notesInflater == null) {
            notesInflater = LayoutInflater.from(getContext());
        }
        View myView = notesInflater.inflate(R.layout.note_list_element, parent, false);

        Note item = getItem(position);
        TextView title = (TextView) myView.findViewById(R.id.titleTextView);
        TextView datetime = (TextView) myView.findViewById(R.id.dateTextView);
        EditText desc = (EditText) myView.findViewById(R.id.editText);

        ImageView image = (ImageView) myView.findViewById(R.id.noteImage);
        ImageView imp = (ImageView) myView.findViewById(R.id.impImage);

        title.setText(item.Title);

        desc.setText(item.Description);
        desc.setFocusable(false);

        datetime.setText(item.DateTime);

        switch (item.Importance) {
            case 0:
                imp.setImageResource(R.drawable.red_dot);
                break;
            case 1:
                imp.setImageResource(R.drawable.orange_dot);
                break;
            case 2:
                imp.setImageResource(R.drawable.green_dot);
        }

        if (item.Image.equals("")) {
            image.setImageBitmap(BitmapLoader.decodeBitmapFromResource(getContext().getResources(), R.drawable.image, 60, 60));
        } else {
            image.setImageBitmap(BitmapLoader.decodeBitmapFromPath(item.Image, 60, 60));
        }
        return myView;

    }
}
