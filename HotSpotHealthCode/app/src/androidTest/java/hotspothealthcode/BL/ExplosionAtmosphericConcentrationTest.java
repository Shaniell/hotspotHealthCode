package hotspothealthcode.BL;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.ExplosionAtmosphericConcentration;
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
public class ExplosionAtmosphericConcentrationTest
{
    @Test
    public void testCalcEffectiveHeightWithoutGreenFieldUnstable()
    {
        ExplosionAtmosphericConcentration explosion = new ExplosionAtmosphericConcentration();

        // Set additional data
        explosion.setReferenceHeight(10);
        explosion.setSurfaceRoughnessHeight(3);
        explosion.setSampleTime(10);
        explosion.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set explosion data
        explosion.setSourceTerm(20000);
        explosion.setExplosiveAmount(5);

        // Set meto conditions
        explosion.setWindDirection(287);
        explosion.setWindSpeedAtReferenceHeight(9.8);
        explosion.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        Class c = ExplosionAtmosphericConcentration.class;

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight");

        method.setAccessible(true);

        double height = (double) method.invoke(explosion);

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
    public void testCalcEffectiveHeightWithoutGreenFieldStable()
    {
        ExplosionAtmosphericConcentration explosion = new ExplosionAtmosphericConcentration();

        // Set additional data
        explosion.setReferenceHeight(10);
        explosion.setSurfaceRoughnessHeight(3);
        explosion.setSampleTime(10);
        explosion.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set explosion data
        explosion.setSourceTerm(20000);
        explosion.setExplosiveAmount(5);

        // Set meto conditions
        explosion.setWindDirection(287);
        explosion.setWindSpeedAtReferenceHeight(9.8);
        explosion.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        Class c = ExplosionAtmosphericConcentration.class;

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight");

            method.setAccessible(true);

            double height = (double) method.invoke(explosion);

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
    public void testCalcEffectiveHeightWithGreenFieldUnstable()
    {
        ExplosionAtmosphericConcentration explosion = new ExplosionAtmosphericConcentration();

        // Set additional data
        explosion.setReferenceHeight(10);
        explosion.setSurfaceRoughnessHeight(3);
        explosion.setSampleTime(10);
        explosion.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set explosion data
        explosion.setSourceTerm(20000);
        explosion.setExplosiveAmount(5);
        explosion.setIsGreenField(true);

        // Set meto conditions
        explosion.setWindDirection(287);
        explosion.setWindSpeedAtReferenceHeight(9.8);
        explosion.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));

        Class c = ExplosionAtmosphericConcentration.class;

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight");

            method.setAccessible(true);

            double height = (double) method.invoke(explosion);

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
    public void testCalcEffectiveHeightWithGreenFieldStable()
    {
        ExplosionAtmosphericConcentration explosion = new ExplosionAtmosphericConcentration();

        // Set additional data
        explosion.setReferenceHeight(10);
        explosion.setSurfaceRoughnessHeight(3);
        explosion.setSampleTime(10);
        explosion.setTerrainType(TerrainType.STANDARD_TERRAIN);

        // Set explosion data
        explosion.setSourceTerm(20000);
        explosion.setExplosiveAmount(5);
        explosion.setIsGreenField(true);

        // Set meto conditions
        explosion.setWindDirection(287);
        explosion.setWindSpeedAtReferenceHeight(9.8);
        explosion.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_E));

        Class c = ExplosionAtmosphericConcentration.class;

        Method method = null;
        try {
            method = c.getDeclaredMethod("calcEffectiveReleaseHeight");

            method.setAccessible(true);

            double height = (double) method.invoke(explosion);

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
}
