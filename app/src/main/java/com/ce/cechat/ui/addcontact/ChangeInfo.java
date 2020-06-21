package com.ce.cechat.ui.addcontact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ce.cechat.R;
public class ChangeInfo  extends AppCompatActivity {
    private EditText identity;
    private EditText department;
    private EditText entryyear;
    private EditText introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        getSupportActionBar().hide();

        identity = findViewById(R.id.identity);
        department = findViewById(R.id.department);
        entryyear = findViewById(R.id.entryyear);
        introduction = findViewById(R.id.introduction);
        final Button new_announcement = findViewById(R.id.new_announcement);

        new_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();

                //replyIntent.putExtra("user_id", user_id.getText().toString());
                replyIntent.putExtra("user_role", identity.getText().toString());
                replyIntent.putExtra("user_sex", "male");
                replyIntent.putExtra("user_introduction", introduction.getText().toString());
                replyIntent.putExtra("user_photo_path", "");
                replyIntent.putExtra("user_department", department.getText().toString());
                replyIntent.putExtra("user_entry_year", entryyear.getText().toString());
                //replyIntent.putExtra("user_nickname", user_id.getText().toString());
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });
    }
}
