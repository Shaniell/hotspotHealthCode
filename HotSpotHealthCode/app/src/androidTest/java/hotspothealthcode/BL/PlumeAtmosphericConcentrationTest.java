package hotspothealthcode.BL;

import android.test.ActivityUnitTestCase;

import com.hotspothealthcode.hotspothealthcode.MainActivity;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;

/**
 * Created by Giladl on 13/01/2016.
 */
public class PlumeAtmosphericConcentrationTest extends ActivityUnitTestCase<MainActivity>
{
    public PlumeAtmosphericConcentrationTest(){
        super(MainActivity.class);
    }

    public void testCalcAtmosphericConcentration()
    {

        /*double referenceHeight,
        double windSpeedAtReferenceHeight,
        double surfaceRoughnessHeight,
        int sampleTime,
        TerrainType terrainType,
        double sourceTerm,
        ArrayList<Integer> concetrationPoints,
        int crossWindOffset,
        int verticalOffset,
        double physicalStackHeight,
        double stackExitVelocity,
        double stackRadius,
        double airTemp,
        double stackTemp,
        double heatEmission,
        boolean calcMomentum*/

        ArrayList<ConcentrationPoint> concetrationPoints = new ArrayList<>();

        concetrationPoints.add(new ConcentrationPoint(0.03, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(0.1, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(0.2, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(0.3, 0, 1.5));
        concetrationPoints.add(new ConcentrationPoint(0.4, 0, 1.5));

        PlumeAtmosphericConcentration plume = new PlumeAtmosphericConcentration();

        plume.setReferenceHeight(10);
        plume.setSourceTerm(20000);
        plume.setEffectiveReleaseHeight(10);
        plume.setWindDirection(270);
        plume.setWindSpeedAtReferenceHeight(1);
        plume.setMeteorologicalCondition(MeteorologicalConditions.SUN_HIGH_IN_SKY);
        plume.setTerrainType(TerrainType.STANDARD_TERRAIN);
        plume.setSurfaceRoughnessHeight(3);
        plume.setSampleTime(10);
        plume.setCalcMomentum(false);
        plume.setConcentrationPoints(concetrationPoints);

        OutputResult outputResult = plume.calcAtmosphericConcentration();

        int x = 9;

        /*PlumeAtmosphericConcentration plumeAtmosphericConcentration = new PlumeAtmosphericConcentration(10,
                                                                                                        1,
                                                                                                        270,
                                                                                                        MeteorologicalConditions.SUN_HIGH_IN_SKY,
                                                                                                        3,
                                                                                                        10,
                                                                                                        TerrainType.STANDARD_TERRAIN,
                                                                                                        20000,
                                                                                                        concetrationPoints,
                                                                                                        0,
                                                                                                        1.5,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        false,
                                                                                                        10);*/

        /*PlumeAtmosphericConcentration plumeAtmosphericConcentration = new PlumeAtmosphericConcentration(10,
                                                                                                        1,
                                                                                                        270,
                                                                                                        MeteorologicalConditions.SUN_HIGH_IN_SKY,
                                                                                                        3,
                                                                                                        10,
                                                                                                        TerrainType.STANDARD_TERRAIN,
                                                                                                        20000,
                                                                                                        concetrationPoints,
                                                                                                        0,
                                                                                                        15,
                                                                                                        30,
                                                                                                        1.2,
                                                                                                        0.8,
                                                                                                        25,
                                                                                                        60,
                                                                                                        0,
                                                                                                        true,
                                                                                                        0,
                new PasquillStability(PasquillStabilityType.TYPE_E));*/

        //ArrayList<ConcentrationResult> concentrationResults = plumeAtmosphericConcentration.calcAtmosphericConcentration();
    }
}
