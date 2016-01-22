package hotspothealthcode.BL;

import android.test.ActivityUnitTestCase;

import com.hotspothealthcode.hotspothealthcode.MainActivity;

import junit.framework.TestCase;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.MeteorologicalConditions;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStability;
import hotspothealthcode.BL.AtmosphericConcentration.PasquillStabilityType;
import hotspothealthcode.BL.AtmosphericConcentration.PlumeAtmosphericConcentration;
import hotspothealthcode.BL.AtmosphericConcentration.TerrainType;

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
        ArrayList<Integer> downWindOffsets,
        int crossWindOffset,
        int verticalOffset,
        double physicalStackHeight,
        double stackExitVelocity,
        double stackRadius,
        double airTemp,
        double stackTemp,
        double heatEmission,
        boolean calcMomentum*/

        ArrayList<Double> downWindOffsets = new ArrayList<>();

        downWindOffsets.add(0.03);
        downWindOffsets.add(0.1);
        downWindOffsets.add(0.2);
        downWindOffsets.add(0.3);
        downWindOffsets.add(0.4);

        PlumeAtmosphericConcentration plumeAtmosphericConcentration = new PlumeAtmosphericConcentration(10,
                                                                                                        1,
                                                                                                        270,
                                                                                                        MeteorologicalConditions.SUN_HIGH_IN_SKY,
                                                                                                        3,
                                                                                                        10,
                                                                                                        TerrainType.STANDARD_TERRAIN,
                                                                                                        20000,
                                                                                                        downWindOffsets,
                                                                                                        0,
                                                                                                        1.5,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        0,
                                                                                                        false,
                                                                                                        10);

        /*PlumeAtmosphericConcentration plumeAtmosphericConcentration = new PlumeAtmosphericConcentration(10,
                                                                                                        1,
                                                                                                        270,
                                                                                                        MeteorologicalConditions.SUN_HIGH_IN_SKY,
                                                                                                        3,
                                                                                                        10,
                                                                                                        TerrainType.STANDARD_TERRAIN,
                                                                                                        20000,
                                                                                                        downWindOffsets,
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

        ArrayList<ConcentrationResult> concentrationResults = plumeAtmosphericConcentration.calcAtmosphericConcentration();

        int x = 9;
    }
}
