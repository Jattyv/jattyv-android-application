package de.jattyv.android.data;

import android.os.Environment;

import de.jattyv.jcapi.data.jfc.JattyvFileController;
import de.jattyv.jcapi.data.jfc.JattyvFileHandler;
import de.jattyv.jcapi.data.jfc.data.Settings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigFileHandler implements JattyvFileHandler {

    @Override
    public Settings readSettings(String configFile) {
        File path = new File(Environment.getExternalStorageDirectory(), "Jattyv");

        try {
            File config = new File(path, configFile);
            InputStream in = new FileInputStream(config);

            if (config.exists()) {
                return JattyvFileController.readSettings(in, this);
            } else {
                config.createNewFile();
                return JattyvFileController.readSettings(in, this);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConfigFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public void writeSettings(String dataname, Settings config){
        write(dataname, JattyvFileController.getSettingsAsString(config));
    }

    @Override
    public void write(String dataname, String content) {
        File path = new File(Environment.getExternalStorageDirectory(), "Jattyv");
        if(!path.exists()){
            path.mkdirs();
        }
        File file = new File(path, dataname);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream outputStreamWriter = null;

        try {
            outputStreamWriter = new FileOutputStream(file);
            outputStreamWriter.write(content.getBytes());
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String readFile(String path) {
        FileReader in = null;
        File config = new File(Environment.getExternalStorageDirectory().getPath()+path);
        if (config.exists()) {
            try {

                in = new FileReader(path);
                BufferedReader br = new BufferedReader(in);
                String line = "";
                String content = "";
                while ((line = br.readLine()) != null) {
                    content += line;
                }
                in.close();
                return content;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ConfigFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConfigFileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                config.createNewFile();
                return "";
            } catch (IOException ex) {
                Logger.getLogger(ConfigFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

}