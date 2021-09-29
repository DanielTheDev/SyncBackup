package io.github.danielthedev.syncbackup;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class Properties {

    private static final String FILENAME = "/properties.json";
    private static final Properties INSTANCE;

    private final JSONObject source;

    private Properties(JSONObject source) {
        this.source = source;
    }

    static {
        Properties instance = null;
        try(InputStream in = Properties.class.getResourceAsStream(FILENAME)) {
            instance = new Properties(new JSONObject(new String(in.readAllBytes(), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            INSTANCE = instance;
        }
    }

    public <T> T getValue(String path) {
        String[] innerpaths = path.split("\\.");

        JSONObject object = this.source;
        for(int x = 0; x < innerpaths.length-1; x++) {
            object = object.getJSONObject(innerpaths[x]);
        }
        return (T) object.get(innerpaths[innerpaths.length-1]);
    }

    public boolean fileExists() {
        return INSTANCE != null;
    }

    public static Properties getInstance() {
        return INSTANCE;
    }
}
