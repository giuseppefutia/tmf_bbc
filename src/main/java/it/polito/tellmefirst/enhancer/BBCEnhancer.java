package it.polito.tellmefirst.enhancer;

/*
* TellMeFirst - A Knowledge Discovery Application
*
* Copyright (C) 2015 Giuseppe Futia
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Affero General Public License as
* published by the Free Software Foundation, either version 3 of the
* License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Affero General Public License for more details.
*
* You should have received a copy of the GNU Affero General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BBCEnhancer {

    static Log LOG = LogFactory.getLog(BBCEnhancer.class);

    final static String BBC_API = "http://juicer.api.bbci.co.uk/articles?facets[]=";

    public BBCEnhancer() {

    }

    public String getPropValues() throws IOException {
        Properties prop = new Properties();
        String propFileName = "api.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        String apiKey = "";
        if (inputStream != null) {
            prop.load(inputStream);
            apiKey = prop.getProperty("API_KEY");
        } else {
            throw new FileNotFoundException();
        }
        inputStream.close();
        return apiKey;
    }

    public String createURL(String URI, String API_KEY) {
        String result = "";
        try {
            result = BBC_API + URLEncoder.encode(URI, "UTF-8") + "&api_key=" + API_KEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getResultFromAPI(String urlStr, String type) {

        LOG.debug("[getResultFromAPI] - BEGIN");
        String result = "";
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", type);
            if (conn.getResponseCode() != 200) {
                System.out.println(conn.getResponseMessage());
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            result = sb.toString();
        }catch (Exception e){
            LOG.error("[getResultFromAPI] - EXCEPTION: ", e);
        }
        LOG.debug("[getResultFromAPI] - END");
        return result;
    }

    public static void main(String[] args) throws IOException {
        BBCEnhancer bbcEnhancer = new BBCEnhancer();
        String API_KEY = bbcEnhancer.getPropValues();
        String URL = bbcEnhancer.createURL("http://dbpedia.org/resource/Barack_Obama", API_KEY);
        String result = bbcEnhancer.getResultFromAPI(URL, "application/json");
        LOG.info(result);
    }
}
