package hotspothealthcode.BL.AtmosphericConcentration.results;

import java.util.ArrayList;

/**
 * Created by Giladl on 08/02/2016.
 */
public enum ResultField
{
    MODEL_TYPE("modelType"),
    STABILITY_TYPE("stabilityType"),
    WIND_SPEED("windSpeed"),
    WIND_DIRECTION("windDirection"),
    METEOROLOGICAL_CONDITION("meteorologicalCondition"),
    LOCATION("location");

    private final String fieldName;

    private ResultField(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public static ArrayList<ResultField> getResultFields()
    {
        ArrayList<ResultField> lst = new ArrayList<ResultField>();

        lst.add(MODEL_TYPE);
        lst.add(STABILITY_TYPE);
        lst.add(WIND_SPEED);
        lst.add(WIND_DIRECTION);
        lst.add(METEOROLOGICAL_CONDITION);
        lst.add(LOCATION);

        return lst;
    }

    @Override
    public String toString()
    {
        return this.fieldName;
    }
}
