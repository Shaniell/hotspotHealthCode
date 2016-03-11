package com.hotspothealthcode.hotspothealthcode.FileActivities;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DateFormat;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hotspothealthcode.hotspothealthcode.OutputActivity;
import com.hotspothealthcode.hotspothealthcode.R;

import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.Models.ExplorerItem;
import hotspothealthcode.controllers.Controller;

public class FileLoaderActivity extends AppCompatActivity {

    private File currentDir;
    private FileArrayAdapter adapter;
    private ListView filesView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_explorer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        currentDir = new File("/sdcard/");
        fill(currentDir);
    }

    private void fill(File f)
    {
        File[]dirs = f.listFiles();
        this.setTitle("Current Dir: " + f.getName());

        List<ExplorerItem>dir = new ArrayList<ExplorerItem>();
        List<ExplorerItem>fls = new ArrayList<ExplorerItem>();

        try
        {
            for(File file: dirs)
            {
                if(file.isDirectory()){
                    File[] fbuf = file.listFiles();

                    int buf = 0;

                    if(fbuf != null){
                        buf = fbuf.length;
                    }
                    else
                        buf = 0;

                    String num_item = String.valueOf(buf);

                    if(buf == 0)
                        num_item = num_item + " item";
                    else
                        num_item = num_item + " items";

                    //String formated = lastModDate.toString();
                    dir.add(new ExplorerItem(file.getName(), num_item, file.getAbsolutePath(), "directory_icon"));
                }
                else
                {
                    fls.add(new ExplorerItem(file.getName(), file.length() + " Byte", file.getAbsolutePath(), "file_icon"));
                }
            }
        }catch(Exception e)
        {

        }

        Collections.sort(dir);
        Collections.sort(fls);

        dir.addAll(fls);

        if(!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0,new ExplorerItem("..", "Parent Directory", f.getParent(), "directory_up"));

        this.adapter = new FileArrayAdapter(this, R.layout.file_item, dir);

        this.filesView = (ListView)findViewById(R.id.lstFiles);

        this.filesView.setAdapter(adapter);

        // Set on click event
        this.filesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExplorerItem o = adapter.getItem(position);

                if(o.getImage().equalsIgnoreCase("directory_icon") || o.getImage().equalsIgnoreCase("directory_up")){
                    currentDir = new File(o.getPath());
                    fill(currentDir);
                }
                else
                {
                    onFileClick(o);
                }
            }
        });
    }

    private void onFileClick(ExplorerItem o)
    {
        if (o.getExtension().equalsIgnoreCase("json")) {
            Intent intent = new Intent();
            intent.putExtra("path", currentDir.toString());
            intent.putExtra("fileName", o.getName());
            setResult(RESULT_OK, intent);

            File file = new File(this.currentDir, o.getName());

            Controller.loadOutputResult(file);

            Intent outputIntent = new Intent(getApplicationContext(), OutputActivity.class);

            startActivity(outputIntent);

            //finish();
        }
        else
        {
            Toast.makeText(this, "Wrong file type", Toast.LENGTH_SHORT);
        }
    }
}
