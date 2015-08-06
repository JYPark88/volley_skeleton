package com.coupang.nolan.volley_skeleton.network;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherRequest extends BaseRequest{
    public static final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily";
    static final String QUERY_PARAM = "q";
    static final String FORMAT_PARAM = "mode";
    static final String UNITS_PARAM = "units";
    static final String DAYS_PARAM = "cnt";

    private Context mContext;
    private Response.Listener<String> mListener;

    public WeatherRequest(Context context, String url ,Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(context, url, Method.GET, errorListener);
        mContext = context;
        mListener = listener;
    }

    // Method가 get일 경우
    public static String getUrlParams(String city, String unit, int count) {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, city)
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(UNITS_PARAM, unit)
                .appendQueryParameter(DAYS_PARAM, Integer.toString(count))
                .build();

        return builtUri.toString();
    }

    @Override
    protected void deliverResponse(JSONObject response) {

        try {
            if(response.getString("cod").equals("404")){
                getErrorListener().onErrorResponse(null);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListener.onResponse(response.toString());
    }


}
