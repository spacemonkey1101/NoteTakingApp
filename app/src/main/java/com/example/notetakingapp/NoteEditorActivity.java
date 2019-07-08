package com.example.notetakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
          noteId = intent.getIntExtra("noteId",-1);

        if(noteId!=-1) {
            editText.setText(MainActivity.notes.get(noteId));
        }
        else {
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        //save notes when new things are added
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteId,s.toString());
                MainActivity.arrayAdapter.notifyDataSetChanged();
                //save data when text is changed
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("package com.example.notetakingapp" , Context.MODE_PRIVATE);
                //creating a Hashset to work with from the arrayList in MainActivity
                HashSet<String> hashSet = new HashSet<>(MainActivity.notes);
                //store it in shared preferences
                sharedPreferences.edit().putStringSet("notes",hashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
