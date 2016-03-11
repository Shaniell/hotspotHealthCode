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

    public static ModelType getModelType(String modelType){
        switch (modelType)
        {
            case "General Plume":
            {
                return GENERAL_PLUME;
            }
            case "General Fire":
            {
                return GENERAL_FIRE;
            }
            case "General Explosion":
            {
                return GENERAL_EXPLOSION;
            }
            default:{
                return null;
            }
        }
    }
}
