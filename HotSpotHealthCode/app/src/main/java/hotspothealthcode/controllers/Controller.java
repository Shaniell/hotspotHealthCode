package hotspothealthcode.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.FireAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.BL.StorageAccess.StorageAccessor;
import hotspothealthcode.BL.Weather.WeatherManager;

/**
 * Created by Giladl on 19/01/2016.
 */
public class Controller
{
    private static LatLng currentLocation;
    private static Weather currentWeather;
    private static AtmosphericConcentration calcConcentration;

    public static void init(LatLng location)
    {
        Controller.currentLocation = location;

        Controller.currentWeather = Controller.getWeatherByLocation();
    }

    public static ArrayList<PasquillStabilityType> getStabilityTypes()
    {
        return PasquillStabilityType.getStabilityTypes();
    }

    public static LatLng getCurrentLocation()
    {
        return Controller.currentLocation;
    }

    public static Weather getCurrentWeather()
    {
        return Controller.currentWeather;
    }

    public static Weather getWeatherByLocation()
    {
        try {
            return WeatherManager.INSTANCE.getWeatherByPosition(Controller.currentLocation);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PasquillStabilityType calcStability(double windSpeed, MeteorologicalConditions condition)
    {
        return (new PasquillStability(windSpeed, condition)).getStabilityType();
    }

    public static ArrayList<MeteorologicalConditions> getMeteorologicalConditions()
    {
        return MeteorologicalConditions.getMeteorologicalConditions();
    }

    public static ArrayList<TerrainType> getTerrainTypes()
    {
        return TerrainType.getTerrainTypes();
    }

    public static OutputResult getOutputResultInstance()
    {
        return OutputResult.getInstance();
    }

    public static OutputResult loadOutputResult(File file) throws JSONException
    {
        return StorageAccessor.INSTANCE.loadResult(file);
    }

    public static void saveOutputResult(File file)
    {
        StorageAccessor.INSTANCE.saveResult(file, OutputResult.getInstance());
    }

    public static ArrayList<Double> getCoordinatesDefaultValues()
    {
        //TODO: CREATE CLASS THAT CONTAIN VALUES
        ArrayList<Double> lst = new ArrayList<>();

        lst.add(0.03);
        lst.add(0.2);
        lst.add(0.4);
        lst.add(0.6);
        lst.add(0.8);
        lst.add(1.0);
        lst.add(8.0);
        lst.add(20.0);
        lst.add(40.0);
        lst.add(80.0);

        return lst;
    }

    public static void setCalcConcentration(AtmosphericConcentration calcConcentration)
    {
        Controller.calcConcentration = calcConcentration;

        Controller.calcConcentration.calcAtmosphericConcentration();
    }

    public static boolean saveValueToSharedPreferences(Context context, String key, String value)
    {
        return StorageAccessor.INSTANCE.saveValueToSharedPreferences(context, key, value);
    }

    public static boolean saveValuesToSharedPreferences(Context context, String key, ArrayList<String> values)
    {
        return StorageAccessor.INSTANCE.saveValuesToSharedPreferences(context, key, values);
    }

    public static String getValueFromSharedPreferences(Context context, String key)
    {
        return StorageAccessor.INSTANCE.getValueFromSharedPreferences(context, key);
    }

    public static ArrayList<String> getValuesFromSharedPreferences(Context context, String key)
    {
        return StorageAccessor.INSTANCE.getValuesFromSharedPreferences(context, key);
    }
}
