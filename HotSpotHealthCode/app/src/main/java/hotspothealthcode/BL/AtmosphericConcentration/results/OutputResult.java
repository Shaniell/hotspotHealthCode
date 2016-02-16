package hotspothealthcode.BL.AtmosphericConcentration.results;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Created by Giladl on 08/02/2016.
 */
public class OutputResult
{
    private static OutputResult INSTANCE;

    private HashMap<ResultField, Object> values;
    private ArrayList<ConcentrationResult> results;

    private OutputResult()
    {
        this.values = new HashMap<>();
        this.results = new ArrayList<>();
    }

    public static OutputResult getInstance()
    {
        if (OutputResult.INSTANCE == null)
            OutputResult.INSTANCE = new OutputResult();

        return OutputResult.INSTANCE;
    }

    public static OutputResult newInstance()
    {
        OutputResult.INSTANCE = new OutputResult();

        return OutputResult.INSTANCE;
    }

    public static OutputResult instantiateFromJSON(JSONObject jsonObject) throws JSONException
    {
        OutputResult.INSTANCE = new OutputResult();

        JSONArray array = jsonObject.getJSONArray("results");

        // Convert JSON array to results array
        for (int i = 0; i < array.length(); i++)
        {
            ConcentrationResult concentrationResult = new ConcentrationResult(array.getJSONObject(i));

            OutputResult.INSTANCE.results.add(concentrationResult);
        }

        // Convert fields to hash map
        for (ResultField resultField: ResultField.getResultFields())
        {
            Object obj;

            switch (resultField)
            {
                case LOCATION:
                {
                    obj = (LatLng)jsonObject.get(resultField.toString());

                    break;
                }
                case MODEL_TYPE:
                {
                    obj = (ModelType)jsonObject.get(resultField.toString());

                    break;
                }
                default:
                {
                    obj = (Number)jsonObject.get(resultField.toString());
                }
            }
            OutputResult.INSTANCE.addValue(resultField, obj);
        }

        return OutputResult.INSTANCE;
    }

    public void setResults(ArrayList<ConcentrationResult> results)
    {
        this.results = results;
    }

    public ArrayList<ConcentrationResult> getResults() {
        return this.results;
    }

    public void addValue(ResultField fieldName, Object value)
    {
        this.values.put(fieldName, value);
    }

    public Object getValue(ResultField fieldName)
    {
        return this.values.get(fieldName);
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject = new JSONObject();

        // Convert values to JSON object
        for (Entry<ResultField, Object> entry : this.values.entrySet())
        {
            jsonObject.put(entry.getKey().toString(), entry.getValue());
        }

        JSONArray array = new JSONArray();

        // Convert results to json array
        for (ConcentrationResult result: this.results)
        {
            array.put(result.toJSON());
        }

        jsonObject.put("results", array);

        return jsonObject;
    }
}
