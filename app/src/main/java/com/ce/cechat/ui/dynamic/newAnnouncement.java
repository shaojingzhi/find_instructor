package com.ce.cechat.ui.dynamic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ce.cechat.R;

public class newAnnouncement extends AppCompatActivity {

    private EditText new_title;
    private EditText new_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_announcement);


        new_title = findViewById(R.id.new_title);
        new_content = findViewById(R.id.new_content);
        final Button new_announcement = findViewById(R.id.new_announcement);

        new_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(new_content.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    replyIntent.putExtra("content", new_content.getText().toString());
                    replyIntent.putExtra("title", new_title.getText().toString());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}