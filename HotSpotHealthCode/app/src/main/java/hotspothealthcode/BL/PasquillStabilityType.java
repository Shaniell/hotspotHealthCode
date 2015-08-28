package hotspothealthcode.BL;

/**
 * Created by Giladl on 28/08/2015.
 */
public enum PasquillStabilityType
{
    TYPE_A ('A'),
    TYPE_B ('B'),
    TYPE_C ('C'),
    TYPE_D ('D'),
    TYPE_E ('E'),
    TYPE_F ('F');

    private final char type;

    private PasquillStabilityType(char type)
    {
        this.type = type;
    }
}
