package hotspothealthcode.controllers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.concurrent.ExecutionException;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.FireAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.BL.Weather.WeatherManager;

/**
 * Created by Giladl on 19/01/2016.
 */
public class Controller
{
    private static LatLng currentLocation = new LatLng(40.8516701, -93.2599318); // TODO: DELETE DEFAULT VALUE
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

    public static OutputResult getOutputResultInstance()
    {
        return OutputResult.getInstance();
    }

    public static OutputResult loadOutputResult()
    {
        // TODO: IMPLENENT
        return OutputResult.getInstance();
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
}
