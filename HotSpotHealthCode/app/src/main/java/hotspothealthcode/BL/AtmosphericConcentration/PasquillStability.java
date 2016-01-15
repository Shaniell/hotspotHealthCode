package hotspothealthcode.BL.AtmosphericConcentration;

/**
 * Created by shaniel on 15/08/15.
 */
public class PasquillStability {

    private double windSpeed;
    private MeteorologicalConditions MC;
    public PasquillStabilityType stabilityType;

    public PasquillStability(PasquillStabilityType stabilityType)
    {
        this.stabilityType = stabilityType;
    }

    public PasquillStability(double windSpeed, MeteorologicalConditions condition){

        this.windSpeed = windSpeed;
        this.MC = condition;

        if (windSpeed < 2)
        {
            switch (condition){
                case SUN_HIGH_IN_SKY: {
                    this.stabilityType = PasquillStabilityType.TYPE_A;

                    break;
                }

                case SUN_LOW_IN_SKY_OR_CLOUDY: {
                    this.stabilityType = PasquillStabilityType.TYPE_B;

                    break;
                }

                case NIGHT_TIME: {
                    this.stabilityType = PasquillStabilityType.TYPE_F;

                    break;
                }
            }
        }


        if ((windSpeed >= 2)&&(windSpeed < 3))
        {
            switch (condition){
                case SUN_HIGH_IN_SKY: {
                    this.stabilityType = PasquillStabilityType.TYPE_A;

                    break;
                }

                case SUN_LOW_IN_SKY_OR_CLOUDY: {
                    this.stabilityType = PasquillStabilityType.TYPE_C;

                    break;
                }

                case NIGHT_TIME: {
                    this.stabilityType = PasquillStabilityType.TYPE_E;

                    break;
                }
            }
        }

        if ((windSpeed >= 3)&&(windSpeed < 4))
        {
            switch (condition){
                case SUN_HIGH_IN_SKY:{
                    this.stabilityType = PasquillStabilityType.TYPE_B;

                    break;
                }

                case SUN_LOW_IN_SKY_OR_CLOUDY:{
                    this.stabilityType = PasquillStabilityType.TYPE_C;

                    break;
                }

                case NIGHT_TIME:{
                    this.stabilityType = PasquillStabilityType.TYPE_D;

                    break;
                }
            }
        }

        if (((windSpeed >= 4)&&(windSpeed <= 6)) || (windSpeed > 6))
        {
            switch (condition){
                case SUN_HIGH_IN_SKY: {
                    this.stabilityType = PasquillStabilityType.TYPE_C;

                    break;
                }

                case SUN_LOW_IN_SKY_OR_CLOUDY: { }

                case NIGHT_TIME: {
                    this.stabilityType = PasquillStabilityType.TYPE_D;

                    break;
                }
            }
        }
    }
}
