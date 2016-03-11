package hotspothealthcode.BL.StorageAccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;

/**
 * Created by Giladl on 12/02/2016.
 */
public enum StorageAccessor
{
    INSTANCE;

    public OutputResult loadResult(File file)
    {
        FileReader reader = null;

        OutputResult result = null;

        try {
            reader = new FileReader(file);

            char[] buffer = new char[256];

            reader.read(buffer);

            result = OutputResult.instantiateFromJSON(new JSONObject(buffer.toString()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
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
}
