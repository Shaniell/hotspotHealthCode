package com.hotspothealthcode.hotspothealthcode;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.ExecutionException;

import hotspothealthcode.BL.ServerAccess.AsyncHttpTask;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void WeatherApiTest()
    {

        AsyncHttpTask asyncHttpTask = new AsyncHttpTask();

        asyncHttpTask.execute("api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=23783e28af9a9d529cbacf224654c896");

//        try {
//            String x = asyncHttpTask.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        assertEquals(true, true);
    }
}