package hotspothealthcode.BL;

/**
 * Created by shaniel on 15/08/15.
 */
public class GaussianModel {

    private double C; // C = Time-integrated atmospheric concentration (Ci-s)/(m3).
    private double Q; // Q = Source term (Ci).
    private double H; // H = Effective release height (m).
    private double lambda; // lambda = = Radioactive decay constant (s –1).
    private double x; // x = Downwind distance (m).
    private double y; // y = Crosswind distance (m).
    private double z; // z = Vertical axis distance (m).
    private double sigmaY; // SigmaY = Standard deviation of the integrated concentration distribution in the crosswind direction (m).
    private double sigmaZ; // SigmaZ = Standard deviation of the integrated concentration distribution in the vertical direction (m).
    private double u; // u = Average wind speed at the effective release height (m/s).
    private double L; // L = Inversion layer height (m).
    private double DFx; // DF(x) = Plume Depletion factor


    // Stability type needed for calculations
    private static PasquillStability PasquillStability;

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
    public double calcInversionLayer(){
        // To avoid the sharp transition between the two above equations, the transition into the
        // Inversion layer equation begins when z equals 70% of L and is complete when z equals
        // L. Between these two values, the two equations are linearly interpolated.

        double retVal;

        retVal = this.Q / (Math.sqrt(2 * Math.PI) * this.sigmaY * this.L * this.u);

        retVal *= Math.exp(-0.5 * Math.pow(this.y / this.sigmaY, 2));
        retVal *= Math.exp((-this.lambda * this.x) / this.u) * this.DFx;

        return retVal;
    }

    public void calcStandardTerrainSigmaYZ(){

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

    public void calcCityTerrainSigmaYZ(){

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
}
