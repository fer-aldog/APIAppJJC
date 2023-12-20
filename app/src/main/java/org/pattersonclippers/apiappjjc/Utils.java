package org.pattersonclippers.apiappjjc;

import android.util.Log;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import java.io.IOException;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Utils {

    public static final String LOG_TAG = "AAAAAAAAAAAAAAAAAAAAAAAAA";

    public static double extractFeatureFromJson(String ratesJSON, String currencyTo) {
        double newRate = 0.00;
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(ratesJSON)) {
            return 0.00;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(ratesJSON);

            String amount = baseJsonResponse.getString("amount");
            String date = baseJsonResponse.getString("date");
            JSONObject rates = baseJsonResponse.getJSONObject("rates");
            String currRate = rates.getString(currencyTo);
            newRate = Double.parseDouble(currRate);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the rates JSON results", e);
        }
        return newRate;
    }
}
