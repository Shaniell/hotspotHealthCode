package hotspothealthcode.BL.AtmosphericConcentration;

import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.Functions.BouyantFuelFirePlumeRiseFunc;

/**
 * Created by Giladl on 09/01/2016.
 */
public class FireAtmosphericConcentration extends AtmosphericConcentration
{
    //region Data Members

    private double fuelVolume;
    private int burnDuration;
    private double emissionRate;
    private double ta;
    private double r;

    //endregion

    //region C'tors

    public FireAtmosphericConcentration(double referenceHeight,
                                        double windSpeedAtReferenceHeight,
                                        int windDirection,
                                        MeteorologicalConditions meteorologicalConditions,
                                        double surfaceRoughnessHeight,
                                        int sampleTime,
                                        TerrainType terrainType,
                                        double sourceTerm,
                                        ArrayList<ConcentrationPoint> concentrationPoints,
                                        double fuelVolume,
                                        int burnDuration,
                                        double emissionRate,
                                        double ta,
                                        double r)
    {
        super(referenceHeight,
                windSpeedAtReferenceHeight,
                windDirection,
                meteorologicalConditions,
                surfaceRoughnessHeight,
                sampleTime,
                terrainType,
                sourceTerm,
                concentrationPoints);

        this.fuelVolume = fuelVolume;
        this.burnDuration = burnDuration;
        this.emissionRate = emissionRate;
        this.ta = ta;
        this.r = r;
    }

    public FireAtmosphericConcentration(double referenceHeight,
                                        double windSpeedAtReferenceHeight,
                                        int windDirection,
                                        MeteorologicalConditions meteorologicalConditions,
                                        double surfaceRoughnessHeight,
                                        int sampleTime,
                                        TerrainType terrainType,
                                        double sourceTerm,
                                        ArrayList<ConcentrationPoint> concentrationPoints,
                                        PasquillStability pasquillStability,
                                        double fuelVolume,
                                        int burnDuration,
                                        double emissionRate,
                                        double ta,
                                        double r)
    {
        super(referenceHeight,
                windSpeedAtReferenceHeight,
                windDirection,
                meteorologicalConditions,
                surfaceRoughnessHeight,
                sampleTime,
                terrainType,
                sourceTerm,
                concentrationPoints,
                pasquillStability);

        this.fuelVolume = fuelVolume;
        this.burnDuration = burnDuration;
        this.emissionRate = emissionRate;
        this.ta = ta;
        this.r = r;
    }

    //endregion

    //region Effective Release Height

    private double calcEmissionRate(double fuelVolume,
                                    int burnDuration)
    {
        return 2.58 * Math.pow(10, 7) * (fuelVolume / burnDuration);
    }
    /**
     * The method calculate the Buoyancy Flux
     * @return The Buoyancy Flux
     */
    private double calcBuoyancyFlux(double emissionRate,
                                    double ta)
    {
        return 0.011 * emissionRate * ta;
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

        if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_A ||
            this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_B ||
            this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_C ||
            this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_D) {

            if (buoyancyFlux >= 55) {
                x = 119 * Math.pow(buoyancyFlux, 0.4);
            } else {
                x = 49 * Math.pow(buoyancyFlux, 0.625);
            }

            double p = this.getCityTerrainWindExpoFactor();

            BouyantFuelFirePlumeRiseFunc bouyantFuelFirePlumeRiseFunc = new BouyantFuelFirePlumeRiseFunc(buoyancyFlux,
                                                                                                         x,
                                                                                                         this.windSpeedAtReferenceHeight,
                                                                                                         this.referenceHeight,
                                                                                                         p);

            effectiveReleaseHeight = solver.solve(10, bouyantFuelFirePlumeRiseFunc, 0, 100);
        } else {

            if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_E)
            {
                s = (0.020 * AtmosphericConcentration.G) / ta;
            }
            else // TYPE_F
            {
                s = (0.035 * AtmosphericConcentration.G) / ta;
            }

            effectiveReleaseHeight = 2.6 * Math.pow(buoyancyFlux / (this.windSpeedAtReferenceHeight * s), 1/3);
        }

        effectiveReleaseHeight += Math.pow(Math.pow(effectiveReleaseHeight, 3) + Math.pow(r / 0.6, 3), 1/3) -
                                  (r / 0.6);

        return effectiveReleaseHeight;
    }

    //endregion

    //region Atmospheric Concentration

    public ArrayList<ConcentrationResult> calcAtmosphericConcentration()
    {
        double buoyancyFlux;
        double emissionRate;

        // Calc emission rate
        if(this.emissionRate == 0)
            emissionRate = this.calcEmissionRate(this.fuelVolume, this.burnDuration);
        else
            emissionRate = this.emissionRate;

        // Calc buoyancy Flux
        buoyancyFlux = this.calcBuoyancyFlux(emissionRate, this.ta);

        ArrayList<ConcentrationResult> results = new ArrayList<ConcentrationResult>();

        double effectiveReleaseHeight = this.calcEffectiveReleaseHeight(this.r,
                                                                        this.ta,
                                                                        buoyancyFlux);

        double windSpeed = this.calcWindSpeed(this.terrainType, effectiveReleaseHeight);

        return this.getConcentrationResults(effectiveReleaseHeight, windSpeed);
    }

    //endregion
}
