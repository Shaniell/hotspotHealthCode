package hotspothealthcode.BL.AtmosphericConcentration;

import java.util.ArrayList;

/**
 * Created by Giladl on 09/01/2016.
 */
public class FireAtmosphericConcentration extends AtmosphericConcentration
{
    //region Data Members

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
     */
    public FireAtmosphericConcentration(double referenceHeight,
                                        double windSpeedAtReferenceHeight,
                                        double surfaceRoughnessHeight,
                                        int sampleTime,
                                        TerrainType terrainType,
                                        double sourceTerm,
                                        ArrayList<Integer> downWindOffsets,
                                        int crossWindOffset,
                                        int verticalOffset)
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
     * @param pasquillStability - The pasquill Stability
     */
    public FireAtmosphericConcentration(double referenceHeight,
                                        double windSpeedAtReferenceHeight,
                                        double surfaceRoughnessHeight,
                                        int sampleTime,
                                        TerrainType terrainType,
                                        double sourceTerm,
                                        ArrayList<Integer> downWindOffsets,
                                        int crossWindOffset,
                                        int verticalOffset,
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
        return 0;
    }

    //endregion
}
