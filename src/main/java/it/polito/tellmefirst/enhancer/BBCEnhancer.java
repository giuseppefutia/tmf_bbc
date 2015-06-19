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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BBCEnhancer {

    static Log LOG = LogFactory.getLog(BBCEnhancer.class);

    final static String BBC_API = "http://data.test.bbc.co.uk/bbcrd-juicer/articles?facets[]=";
    final static String API_KEY = "9OHbOpZpVh9tQZBDjwTlTmsCF2Ce0yGQ";
    final static String LIMIT = "10";

    public BBCEnhancer(){

    }

    public String createURL(String URI) {
        String result = "";
        try {
            result = BBC_API + URLEncoder.encode(URI, "UTF-8")  + "&limit=" + LIMIT + "&apikey=" + API_KEY;
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

    public static void main(String[] args){
        BBCEnhancer bbcEnhancer = new BBCEnhancer();
        String URL = bbcEnhancer.createURL("http://dbpedia.org/resource/Barack_Obama");
        String result = bbcEnhancer.getResultFromAPI(URL, "application/json");
    }
}
