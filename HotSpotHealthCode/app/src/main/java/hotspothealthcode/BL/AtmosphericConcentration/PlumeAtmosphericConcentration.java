package hotspothealthcode.BL.AtmosphericConcentration;

import java.util.ArrayList;

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
                                         boolean calcMomentum)
    {
        super(referenceHeight,
              windSpeedAtReferenceHeight,
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
                                         boolean calcMomentum,
                                         PasquillStability pasquillStability)
    {
        super(referenceHeight,
              windSpeedAtReferenceHeight,
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

            if (h <= 2) {
                effectiveReleaseHeight = 0;

                uh = this.calcWindSpeed(this.terrainType, h / 2);
            } else {
                effectiveReleaseHeight = h;

                uh = this.calcWindSpeed(this.terrainType, h);
            }

            effectiveReleaseHeight += ((1.6 * Math.pow(buoyancyFlux, 1 / 3)) * Math.pow(x, 2 / 3)) / uh;
        } else {
            if (h <= 2) {
                effectiveReleaseHeight = 0;

                uh = this.calcWindSpeed(this.terrainType, 2);
            } else {
                effectiveReleaseHeight = h;

                uh = this.calcWindSpeed(this.terrainType, h);
            }

            if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_E) {
                s = (0.020 * AtmosphericConcentration.G) / ta;
            } else // TYPE_F
            {
                s = (0.035 * AtmosphericConcentration.G) / ta;
            }

            if (uh > 1.4) {
                effectiveReleaseHeight += (2.6 * (Math.pow(buoyancyFlux / (uh * s), 1 / 3)));
            } else {
                effectiveReleaseHeight += 5 * Math.pow(buoyancyFlux, 1 / 4) * Math.pow(s, -3.8);
            }
        }

        return effectiveReleaseHeight;
    }

    /** TODO: FIX THE METHOD!!!
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
        double effectiveReleaseHeight = h > 2 ? h : 0; // H
        double uh; // Wind speed at physical height
        double s;

        uh = calcWindSpeed(this.terrainType, h);

        if(this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_A ||
           this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_B ||
           this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_C ||
           this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_D) {

            effectiveReleaseHeight += ((6 * v * r) / uh);
        }
        else
        {
            momentumFlux = 0.25 * Math.pow(2 * r * v, 2);

            if (this.pasquillStability.stabilityType == PasquillStabilityType.TYPE_E)
            {
                s = 0.000875;
            }
            else // TYPE_F
            {
                s = 0.00175;
            }

            effectiveReleaseHeight += 1.5 * ((Math.pow(momentumFlux, 1/3) / uh) * Math.pow(s, -1/6));
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
     * @param heatEmission - The heat emission
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

        if(flux == 0)
        {
            buoyancyFlux = flux;
        }
        else
        {
            buoyancyFlux = this.calcBuoyancyFlux(v, r, ta, ts);
        }

        buoyantEffectiveHeight = this.calcBouyantEffectiveReleaseHeight(h, ta, buoyancyFlux);

        if(calcMomentum)
        {
            double momentumEffectiveHeight;

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
        ArrayList<ConcentrationResult> results = new ArrayList<ConcentrationResult>();

        double effectiveReleaseHeight = this.calcEffectiveReleaseHeight(this.physicalStackHeight,
                                                                        this.stackExitVelocity,
                                                                        this.stackRadius,
                                                                        AtmosphericConcentration.convertToKelvin(this.airTemp),
                                                                        AtmosphericConcentration.convertToKelvin(this.stackTemp),
                                                                        this.heatEmission,
                                                                        this.calcMomentum);

        double windSpeed = this.calcWindSpeed(this.terrainType, effectiveReleaseHeight);

        return this.getConcentrationResults(effectiveReleaseHeight, windSpeed);
    }

    //endregion
}
