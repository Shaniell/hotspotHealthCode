package hotspothealthcode.BL.AtmosphericConcentration;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Giladl on 28/08/2015.
 */
public enum PasquillStabilityType
{
    EMPTY_VALUE ("Choose value"),
    TYPE_A ("A: Very unstable"),
    TYPE_B ("B: Moderately unstable"),
    TYPE_C ("C: Slightly unstable"),
    TYPE_D ("D: Neutral"),
    TYPE_E ("E: Slightly stable"),
    TYPE_F ("F: Moderately stable");

    private final String type;

    private PasquillStabilityType(String type)
    {
        this.type = type;
    }

    public static ArrayList<PasquillStabilityType> getStabilityTypes()
    {
        ArrayList<PasquillStabilityType> lst = new ArrayList<PasquillStabilityType>();

        lst.add(EMPTY_VALUE);
        lst.add(TYPE_A);
        lst.add(TYPE_B);
        lst.add(TYPE_C);
        lst.add(TYPE_D);
        lst.add(TYPE_E);
        lst.add(TYPE_F);

        return lst;
    }

    public static PasquillStabilityType getStabilityType(String stabilityType)
    {
        switch (stabilityType)
        {
            case "A: Very unstable":
            {
                return TYPE_A;
            }

            case "B: Moderately unstable":
            {
                return TYPE_B;
            }

            case "C: Slightly unstable":
            {
                return TYPE_C;
            }

            case "D: Neutral":
            {
                return TYPE_D;
            }

            case "E: Slightly stable":
            {
                return TYPE_E;
            }

            case "F: Moderately stable":
            {
                return TYPE_F;
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public String toString()
    {
        return this.type;
    }
}
