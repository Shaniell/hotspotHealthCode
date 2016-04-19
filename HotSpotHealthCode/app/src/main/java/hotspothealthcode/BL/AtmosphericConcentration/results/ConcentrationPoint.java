package hotspothealthcode.BL.AtmosphericConcentration.results;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Giladl on 09/01/2016.
 */
public class ConcentrationPoint
{
    private static double EARTH_RADIUS = 6371.0 * 1000;

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

    public LatLng toLatLng(LatLng startPosition, double windDirection)
    {
        double direction;

        if (x < 0)
        {
            direction = windDirection + 180;
        }
        else
        {
            direction = windDirection;
        }

        double radWindDirection = Math.toRadians(direction);
        double radStartLat = Math.toRadians(startPosition.latitude);
        double radStartLng = Math.toRadians(startPosition.longitude);


        // Calculate point from start point to down wind distance
        double downWindLat = Math.asin((Math.sin(radStartLat) * Math.cos(Math.abs(this.x) / ConcentrationPoint.EARTH_RADIUS)) +
                                       (Math.cos(radStartLat) * Math.sin(Math.abs(this.x) / ConcentrationPoint.EARTH_RADIUS) * Math.cos(radWindDirection)));

        double downWindLng = radStartLng + Math.atan2(Math.sin(radWindDirection) *
                                                      Math.sin(Math.abs(this.x) / ConcentrationPoint.EARTH_RADIUS) *
                                                      Math.cos(radStartLat),
                                                      Math.cos(Math.abs(this.x) / ConcentrationPoint.EARTH_RADIUS) -
                                                      (Math.sin(radStartLat) * Math.sin(downWindLat)));

        LatLng point;

        if (this.y == 0)
            point = new LatLng(Math.toDegrees(downWindLat), Math.toDegrees(downWindLng));
        // If there is a cross wind direction, calculate new lat lng
        else
        {
            direction = windDirection;

            if (this.y > 0)
            {
                direction -= 90;
            }
            else
            {
                direction += 90;
            }

            direction = Math.toRadians(direction);

            // Calculate point from down wind distance point to cross wind distance
            double crossWindLat = Math.asin((Math.sin(downWindLat) * Math.cos(Math.abs(this.y) / ConcentrationPoint.EARTH_RADIUS)) +
                                            (Math.cos(downWindLat) * Math.sin(Math.abs(this.y) / ConcentrationPoint.EARTH_RADIUS) * Math.cos(direction)));

            double crossWindLng = downWindLng + Math.atan2(Math.sin(direction) *
                                                                       Math.sin(Math.abs(this.y) / ConcentrationPoint.EARTH_RADIUS) *
                                                                       Math.cos(downWindLat),
                                                                       Math.cos(Math.abs(this.y) / ConcentrationPoint.EARTH_RADIUS) -
                                                                       (Math.sin(downWindLat) * Math.sin(crossWindLat)));

            point = new LatLng(Math.toDegrees(crossWindLat), Math.toDegrees(crossWindLng));
        }

        return point;
    }
}
