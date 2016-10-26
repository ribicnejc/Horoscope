package com.ribicnejc.horoscopeapp.API;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HorAPI {
    private String date;
    private String horoscope;
    private String sunsign;
    private boolean success = false;
    private boolean apiFail  = true;

    public String getSunsign() {
        return sunsign;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public String getDate() {
        return date;
    }

    public boolean isApiFail() {
        return apiFail;
    }

    public boolean isSuccess() {
        return success;
    }

    private class HoroscopeTask extends AsyncTask<Void, Void, String>{
        String sign;
        String apiKey = "http://horoscope-api.herokuapp.com/horoscope/today/";
        HoroscopeTask (String sign){
            this.sign = sign;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;
            String query;
            try{
                query = apiKey + sign;
                queryReturn = sendQuery(query);
                ParseJSON(queryReturn);
            }catch (Exception e){
                success = false;
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            success = true;
        }

        private String sendQuery(String query) throws IOException{
            String result = "";
            URL searchURL = new URL(query);
            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
                String line;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
            }
            return result;
        }

        private void ParseJSON(String json){
            try{
                JSONObject jsonObject = new JSONObject(json);
                date = jsonHelperGetString(jsonObject, "date");
                horoscope = jsonHelperGetString(jsonObject, "horoscope");
                sunsign = jsonHelperGetString(jsonObject, "sunsign");
            }catch (Exception e){
                apiFail = true;
            }
        }

        private String jsonHelperGetString(JSONObject obj, String k){
            String v = null;
            try{
                v = obj.getString(k);
            }catch (JSONException e){
                Log.d("JSON ERROR", "jsonHelperGetString error");
            }
            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
            JSONObject o = null;
            try{
                o = obj.getJSONObject(k);
            }catch (JSONException e){
                Log.d("JSON ERROR", "jsonHeplerGetJSONObject");
            } return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
            JSONArray a = null;
            try{
                a = obj.getJSONArray(k);
            }catch (JSONException e){
                Log.d("JSON ERROR", "jsonHelperGetJsonArray");
            }return  a;
        }
    }
}
