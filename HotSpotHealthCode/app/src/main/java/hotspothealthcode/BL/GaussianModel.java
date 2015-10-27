package hotspothealthcode.BL;

/**
 * Created by shaniel on 15/08/15.
 */
public class GaussianModel {

    private double C = 0; // C = Time-integrated atmospheric concentration (Ci-s)/(m3).
    private double Q = 0; // Q = Source term (Ci).
    private double H = 0; // H = Effective release height (m).
    private double lambda = 0; // lambda = = Radioactive decay constant (s –1).
    private double x = 0; // x = Downwind distance (m).
    private double y = 0; // y = Crosswind distance (m).
    private double z = 0; // z = Vertical axis distance (m).
    private double sigmaY = 0; // SigmaY = Standard deviation of the integrated concentration distribution in the crosswind direction (m).
    private double sigmaZ = 0; // SigmaZ = Standard deviation of the integrated concentration distribution in the vertical direction (m).
    private double u = 0; // u = Average wind speed at the effective release height (m/s).
    private double L = 0; // L = Inversion layer height (m).
    private double DFx = 0; // DF(x) = Plume Depletion factor
    private double sampleTime; // Sample time for the deviation of the integrated concentration distribution. default value 10 minutes
    private double z0; // Surface roughness height (cm) values between 3 to 300 cm.
    private double uH; // Wind speed (m/s), at height H (m)
    private double referenceHeight; // The referance height for the wind speed (values between 2 and 100)
    private double uRreferenceHeight;


    // Stability type needed for calculations
    private static PasquillStability PasquillStability;

    public GaussianModel(double Q,
                         double H,
                         double lambda,
                         double x,
                         double y,
                         double z,
                         double u,
                         double L,
                         double DFx,
                         double z0,
                         double sampleTime)
    {
        this.initialize(Q, H, lambda, x, y, z, u, L, DFx, z0, sampleTime);
    }

    public GaussianModel(double Q,
                         double H,
                         double lambda,
                         double x,
                         double y,
                         double z,
                         double u,
                         double L,
                         double DFx,
                         double z0)
    {
        this.initialize(Q, H, lambda, x, y, z, u, L, DFx, z0, 10);
    }

    private void initialize(double Q,
                            double H,
                            double lambda,
                            double x,
                            double y,
                            double z,
                            double u,
                            double L,
                            double DFx,
                            double z0,
                            double sampleTime)
    {
        this.Q = Q;
        this.H = H;
        this.lambda = lambda;
        this.x = x;
        this.y = y;
        this.z = z;
        this.u = u;
        this.L = L;
        this.DFx = DFx;
        this.z0 = z0;
        this.sampleTime = sampleTime;
    }

    public void calculate()
    {
        // Sigma Z,Y calculations
        // TODO: implement the choice logic
        calcStandardTerrainSigmaYZ();
        calcCityTerrainSigmaYZ();

        // Calculate the gaussian model
        // TODO: implement the choice logic
        this.C = calcGasConcentration();
        this.C = calcInversionLayerConcentration();
    }


    /***
     * The following Gaussian model equations determine the time-integrated
     * atmospheric concentration of a gas or an aerosol at any point in space.
     * @return The Gas Concentration
     */
    public double calcGasConcentration(){
        double retVal;

        retVal = this.Q / (2 * Math.PI * this.sigmaY * this.sigmaZ * this.u);

        retVal *= Math.exp(-0.5 * Math.pow(this.y / this.sigmaY, 2));
        retVal *= (Math.exp(-0.5 * Math.pow((this.z - this.H) / this.sigmaZ, 2)) +
                   Math.exp(-0.5 * Math.pow((this.z + this.H) / this.sigmaZ, 2)));
        retVal *= Math.exp((-this.lambda * this.x) / this.u) * this.DFx;

        return retVal;
    }

    /***
     * If the inversion layer option is in effect, and z exceeds the inversion height (L),
     * the Following equation is used.
     * @return The Inversion Layer
     */
    public double calcInversionLayerConcentration(){
        // To avoid the sharp transition between the two above equations, the transition into the
        // Inversion layer equation begins when z equals 70% of L and is complete when z equals
        // L. Between these two values, the two equations are linearly interpolated.

        double retVal;

        retVal = this.Q / (Math.sqrt(2 * Math.PI) * this.sigmaY * this.L * this.u);

        retVal *= Math.exp(-0.5 * Math.pow(this.y / this.sigmaY, 2));
        retVal *= Math.exp((-this.lambda * this.x) / this.u) * this.DFx;

        return retVal;
    }

    public void calcSigmaYZ(TerrainType terrainType)
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

    private void calcStandardTerrainSigmaYZ(){

        double parameter = 0;
        double Aparameter = 0;
        double Bparameter = 1;

        switch (GaussianModel.PasquillStability.stabilityType){
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

    private void calcCityTerrainSigmaYZ(){

        double parameter = 0;
        double Aparameter = 0;
        double Bparameter = 1;

        switch (GaussianModel.PasquillStability.stabilityType){
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

    private void calcStandardTerrainWindSpeed()
    {
        double p = 0;

        switch (GaussianModel.PasquillStability.stabilityType){
            case TYPE_A: {  p = 0.07; break; }
            case TYPE_B: {  p = 0.07; break; }
            case TYPE_C: {  p = 0.10; break; }
            case TYPE_D: {  p = 0.15; break; }
            case TYPE_E: {  p = 0.35; break; }
            case TYPE_F: {  p = 0.55; break; }
        }

        //this.uH =

        //TODO: COMPLETE Standard Terrain Wind Speed AND city Terrain Wind Speed methods
    }

    private void calcCityTerrainWindSpeed()
    {
        double p = 0;

        switch (GaussianModel.PasquillStability.stabilityType){
            case TYPE_A: {  p = 0.15; break; }
            case TYPE_B: {  p = 0.15; break; }
            case TYPE_C: {  p = 0.20; break; }
            case TYPE_D: {  p = 0.25; break; }
            case TYPE_E: {  p = 0.40; break; }
            case TYPE_F: {  p = 0.60; break; }
        }

        //this.uH =

        //TODO: COMPLETE Standard Terrain Wind Speed AND city Terrain Wind Speed methods
    }
}
