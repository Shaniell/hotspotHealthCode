package hotspothealthcode.BL.AtmosphericConcentration;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import hotspothealthcode.BL.AtmosphericConcentration.ConcentrationPoint;

/**
 * Created by Giladl on 09/01/2016.
 */
public class ConcentrationResult
{
    private double concentration;
    private int arrivalTime; // HH:MM
    private ConcentrationPoint point;

    public ConcentrationResult(ConcentrationPoint point,
                               double concentration,
                               int arrivalTime)
    {

        this.point = point;
        this.concentration = concentration;
        this.arrivalTime = arrivalTime;
    }

    public ConcentrationResult(JSONObject jsonObject) throws JSONException {

        this.point = new ConcentrationPoint(jsonObject.getJSONObject("point"));
        this.concentration = jsonObject.getDouble("concentration");
        this.arrivalTime = jsonObject.getInt("arrivalTime");
    }

    public ConcentrationPoint getPoint() {
        return this.point;
    }

    public double getConcentration() {
        return this.concentration;
    }

    public int getArrivalTime() {
        return this.arrivalTime;
    }

    public String getStringPoint() {
        return this.point.toString();
    }

    public String getStringConcentration() {
        NumberFormat formatter = new DecimalFormat("0.#E0");

        return formatter.format(this.concentration);
    }

    public String getStringArrivalTime() {

        int hours = this.arrivalTime / 3600;
        int minutes = (this.arrivalTime % 3600) / 60;

        String time = "";

        if (minutes == 0)
            time += "<";

        if (hours < 10)
            time += "0" + hours;
        else
            time += hours;

        time += ":";

        if (minutes < 10)
            time += "0" + minutes;
        else
            time += minutes;

        return time;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("point", this.point.toJSON());
        jsonObject.put("concentration", this.concentration);
        jsonObject.put("arrivalTime", this.arrivalTime);

        return jsonObject;
    }
}
