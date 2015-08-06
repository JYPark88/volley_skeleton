package com.coupang.nolan.volley_skeleton.network;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestPostRequest extends BaseRequest {
    private static final String BASE_URL = "http://httpbin.org/post";

    private Context mContext;
    private Response.Listener<String> mListener;
    private Response.ErrorListener mErrorListener;

    private String mQuery;
    private String mUnit;
    private String mCount;

    public TestPostRequest(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(context, BASE_URL, Method.POST, errorListener);
        mContext = context;
        mListener = listener;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("q", mQuery);
        params.put("mode", "json");
        params.put("units", mUnit);
        params.put("cnt", mCount);
        return params;
    }

    public void setParameters(String query, String unit, int count) {
        mQuery = query;
        mUnit = unit;
        mCount = Integer.toString(count);
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response.toString());
    }


}
