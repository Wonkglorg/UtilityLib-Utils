package com.wonkglorg.utilitylib.stuff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IP {
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Get the public ip of the server using the checkip.amazonaws.com service
     *
     * @return the public ip of the server
     */
    private String getPublicIP() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String ip = in.readLine();
            in.close();
            return ip;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }
}
