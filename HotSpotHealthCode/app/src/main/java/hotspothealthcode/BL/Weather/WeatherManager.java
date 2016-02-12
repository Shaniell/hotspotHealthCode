package hotspothealthcode.BL.Weather;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.BL.ServerAccess.AsyncHttpTask;

/**
 * Created by Giladl on 08/12/2015.
 */
public enum WeatherManager {

    INSTANCE;

    private static final String API_KEY = "23783e28af9a9d529cbacf224654c896";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String API_LAT_PARAM = "lat=";
    private static final String API_LON_PARAM = "lon=";
    private static final String API_KEY_PARAM = "APPID=";
    private static final String API_METRIC_UNITS_PARAM = "units=metric";

    private WeatherManager()
    {

    }

    public Weather getWeatherByPosition(LatLng position) throws ExecutionException, InterruptedException
    {
        AsyncHttpTask httpTask = new AsyncHttpTask();
        String Url = API_URL + API_LAT_PARAM + position.latitude + "&" +
                     API_LON_PARAM + position.longitude + "&" +
                     API_METRIC_UNITS_PARAM + "&" +
                     API_KEY_PARAM + API_KEY;

        httpTask.execute(Url);

        while(httpTask.getStatus() == AsyncTask.Status.RUNNING);

        JSONObject jsonObject = httpTask.get();

        return parseJsonWeather(jsonObject);
    }

    private Weather parseJsonWeather(JSONObject jsonObject)
    {
        try
        {
            JSONObject weatherJSON = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject mainJSON = jsonObject.getJSONObject("main");
            JSONObject windJSON = jsonObject.getJSONObject("wind");

            Weather weather = new Weather(mainJSON.getDouble("temp"),
                                          windJSON.getDouble("deg"),
                                          windJSON.getDouble("speed"),
                                          weatherJSON.getString("main"),
                                          weatherJSON.getString("description"));

            return weather;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
