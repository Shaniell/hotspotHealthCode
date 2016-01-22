package hotspothealthcode.BL;

import junit.framework.TestCase;

import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;

/**
 * Created by Giladl on 14/12/2015.
 */
public class GaussianModelDELETELATERTest extends TestCase {

    public void testCalcDFx() throws Exception {

    }

    public void testCalculate() throws Exception {

    }

    public void testCalcGasConcentration() throws Exception {
        /*GaussianModel g = new GaussianModel(TerrainType.STANDARD_TERRAIN,
                                            MeteorologicalConditions.SUN_HIGH_IN_SKY,
                                            1.2,
                                            0,*/
        DerivativeStructure d = new DerivativeStructure(1,1);

        d = d.add(17.576);

        d = d.add(3);

        int x = 9;
    }

    public void testCalcGasConcentrationWithInversionLayer() throws Exception {

    }
}