package hotspothealthcode.BL.AtmosphericConcentration;

import java.util.ArrayList;

/**
 * Created by Giladl on 09/01/2016.
 */
public class AtmosphericConcentration
{
    //region Static constants

    protected static final double G = 9.8; // gravitational acceleration (9.8 m/s2)
    protected static final double DFX = 0.025; // DF(x) = Plume Depletion factor

    //endregion

    //region Data Members

    protected PasquillStability pasquillStability;
    protected MeteorologicalConditions meteorologicalConditions;
    protected double referenceHeight;
    protected double windSpeedAtReferenceHeight;
    protected int windDirection;
    protected double surfaceRoughnessHeight; // cm
    protected int sampleTime;
    protected TerrainType terrainType;
    protected double sourceTerm;
    protected ArrayList<Double> downWindOffsets;
    protected double crossWindOffset;
    protected double verticalOffset;

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
    public AtmosphericConcentration(double referenceHeight,
                                    double windSpeedAtReferenceHeight,
                                    int windDirection,
                                    MeteorologicalConditions meteorologicalConditions,
                                    double surfaceRoughnessHeight,
                                    int sampleTime,
                                    TerrainType terrainType,
                                    double sourceTerm,
                                    ArrayList<Double> downWindOffsets,
                                    double crossWindOffset,
                                    double verticalOffset)
    {
        this.referenceHeight = referenceHeight;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.windDirection = windDirection;
        this.meteorologicalConditions = meteorologicalConditions;
        this.surfaceRoughnessHeight = surfaceRoughnessHeight;
        this.sampleTime = sampleTime;
        this.terrainType = terrainType;
        this.sourceTerm = sourceTerm;
        this.downWindOffsets = downWindOffsets;
        this.crossWindOffset = crossWindOffset;
        this.verticalOffset = verticalOffset;
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
    public AtmosphericConcentration(double referenceHeight,
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
                                    PasquillStability pasquillStability)
    {
        this.referenceHeight = referenceHeight;
        this.windSpeedAtReferenceHeight = windSpeedAtReferenceHeight;
        this.meteorologicalConditions = meteorologicalConditions;
        this.windDirection = windDirection;
        this.surfaceRoughnessHeight = surfaceRoughnessHeight;
        this.sampleTime = sampleTime;
        this.terrainType = terrainType;
        this.sourceTerm = sourceTerm;
        this.downWindOffsets = downWindOffsets;
        this.crossWindOffset = crossWindOffset;
        this.verticalOffset = verticalOffset;
        this.pasquillStability = pasquillStability;
    }

    //endregion

    //region Static Methods

    protected static double convertToKelvin(double temp)
    {
        return temp + 273.15;
    }

    //endregion

    //region Gussian Equation

    /**
     * The method calculate the Gussian Equation
     * @param sourceTerm - The source term (Ci).
     * @param sigmaY - Standard deviation of the integrated concentration distribution in the crosswind direction (m).
     * @param sigmaZ - Standard deviation of the integrated concentration distribution in the vertical direction (m).
     * @param effectiveReleaseHeight - Effective release height (m)
     * @param windSpeedAtHeight - Wind speed (m/s), at height effective release height (m)
     * @param point - Point to calculate the gussian equation (x, y , z)
     * @return The time-integrated atmospheric
               concentration of a gas or an aerosol at any point in space
     */
    private double calcGussianEquation(double sourceTerm,
                                         double sigmaY,
                                         double sigmaZ,
                                         double effectiveReleaseHeight,
                                         double windSpeedAtHeight,
                                         ConcentrationPoint point)
    {
        double retVal;

        retVal = sourceTerm / (2 * Math.PI * sigmaY * sigmaZ * windSpeedAtHeight);

        retVal *= Math.exp(-0.5 * Math.pow(point.getY() / sigmaY, 2));
        retVal *= (Math.exp(-0.5 * Math.pow((point.getZ() - effectiveReleaseHeight) / sigmaZ, 2)) +
                Math.exp(-0.5 * Math.pow((point.getZ() + effectiveReleaseHeight) / sigmaZ, 2)));
        retVal *= Math.exp((-point.getX()) / windSpeedAtHeight) * AtmosphericConcentration.DFX;

        return retVal;
    }

    /**
     * The method calculate the Gussian Equation
     * @param sourceTerm - The source term (Ci).
     * @param sigmaY - Standard deviation of the integrated concentration distribution in the crosswind direction (m).
     * @param sigmaZ - Standard deviation of the integrated concentration distribution in the vertical direction (m).
     * @param effectiveReleaseHeight - Effective release height (m)
     * @param windSpeedAtHeight - Wind speed (m/s), at height effective release height (m)
     * @param inversionHeight - The inversion layer height (m)
     * @param point - Point to calculate the gussian equation (x, y , z)
     * @return The time-integrated atmospheric
                concentration of a gas or an aerosol at any point in space
     */
    private double calcGussianEquationWithInversionLayer(double sourceTerm,
                                                           double sigmaY,
                                                           double sigmaZ,
                                                           double effectiveReleaseHeight,
                                                           double windSpeedAtHeight,
                                                           int inversionHeight,
                                                           ConcentrationPoint point){
        // To avoid the sharp transition between the two above equations, the transition into the
        // Inversion layer equation begins when z equals 70% of L and is complete when z equals
        // L. Between these two values, the two equations are linearly interpolated.

        double retVal;

        retVal = sourceTerm / (Math.sqrt(2 * Math.PI) * sigmaY * inversionHeight * windSpeedAtHeight);

        retVal *= Math.exp(-0.5 * Math.pow(point.getY() / sigmaY, 2));
        retVal *= Math.exp((-point.getX()) / windSpeedAtHeight) * AtmosphericConcentration.DFX;

        return retVal;
    }

    //endregion

    //region Wind Speed

    /**
     * Calculate the standard terrain wind expo factor
     * @return the standard terrain wind expo factor
     */
    private double getStandardTerrainWindExpoFactor() {
        double p = 0;

        switch (this.pasquillStability.stabilityType) {
            case TYPE_A: {
                p = 0.07;
                break;
            }
            case TYPE_B: {
                p = 0.07;
                break;
            }
            case TYPE_C: {
                p = 0.10;
                break;
            }
            case TYPE_D: {
                p = 0.15;
                break;
            }
            case TYPE_E: {
                p = 0.35;
                break;
            }
            case TYPE_F: {
                p = 0.55;
                break;
            }
        }

        return p;
    }

    /**
     * Calculate the city terrain wind expo factor
     * @return the city terrain wind expo factor
     */
    protected double getCityTerrainWindExpoFactor() {
        double p = 0;

        switch (this.pasquillStability.stabilityType) {
            case TYPE_A: {
                p = 0.15;
                break;
            }
            case TYPE_B: {
                p = 0.15;
                break;
            }
            case TYPE_C: {
                p = 0.20;
                break;
            }
            case TYPE_D: {
                p = 0.25;
                break;
            }
            case TYPE_E: {
                p = 0.40;
                break;
            }
            case TYPE_F: {
                p = 0.60;
                break;
            }
        }

        return p;
    }

    /**
     * Calculate the wind speed in the given height
     * @param terrainType - The terrain type
     * @param h - The height
     * @return the wind speed in the given height
     */
    protected double calcWindSpeed(TerrainType terrainType, double h)
    {
        double p = 0;

        switch (terrainType)
        {
            case STANDARD_TERRAIN: {
                p = getStandardTerrainWindExpoFactor();

                break;
            }

            case CITY_TERRAIN: {
                p = getCityTerrainWindExpoFactor();

                break;
            }
        }

        return this.windSpeedAtReferenceHeight * Math.pow((h / this.referenceHeight), p);
    }

    //endregion

    //region Deviation Calculation

    /**
     * Calculate the deviation (sigmaY and sigmaZ)
     * @param terrainType - The terrain type
     * @param downWindOffset - The downwind axis (x-axis) offset (m)
     * @return List containing sigmaY at 0 index and sigmaZ at 1 index
     */
    protected ArrayList<Double> calcSigmaYZ(TerrainType terrainType,
                                            double downWindOffset)
    {
        double sigmaY;
        double sigmaZ;
        ArrayList<Double> lst = new ArrayList<>();

        switch (terrainType)
        {
            case STANDARD_TERRAIN: {
                lst = this.calcStandardTerrainSigmaYZ(downWindOffset);

                break;
            }

            case CITY_TERRAIN: {
                lst = this.calcCityTerrainSigmaYZ(downWindOffset);

                break;
            }
        }

        sigmaY = lst.get(0);
        sigmaZ = lst.get(1);

        // Add sample time to calculation (in case the sample time is the default 10 minutes
        // the sigmaY will remain the same)
        sigmaY = sigmaY * Math.pow((this.sampleTime / 10), 0.2);

        // Add Surface roughness height to calculation (z0)
        sigmaZ = sigmaZ * Math.pow((this.surfaceRoughnessHeight / 3), 0.2);

        lst.clear();

        lst.add(sigmaY);
        lst.add(sigmaZ);

        return lst;
    }

    /**
     * Calculate with standard terrain
     * @param downWindOffset - The downwind axis (x-axis) offset (m)
     * @return List containing sigmaY at 0 index and sigmaZ at 1 index
     */
    protected ArrayList<Double> calcStandardTerrainSigmaYZ(double downWindOffset){

        double parameter = 0;
        double Aparameter = 0;
        double Bparameter = 1;
        double sigmaY;
        double sigmaZ;
        ArrayList<Double> lst = new ArrayList<>();

        switch (this.pasquillStability.stabilityType){
            case TYPE_A: {  parameter = 0.22; Aparameter = 0.20; Bparameter = 1; break; }
            case TYPE_B: {  parameter = 0.16; Aparameter = 0.12; Bparameter = 1; break; }
            case TYPE_C: {  parameter = 0.11; Aparameter = 0.080; Bparameter = Math.sqrt(1 + (0.0002 * downWindOffset)); break; }
            case TYPE_D: {  parameter = 0.08; Aparameter = 0.060; Bparameter = Math.sqrt(1 + (0.0015 * downWindOffset)); break; }
            case TYPE_E: {  parameter = 0.06; Aparameter = 0.030; Bparameter = 1 + (0.0003 * downWindOffset); break; }
            case TYPE_F: {  parameter = 0.04; Aparameter = 0.016; Bparameter = 1 + (0.0003 * downWindOffset); break; }
        }

        sigmaY = ((parameter * downWindOffset) / Math.sqrt(1 + 0.0001 * downWindOffset));
        sigmaZ = ((Aparameter * downWindOffset) / Bparameter);

        lst.add(sigmaY);
        lst.add(sigmaZ);

        return lst;
    }

    /**
     * Calculate with city terrain
     * @param downWindOffset - The downwind axis (x-axis) offset (m)
     * @return List containing sigmaY at 0 index and sigmaZ at 1 index
     */
    protected ArrayList<Double> calcCityTerrainSigmaYZ(double downWindOffset){

        double parameter = 0;
        double Aparameter = 0;
        double Bparameter = 1;
        double sigmaY;
        double sigmaZ;
        ArrayList<Double> lst = new ArrayList<>();

        switch (this.pasquillStability.stabilityType){
            case TYPE_A: { }
            case TYPE_B: {  parameter = 0.32; Aparameter = 0.24 * Math.sqrt(1 + (0.001 * downWindOffset)); Bparameter = 1; break; }
            case TYPE_C: {  parameter = 0.22; Aparameter = 0.20; Bparameter = 1; break; }
            case TYPE_D: {  parameter = 0.16; Aparameter = 0.14; Bparameter = Math.sqrt(1 + (0.0003 * downWindOffset)); break; }
            case TYPE_E: {  parameter = 0.11; Aparameter = 0.08; Bparameter = Math.sqrt(1 + (0.0015 * downWindOffset)); break; }
            case TYPE_F: {  parameter = 0.11; Aparameter = 0.08; Bparameter = Math.sqrt(1 + (0.0015 * downWindOffset)); break; }
        }

        sigmaY = ((parameter * downWindOffset) / Math.sqrt(1 + 0.0004 * downWindOffset));
        sigmaZ = ((Aparameter * downWindOffset) / Bparameter);

        lst.add(sigmaY);
        lst.add(sigmaZ);

        return lst;
    }

    //endregion

    //region Result Methods

    protected ArrayList<ConcentrationResult> getConcentrationResults(double effectiveReleaseHeight,
                                                                     double windSpeed)
    {
        ArrayList<ConcentrationResult> results = new ArrayList<ConcentrationResult>();

        // Calculate the concentration at points:
        // (x, 0, 0, H), (x, y, 0, H), (x, -y, 0, H), (x, y, z, H), (x, -y, z, H)
        for (double offset: this.downWindOffsets)
        {
            // Calculate sigmaY and sigmaZ
            ArrayList<Double> lst = this.calcSigmaYZ(this.terrainType, offset);

            double sigmaY = lst.get(0);
            double sigmaZ = lst.get(1);

            // (x, 0, 0, H)
            this.addResult(this.sourceTerm,
                    sigmaY,
                    sigmaZ,
                    effectiveReleaseHeight,
                    windSpeed,
                    new ConcentrationPoint(offset * 1000, 0, 0),
                    results);

            // (x, y, 0, H)
            this.addResult(this.sourceTerm,
                    sigmaY,
                    sigmaZ,
                    effectiveReleaseHeight,
                    windSpeed,
                    new ConcentrationPoint(offset * 1000, this.crossWindOffset * 1000, 0),
                    results);

            // (x, -y, 0, H)
            this.addResult(this.sourceTerm,
                    sigmaY,
                    sigmaZ,
                    effectiveReleaseHeight,
                    windSpeed,
                    new ConcentrationPoint(offset * 1000, -this.crossWindOffset * 1000, 0),
                    results);

            // (x, y, z, H)
            this.addResult(this.sourceTerm,
                    sigmaY,
                    sigmaZ,
                    effectiveReleaseHeight,
                    windSpeed,
                    new ConcentrationPoint(offset * 1000, this.crossWindOffset * 1000, this.verticalOffset),
                    results);

            // (x, -y, z, H)
            this.addResult(this.sourceTerm,
                    sigmaY,
                    sigmaZ,
                    effectiveReleaseHeight,
                    windSpeed,
                    new ConcentrationPoint(offset * 1000, -this.crossWindOffset * 1000, this.verticalOffset),
                    results);
        }

        return results;
    }

    private void addResult(double sourceTerm,
                           double sigmaY,
                           double sigmaZ,
                           double effectiveReleaseHeight,
                           double windSpeed,
                           ConcentrationPoint point,
                           ArrayList<ConcentrationResult> results)
    {
        double concentration = this.calcGussianEquation(sourceTerm,
                sigmaY,
                sigmaZ,
                effectiveReleaseHeight,
                windSpeed,
                point);

        results.add(new ConcentrationResult(point, concentration, point.getX() / windSpeed));
    }

    //endregion
}
