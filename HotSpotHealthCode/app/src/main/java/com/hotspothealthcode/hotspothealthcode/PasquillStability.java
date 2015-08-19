package com.hotspothealthcode.hotspothealthcode;

/**
 * Created by shaniel on 15/08/15.
 */
public class PasquillStability {

    private int wind;
    private MeteorologicalConditions MC;
    public char StabilityType;

    public PasquillStability(int wind, MeteorologicalConditions condition){

        this.wind = wind;
        this.MC = condition;

        if (wind < 2)
        {
            switch (condition){
                case SunHighInSky:{ StabilityType = 'A'; break;}
                case SunLowInSkyOrCloudy:{ StabilityType = 'B'; break;}
                case NightTime:{StabilityType = 'F'; break;}
            }
        }


        if ((wind >= 2)&&(wind < 3))
        {
            switch (condition){
                case SunHighInSky:{ StabilityType = 'A'; break;}
                case SunLowInSkyOrCloudy:{ StabilityType = 'C'; break;}
                case NightTime:{StabilityType = 'E'; break;}
            }
        }

        if ((wind >= 3)&&(wind < 4))
        {
            switch (condition){
                case SunHighInSky:{ StabilityType = 'B'; break;}
                case SunLowInSkyOrCloudy:{ StabilityType = 'C'; break;}
                case NightTime:{StabilityType = 'D'; break;}
            }
        }

        if ((wind >= 4)&&(wind <= 5))
        {
            switch (condition){
                case SunHighInSky:{ StabilityType = 'C'; break;}
                case SunLowInSkyOrCloudy:{ StabilityType = 'D'; break;}
                case NightTime:{StabilityType = 'D'; break;}
            }
        }

        if (wind < 6)
        {
            switch (condition){
                case SunHighInSky:{ StabilityType = 'C'; break;}
                case SunLowInSkyOrCloudy:{ StabilityType = 'D'; break;}
                case NightTime:{StabilityType = 'D'; break;}
            }
        }
    }
}
