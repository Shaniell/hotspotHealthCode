package hotspothealthcode.BL.AtmosphericConcentration.results;

/**
 * Created by Giladl on 15/02/2016.
 */
public enum ModelType
{
    GENERAL_PLUME("General Plume"),
    GENERAL_FIRE("General Fire"),
    GENERAL_EXPLOSION("General Explosion");

    private String type;

    private ModelType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return this.type;
    }
}
