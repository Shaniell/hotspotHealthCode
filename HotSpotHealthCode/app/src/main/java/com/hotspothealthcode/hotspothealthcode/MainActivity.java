package com.hotspothealthcode.hotspothealthcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import com.hotspothealthcode.hotspothealthcode.*;

public class MainActivity extends AppCompatActivity{

    ImageButton imageButtonPlume;
    ImageButton imageButtonFire;
    ImageButton imageButtonExplosion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {

        imageButtonPlume = (ImageButton) findViewById(R.id.ibGeneralPlume);
        imageButtonFire = (ImageButton) findViewById(R.id.ibGeneralFire);
        imageButtonExplosion = (ImageButton) findViewById(R.id.ibGeneralExplosion);

        imageButtonPlume.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent plumeStepperActivityIntent = new Intent(getApplicationContext(), PlumeStepperActivity.class);
                Intent mapsActivity = new Intent(getApplicationContext(), MapsActivity.class);

                mapsActivity.putExtra("NextIntent", plumeStepperActivityIntent);

                startActivity(mapsActivity);
            }
        });

        imageButtonFire.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent fireStepperActivityIntent = new Intent(getApplicationContext(), FireStepperActivity.class);
                Intent mapsActivity = new Intent(getApplicationContext(), MapsActivity.class);

                mapsActivity.putExtra("NextIntent", fireStepperActivityIntent);

                startActivity(mapsActivity);

            }
        });

        imageButtonExplosion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent explosionStepperActivityIntent = new Intent(getApplicationContext(), ExplosionStepperActivity.class);
                Intent mapsActivity = new Intent(getApplicationContext(), MapsActivity.class);

                mapsActivity.putExtra("NextIntent", explosionStepperActivityIntent);

                startActivity(mapsActivity);

            }
        });

    }

}
