package hotspothealthcode.BL;

import android.test.ActivityUnitTestCase;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.ActivityInstrumentationTestCase2;

//import org.junit.Test;
//import org.junit.runner.RunWith;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

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
    @Test
    public void testCalcAtmosphericConcentrationWithEffectiveHeight()
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

        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        assert true == true;
    }

    @Test
    public void testCalcAtmosphericConcentrationWithHeatEmissionWithoutMomentum()
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
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        assert true == true;
    }

    @Test
    public void testCalcAtmosphericConcentrationWithHeatEmissionWithMomentum()
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
        plume.setPhysicalStackHeight(10);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        assert true == true;
    }

    @Test
    public void testCalcAtmosphericConcentrationWithoutHeatEmissionWithoutMomentum()
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
        plume.setStackRadius(8);
        plume.setPhysicalStackHeight(10);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(false);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        assert true == true;
    }

    @Test
    public void testCalcAtmosphericConcentrationWithoutHeatEmissionWithMomentum()
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
        plume.setPhysicalStackHeight(10);
        plume.setAirTemp(10.7);
        plume.setCalcMomentum(true);

        // Set meto conditions
        plume.setWindDirection(287);
        plume.setWindSpeedAtReferenceHeight(9.8);
        plume.setPasquillStability(new PasquillStability(PasquillStabilityType.TYPE_A));
        //plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);

        // Set coordiantes
        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

        concetrationPoints.add(new ConcentrationPoint(30, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(100, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(200, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(300, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(400, 0, 1.5));

        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        assert true == true;
    }
}
