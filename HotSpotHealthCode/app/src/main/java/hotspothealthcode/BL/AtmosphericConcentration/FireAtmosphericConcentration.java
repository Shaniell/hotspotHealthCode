package hotspothealthcode.BL.AtmosphericConcentration;

import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.Functions.BouyantFuelFirePlumeRiseFunc;
import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;
import hotspothealthcode.BL.AtmosphericConcentration.results.ResultField;

/**
 * Created by Giladl on 09/01/2016.
 */
public class FireAtmosphericConcentration extends AtmosphericConcentration
{
    //region Data Members

    private double cloudTop;
    private double fuelVolume;
    private int burnDuration;
    private double emissionRate;
    private double airTemp;
    private double releaseRadios;

    //endregion

    //region C'tors

    //endregion

    //region setters

    public void setCloudTop(double cloudTop) {
        this.cloudTop = cloudTop;
    }

    public void setFuelVolume(double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public void setBurnDuration(int burnDuration) {
        this.burnDuration = burnDuration;
    }

    public void setEmissionRate(double emissionRate) {
        this.emissionRate = emissionRate;
    }

    public void setAirTemp(double airTemp) {
        this.airTemp = airTemp;
    }

    public void setReleaseRadios(double releaseRadios) {
        this.releaseRadios = releaseRadios;
    }


    //endregion

    //region Effective Release Height

    private double calcEmissionRate(double fuelVolume,
                                    int burnDuration)
    {
        return 2.58 * Math.pow(10.0, 7.0) * (fuelVolume / burnDuration);
    }
    /**
     * The method calculate the Buoyancy Flux
     * @return The Buoyancy Flux
     */
    private double calcBuoyancyFlux(double emissionRate,
                                    double ta)
    {
        return 0.011 * (emissionRate / ta);
    }

    /**
     * The method calculate the Bouyant Effective Release Height
     * @param r - radius of burning fuel pool (m)
     * @param ta - ambient air temperature (deg K)
     * @param buoyancyFlux - the buoyancy Flux
     * @return The Bouyant Effective Release Height
     */
    private double calcEffectiveReleaseHeight(double r,
                                              double ta,
                                              double buoyancyFlux)
    {
        double effectiveReleaseHeight; // H
        double x;
        double s;

        NewtonRaphsonSolver solver = new NewtonRaphsonSolver();

        if (this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_A ||
            this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_B ||
            this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_C ||
            this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_D) {

            if (buoyancyFlux >= 55) {
                x = 119 * Math.pow(buoyancyFlux, 0.4);
            } else {
                x = 49 * Math.pow(buoyancyFlux, 0.625);
            }

            double p = this.getCityTerrainWindExpoFactor();

            BouyantFuelFirePlumeRiseFunc bouyantFuelFirePlumeRiseFunc = new BouyantFuelFirePlumeRiseFunc(buoyancyFlux,
                                                                                                         this.windSpeedAtReferenceHeight,
                                                                                                         this.referenceHeight,
                                                                                                         p,
                                                                                                         x);

            effectiveReleaseHeight = solver.solve(100, bouyantFuelFirePlumeRiseFunc, 10, 100);
        } else {

            if (this.pasquillStability.getStabilityType() == PasquillStabilityType.TYPE_E)
            {
                s = (0.020 * AtmosphericConcentration.G) / ta;
            }
            else // TYPE_F
            {
                s = (0.035 * AtmosphericConcentration.G) / ta;
            }

            effectiveReleaseHeight = 2.6 * Math.pow(buoyancyFlux / (this.windSpeedAtReferenceHeight * s), 1.0 / 3.0);
        }

        effectiveReleaseHeight = Math.pow(Math.pow(effectiveReleaseHeight, 3.0) + Math.pow(r / 0.6, 3.0), 1.0 / 3.0) -
                                  (r / 0.6);

        return effectiveReleaseHeight;
    }

    //endregion

    //region Deviation Calculation

    @Override
    protected double calcDy()
    {
        double currentDy = 999999;
        double prevDy;
        double sigmaY = this.releaseRadios / 2.0;

        prevDy = this.calcVirtualSourceDistanceForSigmaY(this.terrainType, 0, sigmaY);

        // While the current dy is bigger then 10 % of the prev dy
        while (currentDy - prevDy > (0.1 * prevDy))
        {
            currentDy = this.calcVirtualSourceDistanceForSigmaY(this.terrainType, prevDy, sigmaY);

            prevDy = currentDy;
        }

        return currentDy;
    }

    @Override
    protected double calcDz()
    {
        double currentDz = 999999;
        double prevDz;
        double sigmaZ = this.releaseRadios / 2.0;

        prevDz = this.calcVirtualSourceDistanceForSigmaZ(this.terrainType, 0, sigmaZ);

        // While the current dy is bigger then 10 % of the prev dy
        while (currentDz - prevDz > (0.1 * prevDz))
        {
            currentDz = this.calcVirtualSourceDistanceForSigmaY(this.terrainType, prevDz, sigmaZ);

            prevDz = currentDz;
        }

        return currentDz;
    }

    @Override
    protected double calcCrossWindRadios(double sigmaY) {
        return 2 * sigmaY;
    }

    //endregion

    //region Atmospheric Concentration

    @Override
    public OutputResult calcAtmosphericConcentration()
    {
        double buoyancyFlux;
        double emissionRate;

        // Calc emission rate
        if(this.emissionRate == 0)
            emissionRate = this.calcEmissionRate(this.fuelVolume, this.burnDuration);
        else
            emissionRate = this.emissionRate;

        // Calc buoyancy Flux
        buoyancyFlux = this.calcBuoyancyFlux(emissionRate, AtmosphericConcentration.convertToKelvin(this.airTemp));

        ArrayList<ConcentrationResult> results = new ArrayList<ConcentrationResult>();

        double effectiveReleaseHeight;

        if (this.cloudTop == 0) {
            effectiveReleaseHeight = this.calcEffectiveReleaseHeight(this.releaseRadios,
                                                                     AtmosphericConcentration.convertToKelvin(this.airTemp),
                                                                     buoyancyFlux);
        }
        else
        {
            effectiveReleaseHeight = this.cloudTop * 0.7;
        }

        double windSpeed = this.calcWindSpeed(this.terrainType, effectiveReleaseHeight);

        OutputResult outputResult = this.getOutputResult(effectiveReleaseHeight, windSpeed);

        outputResult.addValue(ResultField.MODEL_TYPE, "General Fire");

        return outputResult;
    }

    //endregion
}
