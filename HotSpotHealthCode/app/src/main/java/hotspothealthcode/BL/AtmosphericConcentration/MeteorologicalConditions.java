package hotspothealthcode.BL.AtmosphericConcentration;

import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Created by shaniel on 15/08/15.
 */
public enum MeteorologicalConditions {
    EMPTY_VALUE ("Choose value"),
    SUN_HIGH_IN_SKY("Sun high"),
    SUN_LOW_IN_SKY_OR_CLOUDY("Sun low or cloudy"),
    NIGHT_TIME("Night time");

    private final String condition;

    private MeteorologicalConditions(String condition)
    {
        this.condition = condition;
    }

    public static ArrayList<MeteorologicalConditions> getMeteorologicalConditions()
    {
        ArrayList<MeteorologicalConditions> lst = new ArrayList<MeteorologicalConditions>();

        lst.add(EMPTY_VALUE);
        lst.add(SUN_HIGH_IN_SKY);
        lst.add(SUN_LOW_IN_SKY_OR_CLOUDY);
        lst.add(NIGHT_TIME);

        return lst;
    }

    @Override
    public String toString() {
        return this.condition;
    }
}
