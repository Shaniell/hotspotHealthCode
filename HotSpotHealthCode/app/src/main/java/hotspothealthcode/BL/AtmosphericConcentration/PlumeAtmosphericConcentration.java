package hotspothealthcode.BL.AtmosphericConcentration;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.solvers.NewtonRaphsonSolver;

import java.util.ArrayList;

import hotspothealthcode.BL.AtmosphericConcentration.Functions.BouyantPlumeRiseFunc;
import hotspothealthcode.BL.AtmosphericConcentration.Functions.MomentumPlumeRiseFunc;
import hotspothealthcode.BL.AtmosphericConcentration.Functions.WindSpeedFunc;

/**
 * Created by Giladl on 09/01/2016.
 */
public class PlumeAtmosphericConcentration extends AtmosphericConcentration
{
    //region Data Members

    protected double physicalStackHeight;
    protected double stackExitVelocity;
    protected double stackRadius;
    protected double airTemp;
    protected double stackTemp;
    protected double heatEmission;
    protected boolean calcMomentum;
    protected double effectiveReleaseHeight;

    //endregion

    //region C'tors

    /**
     * The constructor
     * @param referenceHeight - The reference height
     * @param windSpeedAtReferenceHeight - The wind speed at reference height
     * @param surfaceRoughnessHeight - The surface Roughness Height in cm
     * @param sampleTime - The sample time in minutes
     * @param terrainType - The terrain type
     * @param sourceTerm - The source term
     * @param downWindOffsets - The list of downwind offsets
     * @param crossWindOffset - The crosswind offset
     * @param verticalOffset - The vertical Offset
     * @param physicalStackHeight - the physical height (m)
     * @param stackExitVelocity - stack exit velocity (m/s)
     * @param stackRadius - stack radius (m)
     * @param airTemp - ambient air temperature (deg K)
     * @param stackTemp - stack effluent temperature (deg K)
     * @param heatEmission - The heat emission
     * @param calcMomentum - Indicates if to calculate the momentum release height
     */
    public PlumeAtmosphericConcentration(double referenceHeight,
                                         double windSpeedAtReferenceHeight,
                                         int windDirection,
                                         MeteorologicalConditions meteorologicalConditions,
                                         double surfaceRoughnessHeight,
                                         int sampleTime,
                                         TerrainType terrainType,
                                         double sourceTerm,
                                         ArrayList<Double> downWindOffsets,
                                         double crossWindOffset,
                                         double verticalOffset,
                                         double physicalStackHeight,
                                         double stackExitVelocity,
                                         double stackRadius,
                                         double airTemp,
                                         double stackTemp,
                                         double heatEmission,
                                         boolean calcMomentum,
                                         double effectiveReleaseHeight)
    {
        super(referenceHeight,
              windSpeedAtReferenceHeight,
              windDirection,
              meteorologicalConditions,
              surfaceRoughnessHeight,
              sampleTime,
              terrainType,
              sourceTerm,
              downWindOffsets,
              crossWindOffset,
              verticalOffset);

        this.physicalStackHeight = physicalStackHeight;
        this.stackExitVelocity = stackExitVelocity;
        this.stackRadius = stackRadius;
        this.airTemp = airTemp;
        this.stackTemp = stackTemp;
        this.heatEmission = heatEmission;
        this.calcMomentum = calcMomentum;
        this.effectiveReleaseHeight = effectiveReleaseHeight;
    }

    /**
     * The constructor
     * @param referenceHeight - The reference height
     * @param windSpeedAtReferenceHeight - The wind speed at reference height
     * @param surfaceRoughnessHeight - The surface Roughness Height in cm
     * @param sampleTime - The sample time in minutes
     * @param terrainType - The terrain type
     * @param sourceTerm - The source term
     * @param downWindOffsets - The list of downwind offsets
     * @param crossWindOffset - The crosswind offset
     * @param verticalOffset - The vertical Offset
     * @param physicalStackHeight - the physical height (m)
     * @param stackExitVelocity - stack exit velocity (m/s)
     * @param stackRadius - stack radius (m)
     * @param airTemp - ambient air temperature (deg K)
     * @param stackTemp - stack effluent temperature (deg K)
     * @param heatEmission - The heat emission
     * @param calcMomentum - Indicates if to calculate the momentum release height
     * @param pasquillStability - The pasquill Stability
     */
    public PlumeAtmosphericConcentration(double referenceHeight,
                                         double windSpeedAtReferenceHeight,
                                         int windDirection,
                                         MeteorologicalConditions meteorologicalConditions,
                                         double surfaceRoughnessHeight,
                                         int sampleTime,
                                         TerrainType terrainType,
                                         double sourceTerm,
                                         ArrayList<Double> downWindOffsets,
                                         double crossWindOffset,
                                         double verticalOffset,
                                         double physicalStackHeight,
                                         double stackExitVelocity,
                                         double stackRadius,
                                         double airTemp,
                                         double stackTemp,
                                         double heatEmission,
                                         boolean calcMomentum,
                                         double effectiveReleaseHeight,
                                         PasquillStability pasquillStability)
    {
        super(referenceHeight,
              windSpeedAtReferenceHeight,
              windDirection,
              meteorologicalConditions,
              surfaceRoughnessHeight,
              sampleTime,
              terrainType,
              sourceTerm,
              downWindOffsets,
              crossWindOffset,
              verticalOffset,
              pasquillStability);

        this.physicalStackHeight = physicalStackHeight;
        this.stackExitVelocity = stackExitVelocity;
        this.stackRadius = stackRadius;
        this.airTemp = airTemp;
        this.stackTemp = stackTemp;
        this.heatEmission = heatEmission;
        this.calcMomentum = calcMomentum;
        this.effectiveReleaseHeight = effectiveReleaseHeight;
    }

    //endregion

    //region Effective Release Height

    /**
     * The method calculate the Buoyancy Flux
     * @param v - stack exit velocity (m/s)
     * @param r - stack radius (m)
     * @param ta - ambient air temperature (deg K)
     * @param ts - stack effluent temperature (deg K)
     * @return The Buoyancy Flux
     */
    private double calcBuoyancyFlux(double v,
                                    double r,
                                    double ta,
                                    double ts)
    {
        return ((r * r) * AtmosphericConcentration.G * v) * (1 - (ta / ts));
    }

    /**
     * The method calculate the Bouyant Effective Release Height
     * @param h - the physical height (m)
     * @param ta - ambient air temperature (deg K)
     * @param buoyancyFlux - the buoyancy Flux
     * @return The Bouyant Effective Release Height
     */
    private double calcBouyantEffectiveReleaseHeight(double h,
                                                     double ta,
                                                     double buoyancyFlux)
    {
        double effectiveReleaseHeight; // H
        double windSpeedAtEffectiveHeight;
        double windSpeedAtStackHeight;
        double x;
        double uh; // Wind speed at physical height
        double s;

        if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_A ||
            this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_B ||
            this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_C ||
            this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_D) {
            if (buoyancyFlux >= 55) {
                x = 119 * Math.pow(buoyancyFlux, 0.4);
            } else {
                x = 49 * Math.pow(buoyancyFlux, 0.625);
            }

            effectiveReleaseHeight = h;

            uh = this.calcWindSpeed(this.terrainType, h);

            effectiveReleaseHeight += ((1.6 * Math.pow(buoyancyFlux, 1 / 3)) * Math.pow(x, 2 / 3)) / uh;
        } else {
            NewtonRaphsonSolver solver = new NewtonRaphsonSolver();

            double p = this.getCityTerrainWindExpoFactor();

            // Create a wind speed function to find the wind speed at effective release height numericly
            WindSpeedFunc windSpeedFunc = new WindSpeedFunc(this.windSpeedAtReferenceHeight,
                                                            this.referenceHeight,
                                                            p);

            //windSpeedAtEffectiveHeight = solver.solve(10000, windSpeedFunc, 1, 15);

            windSpeedAtStackHeight = this.calcWindSpeed(this.terrainType, this.physicalStackHeight);

            if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_E) {
                s = (0.020 * AtmosphericConcentration.G) / ta;
            } else // TYPE_F
            {
                s = (0.035 * AtmosphericConcentration.G) / ta;
            }

            if (windSpeedAtStackHeight > 1.4)
            {
                // Create bouyant plume rise function to solve it numericly
                /*BouyantPlumeRiseFunc bouyantPlumeRiseFunc = new BouyantPlumeRiseFunc(buoyancyFlux,
                                                                                     s,
                                                                                     this.physicalStackHeight,
                                                                                     this.windSpeedAtReferenceHeight,
                                                                                     this.referenceHeight,
                                                                                     p);

                effectiveReleaseHeight = solver.solve(100, bouyantPlumeRiseFunc, 0, 100);*/

                effectiveReleaseHeight = h + 2.6 * Math.pow((buoyancyFlux / (windSpeedAtStackHeight * s)), 1 / 3);

            } else {
                effectiveReleaseHeight = h + 5 * Math.pow(buoyancyFlux, 1 / 4) * Math.pow(s, -3.8);
            }
        }

        return effectiveReleaseHeight;
    }

    /**
     * The method calculate the Momentum Effective Release Height
     * @param h - the physical height (m)
     * @param v - stack exit velocity (m/s)
     * @param r - stack radius (m)
     * @return The Momentum Effective Release Height
     */
    private double calcMomentumEffectiveReleaseHeight(double h,
                                                      double v,
                                                      double r)
    {
        double momentumFlux = 0; // F
        double effectiveReleaseHeight; // H
        double uh; // Wind speed at physical height
        double s;

        uh = calcWindSpeed(this.terrainType, h);

        if(this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_A ||
           this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_B ||
           this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_C ||
           this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_D) {

            effectiveReleaseHeight = h + ((6 * v * r) / uh);
        }
        else
        {
            momentumFlux = 0.25 * Math.pow(2 * r * v, 2);

            double p = this.getCityTerrainWindExpoFactor();

            if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_E)
            {
                s = 0.000875;
            }
            else // TYPE_F
            {
                s = 0.00175;
            }

            /*NewtonRaphsonSolver solver = new NewtonRaphsonSolver();

            MomentumPlumeRiseFunc momentumPlumeRiseFunc = new MomentumPlumeRiseFunc(momentumFlux,
                                                                                    s,
                                                                                    this.physicalStackHeight,
                                                                                    this.windSpeedAtReferenceHeight,
                                                                                    this.referenceHeight,
                                                                                    p);

            effectiveReleaseHeight = h + solver.solve(100, momentumPlumeRiseFunc, 0, 100);*/

            effectiveReleaseHeight = h + 1.5 * Math.pow((momentumFlux / uh), 1 / 3) * Math.pow(s, -1 / 6);
        }

        return effectiveReleaseHeight;
    }

    /**
     * The method calculate the effective release height
     * @param h - the physical height (m)
     * @param v - stack exit velocity (m/s)
     * @param r - stack radius (m)
     * @param ta - ambient air temperature (deg K)
     * @param ts - stack effluent temperature (deg K)
     * @param calcMomentum - Indicates if to calculate the momentum release height
     * @return the effective release height
     */
    private double calcEffectiveReleaseHeight(double h,
                                              double v,
                                              double r,
                                              double ta,
                                              double ts,
                                              double flux,
                                              boolean calcMomentum)
    {
        double EffectiveReleaseHeight = 0;
        double buoyantEffectiveHeight;
        double buoyancyFlux;

        if(flux != 0)
        {
            buoyancyFlux = flux;
        }
        else
        {
            buoyancyFlux = this.calcBuoyancyFlux(v, r, ta, ts);
        }

        // Calc buoyant release height
        buoyantEffectiveHeight = this.calcBouyantEffectiveReleaseHeight(h, ta, buoyancyFlux);

        if(calcMomentum)
        {
            double momentumEffectiveHeight;

            // Calc momentum release height
            momentumEffectiveHeight = this.calcMomentumEffectiveReleaseHeight(h, v, r);

            EffectiveReleaseHeight = (buoyantEffectiveHeight > momentumEffectiveHeight ? buoyantEffectiveHeight : momentumEffectiveHeight);
        }
        else
        {
            EffectiveReleaseHeight = buoyantEffectiveHeight;
        }

        return EffectiveReleaseHeight;
    }

    //endregion

    //region Atmospheric Concentration

    public ArrayList<ConcentrationResult> calcAtmosphericConcentration()
    {
        double concentration = 0;
        double releaseHeight;
        ArrayList<ConcentrationResult> results = new ArrayList<ConcentrationResult>();

        if(this.pasquillStability == null)
        {
            this.pasquillStability = new PasquillStability(this.windSpeedAtReferenceHeight,
                                                           this.meteorologicalConditions);
        }

        if(this.effectiveReleaseHeight != 0)
        {
            releaseHeight = this.effectiveReleaseHeight;
        }
        else
        {
            releaseHeight = this.calcEffectiveReleaseHeight(this.physicalStackHeight,
                            this.stackExitVelocity,
                            this.stackRadius,
                            AtmosphericConcentration.convertToKelvin(this.airTemp),
                            AtmosphericConcentration.convertToKelvin(this.stackTemp),
                            this.heatEmission,
                            this.calcMomentum);
        }

        double windSpeed = this.calcWindSpeed(this.terrainType, releaseHeight);

        return this.getConcentrationResults(releaseHeight, windSpeed);
    }

    //endregion
}
