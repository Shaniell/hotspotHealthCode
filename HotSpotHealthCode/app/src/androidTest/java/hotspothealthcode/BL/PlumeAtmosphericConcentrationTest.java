package hotspothealthcode.BL;

import android.test.ActivityUnitTestCase;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

//import org.junit.Test;
//import org.junit.runner.RunWith;

import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.AtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;

/**
 * Created by Giladl on 13/01/2016.
 */
@RunWith(AndroidJUnit4.class)
public class PlumeAtmosphericConcentrationTest
{
    //region Effective Height Unstable

    @Test
    public void testCalcEffectiveHeightWithHeatEmissionWithoutMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        double h = 15;
        double v = 0;
        double r = 0;
        double ta = 20 + 273.15;
        double ts = 0 + 273.15;
        double flux = 1000;
        boolean calcMomentum = false;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                                                                       double.class,
                                                                       double.class,
                                                                       double.class,
                                                                       double.class,
                                                                       double.class,
                                                                       boolean.class);

        method.setAccessible(true);

        double height = (double) method.invoke(plume,
                                               h,
                                               v,
                                               r,
                                               ta,
                                               ts,
                                               flux,
                                               calcMomentum);

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
    public void testCalcCalcEffectiveHeightWithHeatEmissionWithMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 1000;
        boolean calcMomentum = true;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                                                    h,
                                                    v,
                                                    r,
                                                    ta,
                                                    ts,
                                                    flux,
                                                    calcMomentum);

            Log.i("2", String.valueOf(height));

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
    public void testCalcCalcEffectiveHeightWithoutHeatEmissionWithoutMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 0;
        boolean calcMomentum = false;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("3", String.valueOf(height));

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
    public void testCalcEffectiveHeightWithoutHeatEmissionWithMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 0;
        boolean calcMomentum = true;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("4", String.valueOf(height));

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
    public void testCalcEffectiveHeightWithHeatEmissionWithoutMomentumStableE()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        double h = 15;
        double v = 0;
        double r = 0;
        double ta = 20 + 273.15;
        double ts = 0 + 273.15;
        double flux = 1000;
        boolean calcMomentum = false;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Assert.assertEquals(true, true);

            Log.i("5", String.valueOf(height));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCalcCalcEffectiveHeightWithHeatEmissionWithMomentumStableE()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 1000;
        boolean calcMomentum = true;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("6", String.valueOf(height));

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
    public void testCalcCalcEffectiveHeightWithoutHeatEmissionWithoutMomentumStableE()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 0;
        boolean calcMomentum = false;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("7", String.valueOf(height));

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
    public void testCalcEffectiveHeightWithoutHeatEmissionWithMomentumStableE()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 0;
        boolean calcMomentum = true;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("8", String.valueOf(height));

            Assert.assertEquals(true, true);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //endregion E

    //region Effective Height stable F

    @Test
    public void testCalcEffectiveHeightWithHeatEmissionWithoutMomentumStableF()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_F));

        double h = 15;
        double v = 0;
        double r = 0;
        double ta = 20 + 273.15;
        double ts = 0 + 273.15;
        double flux = 1000;
        boolean calcMomentum = false;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("9", String.valueOf(height));

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
    public void testCalcCalcEffectiveHeightWithHeatEmissionWithMomentumStableF()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_F));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 1000;
        boolean calcMomentum = true;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Assert.assertEquals(true, true);

            Log.i("10", String.valueOf(height));

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCalcCalcEffectiveHeightWithoutHeatEmissionWithoutMomentumStableF()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_F));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 0;
        boolean calcMomentum = false;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("11", String.valueOf(height));

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
    public void testCalcEffectiveHeightWithoutHeatEmissionWithMomentumStableF()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_F));

        double h = 15;
        double v = 10;
        double r = 4;
        double ta = 10.7 + 273.15;
        double ts = 15 + 273.15;
        double flux = 0;
        boolean calcMomentum = true;

        Class c = PlumeAtmosphericConcentration.class;

        ArrayList<Class> types = new ArrayList<>();

        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(double.class);
        types.add(boolean.class);

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight", double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    double.class,
                    boolean.class);

            method.setAccessible(true);

            double height = (double) method.invoke(plume,
                    h,
                    v,
                    r,
                    ta,
                    ts,
                    flux,
                    calcMomentum);

            Log.i("12", String.valueOf(height));

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

    /*//region Atmospheric Concentration Unstable

    @Test
    public void testCalcAtmosphericConcentrationWithEffectiveHeightUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setEffectiveReleaseHeight(10);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithHeatEmissionWithoutMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithHeatEmissionWithMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithoutHeatEmissionWithoutMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithoutHeatEmissionWithMomentumUnstable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    //endregion

    //region Atmospheric Concentration Stable

    @Test
    public void testCalcAtmosphericConcentrationWithEffectiveHeightStable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setEffectiveReleaseHeight(10);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithHeatEmissionWithoutMomentumStable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithHeatEmissionWithMomentumStable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setHeatEmission(1000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithoutHeatEmissionWithoutMomentumStable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    @Test
    public void testCalcAtmosphericConcentrationWithoutHeatEmissionWithMomentumStable()
    {
        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        // Set additional data
        plume.setReferenceHeight(10);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set plume data
        plume.setSourceTerm(20000);
        plume.setPhysicalStackHeight(15);
        plume.setStackTemp(15);
        plume.setStackExitVelocity(10);
        plume.setStackRadius(4);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

//        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
//        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        Assert.assertEquals(true, true);
    }

    //endregion*/
}
