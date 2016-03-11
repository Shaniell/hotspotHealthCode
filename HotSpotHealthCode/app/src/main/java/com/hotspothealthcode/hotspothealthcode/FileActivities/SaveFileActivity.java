package com.hotspothealthcode.hotspothealthcode.FileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hotspothealthcode.hotspothealthcode.R;

import java.io.File;

import hotspothealthcode.controllers.Controller;

/**
 * Created by Giladl on 11/03/2016.
 */
public class SaveFileActivity extends Activity {

    private static final int REQUEST_PATH = 1;

    private EditText fileName;
    private TextView filePath;
    private Button browse;
    private Button cancel;
    private Button save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.save_result_activity);

        this.fileName = (EditText)findViewById(R.id.etFileName);
        this.filePath = (TextView)findViewById(R.id.tvFilePath);
        this.browse = (Button)findViewById(R.id.btnBrowse);
        this.cancel = (Button)findViewById(R.id.btnCancel);
        this.save = (Button)findViewById(R.id.btnSaveFile);

        // Set browse click event listener
        this.browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent directoryChooserIntent = new Intent(getApplicationContext(), DirectoryChooserActivity.class);

                startActivityForResult(directoryChooserIntent, SaveFileActivity.REQUEST_PATH);
            }
        });

        // Set save click event listener
        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!filePath.getText().toString().matches("")) && (!fileName.getText().toString().matches(""))) {
                    File file = new File(filePath.getText().toString(), fileName.getText().toString());

                    Controller.saveOutputResult(file);

                    Toast.makeText(SaveFileActivity.this, "File saved successfully", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else
                {
                    Toast.makeText(SaveFileActivity.this, "You need to enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set cancel click event listener
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {

                String curFileName = data.getStringExtra("fileName");

                if (curFileName != null)
                    this.fileName.setText(curFileName);

                this.filePath.setText(data.getStringExtra("path"));
            }
        }
    }
}
