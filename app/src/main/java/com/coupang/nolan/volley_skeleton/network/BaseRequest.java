package com.coupang.nolan.volley_skeleton.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.coupang.nolan.volley_skeleton.BaseApplication;
import com.coupang.nolan.volley_skeleton.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class BaseRequest extends Request<JSONObject>{
    private Response.Listener<JSONObject> mListener;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public BaseRequest(Context context, String url, int method, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(Constants.VOLLEY_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mSharedPreferences = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mContext = context;

    }

    public BaseRequest(Context context, String url, int method,Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(context, url, method, errorListener);
        mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>(5);
//        headers.put("X-HW-USERID", mSharedPreferences.getString(Constants.PREF_USER_ID, ""));
//        headers.put("X-HW-DOMAIN", mSharedPreferences.getString(Constants.PREF_DOMAIN, ""));
        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "utf-8");
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }


}
