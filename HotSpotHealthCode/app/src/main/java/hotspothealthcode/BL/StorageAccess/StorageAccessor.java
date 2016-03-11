package hotspothealthcode.BL.StorageAccess;

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

import hotspothealthcode.BL.AtmosphericConcentration.results.OutputResult;

/**
 * Created by Giladl on 12/02/2016.
 */
public enum StorageAccessor
{
    INSTANCE;

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
}
