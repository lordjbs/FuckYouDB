package xyz.lordjbs.fuckyoudb;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class FuckYouDB {

    private JSONObject json;
    private String jsonFile;

    public FuckYouDB(String jsonfile) {
        jsonFile = jsonfile;
        JSONObject firstRead = readIntoJSON(jsonfile);
        if(firstRead == null) json = new JSONObject().put("error", true); else json = firstRead;
    }

    public Object get(String key) {
        reload();
        return json.get(key);
    }

    public boolean put(String key, Object value) {
        reload();
        try {
            json.put(key, value);
            write();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map getMap() {
        return json.toMap();
    }

    private void reload() {
        json = readIntoJSON(jsonFile);
    }

    private void write() {
        try {
            BufferedWriter outputWriter;
            outputWriter = new BufferedWriter(new FileWriter(jsonFile));
            outputWriter.write(json.toString() + "");
            outputWriter.newLine();

            outputWriter.flush();
            outputWriter.close();

            reload();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject readIntoJSON(String file) {
        try {
            return new JSONObject(FileUtils.readFileToString(new java.io.File(file), "UTF-8"));
        } catch (Exception e) {
            return null;
        }
    }
}
