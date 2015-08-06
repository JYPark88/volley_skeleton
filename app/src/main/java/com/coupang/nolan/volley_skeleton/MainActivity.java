package com.coupang.nolan.volley_skeleton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.coupang.nolan.volley_skeleton.network.TestPostRequest;
import com.coupang.nolan.volley_skeleton.network.WeatherRequest;
import com.coupang.nolan.volley_skeleton.utils.VolleyHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Volley_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_get_success)
    public void on200SuccessBtnClick(){
       urlWeatherRequestStart(WeatherRequest.getUrlParams("Seoul", "metric", 1));
    }

    @OnClick(R.id.button_get_fail)
    public void on404ErrorBtnClick(){
        urlWeatherRequestStart(WeatherRequest.FORECAST_BASE_URL);
    }

    @OnClick(R.id.button_post)
    public void onPostBtnClick(){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
                Log.i(TAG, response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "에러발생", Toast.LENGTH_SHORT).show();
//                Log.i(TAG, error.toString());
            }
        };

        TestPostRequest testPostRequest = new TestPostRequest(this, listener, errorListener);
        testPostRequest.setParameters("Seoul", "metric", 1);
        VolleyHelper.getRequestQueue().add(testPostRequest);
    }

    private void urlWeatherRequestStart(String requestUrl){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
                Log.i(TAG, response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "에러발생", Toast.LENGTH_SHORT).show();
//                Log.i(TAG, error.toString());
            }
        };

        WeatherRequest weatherRequest = new WeatherRequest(this,
                requestUrl,
                listener,
                errorListener);
        VolleyHelper.getRequestQueue().add(weatherRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyHelper.getRequestQueue().cancelAll(this);
    }
}
