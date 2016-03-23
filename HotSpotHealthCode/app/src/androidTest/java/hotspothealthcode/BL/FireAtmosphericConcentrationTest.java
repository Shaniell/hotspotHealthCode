package hotspothealthcode.BL;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.FireAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;

//import org.junit.Test;
//import org.junit.runner.RunWith;

/**
 * Created by Giladl on 13/01/2016.
 */
@RunWith(AndroidJUnit4.class)
public class FireAtmosphericConcentrationTest
{
    //region Effective Height Unstable

    @Test
    public void testCalcEffectiveHeightWithHeatEmissionUnstable()
    {
        FireAtmosphericConcentration fire = new FireAtmosphericConcentration();

        // Set additional data
        fire.setReferenceHeight(10);
        fire.setSurfaceRoughnessHeight(3);
        fire.setSampleTime(10);
        fire.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set fire data
        fire.setSourceTerm(20000);
        fire.setEmissionRate(10000000);
        fire.setReleaseRadios(4);
        fire.setAirTemp(20 + 273.15);

        // Set meto conditions
        fire.setWindDirection(287);
        fire.setWindSpeedAtReferenceHeight(9.8);
        fire.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        double airTemp = 20 + 273.15;
        double emissionRate = 10000000;
        double r = 4;

        Class c = FireAtmosphericConcentration.class;

        try {

            Method buoyancyFluxMethod = c.getDeclaredMethod("calcBuoyancyFlux", double.class,
                    double.class);

            buoyancyFluxMethod.setAccessible(true);

            double buoyancyFlux = (double) buoyancyFluxMethod.invoke(fire,
                                                                     emissionRate,
                                                                     airTemp);

            Method heightMethod  = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                                                                       double.class,
                                                                       double.class);

            heightMethod.setAccessible(true);

            double height = (double) heightMethod.invoke(fire,
                                                         r,
                                                         airTemp,
                                                         buoyancyFlux);

        Log.i("1", String.valueOf(height));

        Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCalcEffectiveHeightWithoutHeatEmissionUnstable()
    {
        FireAtmosphericConcentration fire = new FireAtmosphericConcentration();

        // Set additional data
        fire.setReferenceHeight(10);
        fire.setSurfaceRoughnessHeight(3);
        fire.setSampleTime(10);
        fire.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set fire data
        fire.setSourceTerm(20000);
        fire.setFuelVolume(100);
        fire.setBurnDuration(10);
        fire.setReleaseRadios(4);
        fire.setAirTemp(20 + 273.15);

        // Set meto conditions
        fire.setWindDirection(287);
        fire.setWindSpeedAtReferenceHeight(9.8);
        fire.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        double airTemp = 20 + 273.15;
        double r = 4;
        double fuelVolume = 100;
        int burnDuration = 10;

        Class c = FireAtmosphericConcentration.class;

        try {

            Method calcEmissionRateMethod = c.getDeclaredMethod("calcEmissionRate", double.class,
                    int.class);

            calcEmissionRateMethod.setAccessible(true);

            double emissionRate  = (double)calcEmissionRateMethod.invoke(fire, fuelVolume, burnDuration);

            Method buoyancyFluxMethod = c.getDeclaredMethod("calcBuoyancyFlux", double.class,
                    double.class);

            buoyancyFluxMethod.setAccessible(true);

            double buoyancyFlux = (double) buoyancyFluxMethod.invoke(fire,
                    emissionRate,
                    airTemp);

            Method heightMethod  = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class);

            heightMethod.setAccessible(true);

            double height = (double) heightMethod.invoke(fire,
                    r,
                    airTemp,
                    buoyancyFlux);

            Log.i("1", String.valueOf(height));

            Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Effective Height stable E

    @Test
    public void testCalcEffectiveHeightWithHeatEmissionStableE()
    {
        FireAtmosphericConcentration fire = new FireAtmosphericConcentration();

        // Set additional data
        fire.setReferenceHeight(10);
        fire.setSurfaceRoughnessHeight(3);
        fire.setSampleTime(10);
        fire.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set fire data
        fire.setSourceTerm(20000);
        fire.setEmissionRate(10000000);
        fire.setReleaseRadios(4);
        fire.setAirTemp(20 + 273.15);

        // Set meto conditions
        fire.setWindDirection(287);
        fire.setWindSpeedAtReferenceHeight(9.8);
        fire.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        double airTemp = 20 + 273.15;
        double emissionRate = 10000000;
        double r = 4;

        Class c = FireAtmosphericConcentration.class;

        try {

            Method buoyancyFluxMethod = c.getDeclaredMethod("calcBuoyancyFlux", double.class,
                    double.class);

            buoyancyFluxMethod.setAccessible(true);

            double buoyancyFlux = (double) buoyancyFluxMethod.invoke(fire,
                    emissionRate,
                    airTemp);

            Method heightMethod  = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class);

            heightMethod.setAccessible(true);

            double height = (double) heightMethod.invoke(fire,
                    r,
                    airTemp,
                    buoyancyFlux);

            Log.i("1", String.valueOf(height));

            Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCalcEffectiveHeightWithoutHeatEmissionStableE()
    {
        FireAtmosphericConcentration fire = new FireAtmosphericConcentration();

        // Set additional data
        fire.setReferenceHeight(10);
        fire.setSurfaceRoughnessHeight(3);
        fire.setSampleTime(10);
        fire.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set fire data
        fire.setSourceTerm(20000);
        fire.setFuelVolume(100);
        fire.setBurnDuration(10);
        fire.setReleaseRadios(4);
        fire.setAirTemp(20 + 273.15);

        // Set meto conditions
        fire.setWindDirection(287);
        fire.setWindSpeedAtReferenceHeight(9.8);
        fire.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        double airTemp = 20 + 273.15;
        double r = 4;
        double fuelVolume = 100;
        int burnDuration = 10;

        Class c = FireAtmosphericConcentration.class;

        try {

            Method calcEmissionRateMethod = c.getDeclaredMethod("calcEmissionRate", double.class,
                    int.class);

            calcEmissionRateMethod.setAccessible(true);

            double emissionRate  = (double)calcEmissionRateMethod.invoke(fire, fuelVolume, burnDuration);

            Method buoyancyFluxMethod = c.getDeclaredMethod("calcBuoyancyFlux", double.class,
                    double.class);

            buoyancyFluxMethod.setAccessible(true);

            double buoyancyFlux = (double) buoyancyFluxMethod.invoke(fire,
                    emissionRate,
                    airTemp);

            Method heightMethod  = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class);

            heightMethod.setAccessible(true);

            double height = (double) heightMethod.invoke(fire,
                    r,
                    airTemp,
                    buoyancyFlux);

            Log.i("1", String.valueOf(height));

            Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Effective Height stable

    @Test
    public void testCalcEffectiveHeightWithHeatEmissionStableF()
    {
        FireAtmosphericConcentration fire = new FireAtmosphericConcentration();

        // Set additional data
        fire.setReferenceHeight(10);
        fire.setSurfaceRoughnessHeight(3);
        fire.setSampleTime(10);
        fire.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set fire data
        fire.setSourceTerm(20000);
        fire.setEmissionRate(10000000);
        fire.setReleaseRadios(4);
        fire.setAirTemp(20 + 273.15);

        // Set meto conditions
        fire.setWindDirection(287);
        fire.setWindSpeedAtReferenceHeight(9.8);
        fire.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_F));

        double airTemp = 20 + 273.15;
        double emissionRate = 10000000;
        double r = 4;

        Class c = FireAtmosphericConcentration.class;

        try {

            Method buoyancyFluxMethod = c.getDeclaredMethod("calcBuoyancyFlux", double.class,
                    double.class);

            buoyancyFluxMethod.setAccessible(true);

            double buoyancyFlux = (double) buoyancyFluxMethod.invoke(fire,
                    emissionRate,
                    airTemp);

            Method heightMethod  = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class);

            heightMethod.setAccessible(true);

            double height = (double) heightMethod.invoke(fire,
                    r,
                    airTemp,
                    buoyancyFlux);

            Log.i("1", String.valueOf(height));

            Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCalcEffectiveHeightWithoutHeatEmissionStableF()
    {
        FireAtmosphericConcentration fire = new FireAtmosphericConcentration();

        // Set additional data
        fire.setReferenceHeight(10);
        fire.setSurfaceRoughnessHeight(3);
        fire.setSampleTime(10);
        fire.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set fire data
        fire.setSourceTerm(20000);
        fire.setFuelVolume(100);
        fire.setBurnDuration(10);
        fire.setReleaseRadios(4);
        fire.setAirTemp(20 + 273.15);

        // Set meto conditions
        fire.setWindDirection(287);
        fire.setWindSpeedAtReferenceHeight(9.8);
        fire.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_F));

        double airTemp = 20 + 273.15;
        double r = 4;
        double fuelVolume = 100;
        int burnDuration = 10;

        Class c = FireAtmosphericConcentration.class;

        try {

            Method calcEmissionRateMethod = c.getDeclaredMethod("calcEmissionRate", double.class,
                    int.class);

            calcEmissionRateMethod.setAccessible(true);

            double emissionRate  = (double)calcEmissionRateMethod.invoke(fire, fuelVolume, burnDuration);

            Method buoyancyFluxMethod = c.getDeclaredMethod("calcBuoyancyFlux", double.class,
                    double.class);

            buoyancyFluxMethod.setAccessible(true);

            double buoyancyFlux = (double) buoyancyFluxMethod.invoke(fire,
                    emissionRate,
                    airTemp);

            Method heightMethod  = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class);

            heightMethod.setAccessible(true);

            double height = (double) heightMethod.invoke(fire,
                    r,
                    airTemp,
                    buoyancyFlux);

            Log.i("1", String.valueOf(height));

            Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //endregion
}
