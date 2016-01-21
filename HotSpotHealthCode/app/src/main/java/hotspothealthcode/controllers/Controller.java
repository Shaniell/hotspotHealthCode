package hotspothealthcode.controllers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.concurrent.ExecutionException;

import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.Models.Weather;
import hotspothealthcode.BL.Weather.WeatherManager;

/**
 * Created by Giladl on 19/01/2016.
 */
public class Controller
{
    public static ArrayList<PasquillStabilityType> getStabilityTypes()
    {
        return PasquillStabilityType.getStabilityTypes();
    }

    public static Weather getWeatherByPosition(LatLng position) throws ExecutionException, InterruptedException
    {
        return new WeatherManager().getWeatherByPosition(position);
    }

    public static PasquillStabilityType calcStability(double windSpeed, MeteorologicalConditions condition)
    {
        return (new PasquillStability(windSpeed, condition)).stabilityType;
    }

    public static ArrayList<MeteorologicalConditions> getMeteorologicalConditions()
    {
        return MeteorologicalConditions.getMeteorologicalConditions();
    }
}
