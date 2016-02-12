package hotspothealthcode.BL.Weather;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.BL.ServerAccess.AsyncHttpTask;

/**
 * Created by Giladl on 12/12/2015.
 */
public class WeatherManagerTest extends TestCase {

    public void testGetWeatherByPosition() throws Exception {

        try {
            Weather wt = WeatherManager.INSTANCE.getWeatherByPosition(new LatLng(32.067111, 34.825975));

            assertNotNull(wt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testParseJsonWeather() throws Exception {

        try {
            JSONObject j = new JSONObject("{\"coord\":{\"lon\":138.93,\"lat\":34.97},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":287.022,\"pressure\":1027.04,\"humidity\":100,\"temp_min\":287.022,\"temp_max\":287.022,\"sea_level\":1035.12,\"grnd_level\":1027.04},\"wind\":{\"speed\":10.01,\"deg\":37.0011},\"clouds\":{\"all\":88},\"dt\":1449933386,\"sys\":{\"message\":0.0337,\"country\":\"JP\",\"sunrise\":1449870155,\"sunset\":1449905595},\"id\":1851632,\"name\":\"Shuzenji\",\"cod\":200}0");
            //Weather wt = w.parseJsonWeather(j);

            //assertNotNull(wt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}