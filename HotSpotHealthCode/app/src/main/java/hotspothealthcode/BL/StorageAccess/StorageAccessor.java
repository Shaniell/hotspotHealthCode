package hotspothealthcode.BL.StorageAccess;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;

/**
 * Created by Giladl on 12/02/2016.
 */
public enum StorageAccessor
{
    INSTANCE;

    private static final String PREFS_NAME = "CalculationPrefFile";

    public OutputResult loadResult(File file)
    {
        BufferedReader bufferedReader = null;
        OutputResult result = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String line;
            StringBuilder text = new StringBuilder();

            // While there are lines to read, read them
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }

            result = OutputResult.instantiateFromJSON(new JSONObject(text.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public void saveResult(File file, OutputResult result)
    {
        FileWriter writer = null;

        try {
            writer = new FileWriter(file);

            writer.write(result.toJSON().toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean saveValueToSharedPreferences(Context context, String key, String value)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(key, value);

        return editor.commit();
    }

    public boolean saveValuesToSharedPreferences(Context context, String key, ArrayList<String> values)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Set<String> set = new HashSet<>();

        for (String value: values) {
            set.add(value);
        }

        editor.putStringSet(key, set);

        return editor.commit();
    }

    public String getValueFromSharedPreferences(Context context, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(key, "");
    }

    public ArrayList<String> getValuesFromSharedPreferences(Context context, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

        Set<String> set = settings.getStringSet(key, new HashSet<String>());

        return new ArrayList<>(set);
    }
}
