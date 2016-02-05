package hotspothealthcode.BL.AtmosphericConcentration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Giladl on 09/01/2016.
 */
public class ConcentrationPoint
{
    private double x;
    private double y;
    private double z;

    public ConcentrationPoint(double x, double y, double z) {
        this.z = z;
        this.x = x;
        this.y = y;
    }

    public ConcentrationPoint(JSONObject jsonObject) throws JSONException {
        this.z = jsonObject.getDouble("z");
        this.x = jsonObject.getDouble("x");
        this.y = jsonObject.getDouble("y");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString()
    {
        return "(" + (this.x / 1000) + ", " + (this.y / 1000) + ", " + this.z + ")";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("x", this.x);
        jsonObject.put("y", this.y);
        jsonObject.put("z", this.z);

        return jsonObject;
    }
}
