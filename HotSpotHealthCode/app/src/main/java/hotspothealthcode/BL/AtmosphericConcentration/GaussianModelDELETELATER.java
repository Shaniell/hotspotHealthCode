package hotspothealthcode.BL.AtmosphericConcentration;

import java.lang.Math;
import java.util.List;

import hotspothealthcode.BL.AtmosphericConcentration.results.ConcentrationPoint;

/**
 * Created by shaniel on 15/08/15.
 */
public class GaussianModelDELETELATER {

    //region Static constants

    private static final double G = 9.8; // gravitational acceleration (9.8 m/s2)

    //endregion

    //region Static Members

    //endregion

    //region Members

    private double C = 0; // C = Time-integrated atmospheric concentration (Ci-s)/(m3).
    private TerrainType terrainType;
    private double Q = 0; // Q = Source term (Ci).
    private double H = 0; // H = Effective release height (m).
    private double plumePhysicalHeight; // plumePhysicalHeight - h (m)
    private double lambda = 0; // lambda = = Radioactive decay constant (s –1).
    private double x = 0; // x = Downwind distance (m).
    private double y = 0; // y = Crosswind distance (m).
    private double z = 0; // z = Vertical axis distance (m).
    private double sigmaY = 0; // SigmaY = Standard deviation of the integrated concentration distribution in the crosswind direction (m).
    private double sigmaZ = 0; // SigmaZ = Standard deviation of the integrated concentration distribution in the vertical direction (m).
    //private double u = 0; // u = Average wind speed at the effective release height (m/s). TODO: UNDERSTAND IF REQUIRED
    private double L = 0; // L = Inversion layer height (m).
    private double DFx = 0.025; // DF(x) = Plume Depletion factor
    private double sampleTime; // Sample time for the deviation of the integrated concentration distribution. default value 10 minutes
    private double z0; // Surface roughness height (cm) values between 3 to 300 cm.
    private double uH; // Wind speed (m/s), at height H (m)
    private double referenceHeight; // The reference height for the wind speed (values between 2 and 100)
    private double uReferenceHeight; // wind speed at the effective release height (m/s)
    private PasquillStability PasquillStability; // Stability type needed for calculations
    private double w;
    //endregion

    //region C'tors

    public GaussianModelDELETELATER(TerrainType terrainType,
                                    MeteorologicalConditions meteorologicalCondition,
                                    double Q,
                                    double H,
                                    double lambda,
                                    double x,
                                    double y,
                                    double z,
                                    double L,
                                    double DFx,
                                    double z0,
                                    double referenceHeight,
                                    double uRreferenceHeight,
                                    double sampleTime,
                                    double W)
    {
        this.initialize(terrainType, meteorologicalCondition, Q, H, lambda, x, y, z, L, z0, referenceHeight, uRreferenceHeight, sampleTime,W);
    }

    public GaussianModelDELETELATER(TerrainType terrainType,
                                    MeteorologicalConditions meteorologicalCondition,
                                    double Q,
                                    double H,
                                    double lambda,
                                    double x,
                                    double y,
                                    double z,
                                    double L,
                                    double z0,
                                    double referenceHeight,
                                    double uReferenceHeight,
                                    double W)
    {
        this.initialize(terrainType, meteorologicalCondition, Q, H, lambda, x, y, z, L, z0, referenceHeight, uReferenceHeight, 10,W);
    }

    //endregion

    //region Other methods

    public void calcPlumeAtmosphericConcentration(double sourceTerm,
                                                  List<Integer> downWindOffsets,
                                                  int crossWindOffset,
                                                  int verticalOffset)
    {

    }

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
        retVal *= Math.exp((-point.getX()) / windSpeedAtHeight) * this.DFx;

        return retVal;
    }

    private void initialize(TerrainType terrainType,
                            MeteorologicalConditions meteorologicalCondition,
                            double Q,
                            double H,
                            double lambda,
                            double x,
                            double y,
                            double z,
                            double L,
                            double z0,
                            double referenceHeight,
                            double uReferenceHeight,
                            double sampleTime,
                            double W)
    {
        this.terrainType = terrainType;
        this.Q = Q;
        this.H = H;
        this.lambda = lambda;
        this.x = x;
        this.y = y;
        this.z = z;
        this.L = L;
        this.DFx = calcDFx();
        this.z0 = z0;
        this.referenceHeight = referenceHeight;
        this.uReferenceHeight = uReferenceHeight;
        this.sampleTime = sampleTime;

        this.w = W;

        this.PasquillStability = new PasquillStability(uReferenceHeight, meteorologicalCondition);

        calculate();
    }

    public double calcDFx(){
        return 0.025;
    }

    public void calculate()
    {
        double buoyantEffectiveHeight;
        double momentumEffectiveHeight;

        // Sigma Z,Y calculations
        calcSigmaYZ(this.terrainType);

        // Calculate effective height TODO: implement the choice logic

        this.H = calcFuelFireEffectiveReleaseHeight(1, 1, 1, 1); //TODO: GET REAL VALUES

        buoyantEffectiveHeight = calcBouyantEffectiveReleaseHeight(this.plumePhysicalHeight, 1, 1, 1, 1, 0); // TODO: GET REAL VALUES
        momentumEffectiveHeight = calcMomentumEffectiveReleaseHeight(this.plumePhysicalHeight, 1, 1); // TODO: GET REAL VALUES

        // Take the bigger between the values
        this.H = (buoyantEffectiveHeight > momentumEffectiveHeight ? buoyantEffectiveHeight : momentumEffectiveHeight);

        // Calculate effective height wind speed
        this.uH = calcWindSpeed(this.terrainType, this.H);

        // Calculate the gaussian model
        // TODO: implement the choice logic
        this.C = calcGasConcentration();
        this.C = calcGasConcentrationWithInversionLayer();
    }

    //endregion

    /**************Calculate gas concentration********************/
    //region Calculate gas concentration

    /***
     * The following Gaussian model equations determine the time-integrated
     * atmospheric concentration of a gas or an aerosol at any point in space.
     * @return The Gas Concentration
     */
    public double calcGasConcentration()
    {
        double retVal;

        retVal = this.Q / (2 * Math.PI * this.sigmaY * this.sigmaZ * this.uH);

        retVal *= Math.exp(-0.5 * Math.pow(this.y / this.sigmaY, 2));
        retVal *= (Math.exp(-0.5 * Math.pow((this.z - this.H) / this.sigmaZ, 2)) +
                Math.exp(-0.5 * Math.pow((this.z + this.H) / this.sigmaZ, 2)));
        retVal *= Math.exp((-this.x) / this.uH) * this.DFx;

        return retVal;
    }

    /***
     * If the inversion layer option is in effect, and z exceeds the inversion height (L),
     * the Following equation is used.
     * @return The Inversion Layer
     */
    public double calcGasConcentrationWithInversionLayer(){
        // To avoid the sharp transition between the two above equations, the transition into the
        // Inversion layer equation begins when z equals 70% of L and is complete when z equals
        // L. Between these two values, the two equations are linearly interpolated.

        double retVal;

        retVal = this.Q / (Math.sqrt(2 * Math.PI) * this.sigmaY * this.L * this.uH);

        retVal *= Math.exp(-0.5 * Math.pow(this.y / this.sigmaY, 2));
        retVal *= Math.exp((-this.x) / this.uH) * this.DFx;

        return retVal;
    }

    //endregion

    /**************Deviation Calculation (sigmaXY)********************/
    //region Deviation Calculation

    /**
     * Calculate the deviation
     * @param terrainType - The terrain type
     */
    private void calcSigmaYZ(TerrainType terrainType)
    {
        switch (terrainType)
        {
            case STANDARD_TERRAIN: {
                calcStandardTerrainSigmaYZ();

                break;
            }

            case CITY_TERRAIN: {
                calcCityTerrainSigmaYZ();

                break;
            }
        }

        // Add sample time to calculation (in case the sample time is the default 10 minutes
        // the sigmaY will remain the same)
        this.sigmaY = this.sigmaY * Math.pow((this.sampleTime / 10), 0.2);

        // Add Surface roughness height to calculation (z0)
        this.sigmaZ = this.sigmaZ * Math.pow((this.z0 / 3), 0.2);
    }

    /**
     * Calculate with standard terrain
     */
    private void calcStandardTerrainSigmaYZ(){

        double parameter = 0;
        double Aparameter = 0;
        double Bparameter = 1;

        switch (this.PasquillStability.stabilityType){
            case TYPE_A: {  parameter = 0.22; Aparameter = 0.20; Bparameter = 1; break; }
            case TYPE_B: {  parameter = 0.16; Aparameter = 0.12; Bparameter = 1; break; }
            case TYPE_C: {  parameter = 0.11; Aparameter = 0.080; Bparameter = Math.sqrt(1 + (0.0002 * this.x)); break; }
            case TYPE_D: {  parameter = 0.08; Aparameter = 0.060; Bparameter = Math.sqrt(1 + (0.0015 * this.x)); break; }
            case TYPE_E: {  parameter = 0.06; Aparameter = 0.030; Bparameter = 1 + (0.0003 * this.x); break; }
            case TYPE_F: {  parameter = 0.04; Aparameter = 0.016; Bparameter = 1 + (0.0003 * this.x); break; }
        }

        this.sigmaY = ((parameter * x) / Math.sqrt(1 + 0.0001 * x));
        this.sigmaZ = ((Aparameter * x) / Bparameter);
    }

    /**
     * Calculate with city terrain
     */
    private void calcCityTerrainSigmaYZ(){

        double parameter = 0;
        double Aparameter = 0;
        double Bparameter = 1;

        switch (this.PasquillStability.stabilityType){
            case TYPE_A: { }
            case TYPE_B: {  parameter = 0.32; Aparameter = 0.24 * Math.sqrt(1 + (0.001 * this.x)); Bparameter = 1; break; }
            case TYPE_C: {  parameter = 0.22; Aparameter = 0.20; Bparameter = 1; break; }
            case TYPE_D: {  parameter = 0.16; Aparameter = 0.14; Bparameter = Math.sqrt(1 + (0.0003 * this.x)); break; }
            case TYPE_E: {  parameter = 0.11; Aparameter = 0.08; Bparameter = Math.sqrt(1 + (0.0015 * this.x)); break; }
            case TYPE_F: {  parameter = 0.11; Aparameter = 0.08; Bparameter = Math.sqrt(1 + (0.0015 * this.x)); break; }
        }

        this.sigmaY = ((parameter * x) / Math.sqrt(1 + 0.0004 * x));
        this.sigmaZ = ((Aparameter * x) / Bparameter);
    }

    //endregion

    /**************Wind Speed********************/
    //region Calculate Wind Speed

    /**
     * Calculate the standard terrain wind expo factor
     * @return the standard terrain wind expo factor
     */
    private double getStandardTerrainWindExpoFactor() {
        double p = 0;

        switch (this.PasquillStability.stabilityType) {
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
    private double getCityTerrainWindExpoFactor() {
        double p = 0;

        switch (this.PasquillStability.stabilityType) {
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
    private double calcWindSpeed(TerrainType terrainType, double h)
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

        return this.uReferenceHeight * Math.pow((h / this.referenceHeight), p);
    }

    //endregion

    /**************Effective Release Height (H)********************/
    //region Buoyant Plume Rise

    /**
     * The method calculate the Bouyant Effective Release Height
     * @param h - the physical height (m)
     * @param v - stack exit velocity (m/s)
     * @param r - stack radius (m)
     * @param ta - ambient air temperature (deg K)
     * @param ts - stack effluent temperature (deg K)
     * @param flux - the flux (if 0 will use v, r, ta, ts to calculate flux)
     * @return The Bouyant Effective Release Height
     */
    private double calcBouyantEffectiveReleaseHeight(double h,
                                                     double v,
                                                     double r,
                                                     double ta,
                                                     double ts,
                                                     double flux)
    {
        double buoyancyFlux; // F
        double effectiveReleaseHeight; // H
        double x;
        double uh; // Wind speed at physical height
        double s;

        if (flux == 0) {
            buoyancyFlux = ((r * r) * GaussianModelDELETELATER.G * v) * (1 - (ta / ts));
        }
        else
        {
            buoyancyFlux = flux;
        }

        if(this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_A ||
           this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_B ||
           this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_C ||
           this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_D) {
            if (buoyancyFlux >= 55) {
                x = 119 * Math.pow(buoyancyFlux, 0.4);
            } else {
                x = 49 * Math.pow(buoyancyFlux, 0.625);
            }

            if(h <= 2)
            {
                effectiveReleaseHeight = 0;

                uh = calcWindSpeed(this.terrainType, h / 2);
            }
            else
            {
                effectiveReleaseHeight = h;

                uh = calcWindSpeed(this.terrainType, h);
            }

            effectiveReleaseHeight += ((1.6 * Math.pow(buoyancyFlux, 1 / 3)) * Math.pow(x, 2 / 3)) / uh;
        }
        else
        {
            if(h <= 2)
            {
                effectiveReleaseHeight = 0;

                uh = calcWindSpeed(this.terrainType, 2);
            }
            else
            {
                effectiveReleaseHeight = h;

                uh = calcWindSpeed(this.terrainType, h);
            }

            if (this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_E)
            {
                s = (0.020 * GaussianModelDELETELATER.G) / ta;
            }
            else // TYPE_F
            {
                s = (0.035 * GaussianModelDELETELATER.G) / ta;
            }

            if(uh > 1.4)
            {
                effectiveReleaseHeight += (2.6 * (Math.pow(buoyancyFlux / (uh * s), 1/3)));
            }
            else
            {
                effectiveReleaseHeight += 5 * Math.pow(buoyancyFlux, 1/4) * Math.pow(s, -3.8);
            }
        }

        return effectiveReleaseHeight;
    }

    //endregion

    //region Momentum Plume Rise

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
        double effectiveReleaseHeight = h > 2 ? h : 0; // H
        double uh; // Wind speed at physical height
        double s;

        uh = calcWindSpeed(this.terrainType, h);

        if(this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_A ||
           this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_B ||
           this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_C ||
           this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_D) {

            effectiveReleaseHeight += ((6 * v * r) / uh);
        }
        else
        {
            momentumFlux = 0.25 * Math.pow(2 * r * v, 2);

            if (this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_E)
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

    //endregion

    //region Fuel Fire Plume Rise

    /**
     * The method calculate the Fuel Fire Effective Release Height
     * @param v - volume of fuel (gallons) burned in time, dt(s)
     * @param t - duration of fuel fire (s)
     * @param r - Radius of burning fuel pool (m)
     * @param temperature - ambient air temperature (K)
     * @return the Fuel Fire Effective Release Height
     */
    private double calcFuelFireEffectiveReleaseHeight(double v,
                                                      double t,
                                                      double r,
                                                      double temperature)
    {
        double q; //heat emission rate (cal/s) (Q)
        double flux;
        double effectiveReleaseHeight;

        q = 2.58 * Math.pow(10, 7) * (v / t);
        flux = 0.011 * (q / temperature);

        effectiveReleaseHeight = this.calcBouyantEffectiveReleaseHeight(2, 0, 0, temperature, 0, flux);

        effectiveReleaseHeight = Math.pow(Math.pow(effectiveReleaseHeight, 3) + Math.pow(r / 0.6, 3), 1/3);
        effectiveReleaseHeight -= (r / 0.6);

        return effectiveReleaseHeight;
    }
    //endregion



    //***************** Explosion (Non-nuclear) ******************//

    // The expression for the time after detonation (tm) at which the maximum cloud rise is attained (e.g.
// the time at which the cloud becomes thermally neutral)
    private double timeAtCluudThermallyNeutral(){

        //tm(w) = 21.6 w^0.33
        return (21.6 * Math.pow(this.w, 0.33));
    }


    // The expressions for the stabilized cloud top (H) as a function of high explosive for unstable
// (Stability class A, B and C) and Stable/Neutral (Stability class D, E F, and G)
    private double StabilizedCloudTop() {

        double retValH = 0;

        // If Stability class is A.B and C Then it's unstable
        if ((this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_A) ||
                (this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_B) ||
                (this.PasquillStability.stabilityType == PasquillStabilityType.TYPE_C)) {
            retValH = 27.4 * Math.pow(this.w, 0.48);
        }

        // If Stability class is D,E,F and G Then it's stable/Neutral
        else
        {
            retValH = 23.3 * Math.pow(this.w, 0.44);
        }

        return  retValH;
    }


    // D calculation
    // TODO: understans better the X and Y params
    // TODO: check out the A param, the article says, it's 10 iterations as i calculated, but not sure.
    private double depositionCalculation(){
        double D = 0;
        double Yimax = this.y;
        double Ximax = this.x;
        double A;

        for (double i=0; i <= 10; i++){
            A = i * 100.0;
            D += (A)/((Math.PI*Yimax)+((Ximax - Yimax)*2*Yimax));
        }

        return  D;
    }
}


