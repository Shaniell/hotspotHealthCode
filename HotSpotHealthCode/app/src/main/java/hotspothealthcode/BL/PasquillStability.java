package hotspothealthcode.BL;

/**
 * Created by shaniel on 15/08/15.
 */
public class PasquillStability {

    private double wind;
    private MeteorologicalConditions MC;
    public PasquillStabilityType stabilityType;

    public PasquillStability(double wind, MeteorologicalConditions condition){

        this.wind = wind;
        this.MC = condition;

        if (wind < 2)
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


        if ((wind >= 2)&&(wind < 3))
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

        if ((wind >= 3)&&(wind < 4))
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

        if (((wind >= 4)&&(wind <= 5)) || (wind < 6))
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
