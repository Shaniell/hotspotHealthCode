package hotspothealthcode.BL.AtmosphericConcentration;

import java.util.ArrayList;

/**
 * Created by Giladl on 27/10/2015.
 */
public enum TerrainType {
    STANDARD_TERRAIN("Standard Terrain"),
    CITY_TERRAIN("City Terrain");

    private String type;

    private TerrainType (String type)
    {
        this.type = type;
    }

    public static ArrayList<TerrainType> getTerrainTypes()
    {
        ArrayList<TerrainType> lst = new ArrayList<>();

        lst.add(STANDARD_TERRAIN);
        lst.add(CITY_TERRAIN);

        return lst;
    }

    @Override
    public String toString()
    {
        return this.type;
    }

}
