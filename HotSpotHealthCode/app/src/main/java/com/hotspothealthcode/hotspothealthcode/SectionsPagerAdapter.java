package com.hotspothealthcode.hotspothealthcode;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

/**
 * Created by shaniel on 13/02/2016.
 */
public interface SectionsPagerAdapter {
    Fragment getItem(int position);

    boolean onOptionsItemSelected(MenuItem item);
}
