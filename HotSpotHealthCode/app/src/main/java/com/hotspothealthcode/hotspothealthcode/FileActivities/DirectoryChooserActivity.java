package com.hotspothealthcode.hotspothealthcode.FileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hotspothealthcode.hotspothealthcode.OutputActivity;
import com.hotspothealthcode.hotspothealthcode.R;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hotspothealthcode.BL.Models.ExplorerItem;
import hotspothealthcode.controllers.Controller;

public class DirectoryChooserActivity extends AppCompatActivity {

    private File currentDir;
    private FileArrayAdapter adapter;
    private ListView filesView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.directory_chooser_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        currentDir = new File("/sdcard/");

        fill(currentDir);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.directory_chooser_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_select_directory:

                Intent intent = new Intent();
                intent.putExtra("path", currentDir.toString());

                setResult(RESULT_OK, intent);

                finish();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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

            finish();
        }
        else
        {
            Toast.makeText(this, "Wrong file type", Toast.LENGTH_SHORT).show();
        }
    }
}
