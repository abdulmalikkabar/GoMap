package com.example.malick.apk;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.example.malick.apk.Adapter.Adapter_Spbu;
import com.example.malick.apk.App.AppController;

import com.example.malick.apk.UI.Spbu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySpbu extends AppCompatActivity implements LocationListener, SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {

    SwipeRefreshLayout swipe;
    ListView list;
    Adapter_Spbu adapter;
    List<Spbu> itemList = new ArrayList<>();
    Double latitude, longitude;
    Criteria criteria;
    Location location;
    LocationManager locationManager;
    String provider;
    ProgressDialog pDialog;

    //https://fasilitasumumpariwisata.000webhostapp.com/Json/json_masjid.php?lat=
    // ip android 192.168.43.8  // ip android 192.168.43.8  192.168.43.8
    // sesuaikan ip laptop/PC atau menggunakan ip emulator bawaan android studio 10.0.2.2 //192.168.43.8
    private static final String url = "https://gomaps.000webhostapp.com/json/j_spbu.php?lat=";
    public static final String url_cari = "https://gomaps.000webhostapp.com/json/cari_spbu.php?lat=-6.894796&lng=110.638413";
    // private static final String url = "http://10.0.2.2/MapMedis/json/json_hospital.php?lat=";
    private static final String TAG = ActivitySpbu.class.getSimpleName();


    String tag_json_obj = "json_obj_req";


    public static final String TAG_ID = "kode_spbu";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_JENIS = "jenis";
    public static final String TAG_NAMA = "nama_spbu";
    public static final String TAG_LAT= "lat";
    public static final String TAG_LNG = "lng";
    public static final String TAG_JARAK= "jarak";
    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbu);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Menampilkan toolbar search


        // menyamakan variabel pada layout dan java
        list = (ListView) findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        // mengisi data dari adapter ke listview
        adapter = new Adapter_Spbu(this, itemList);
        list.setAdapter(adapter);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, false);


        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           lokasi();
                       }
                   }
        );


    }


    private void lokasi() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(provider);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // permintaan update lokasi device dalam waktu 10 detik
        locationManager.requestLocationUpdates(provider, 10000, 1, this);

        if(location!=null){
            onLocationChanged(location);
            callListVolley(latitude, longitude);
        }else{
            Toast.makeText(getBaseContext(), "Lokasi device pengguna tidak ditemukan.\nMohon hidupkan GPS.",
                    Toast.LENGTH_LONG).show();
           /* latitude longitude Alun-alun Demak sebagai default jika tidak ditemukan lokasi dari device pengguna */
            callListVolley(-1.323537, 100.561348);
        }
    }

    private void callListVolley(Double lat, Double lng) {


        itemList.clear();
        adapter.notifyDataSetChanged();

        swipe.setRefreshing(true);

        JsonArrayRequest jArr = new JsonArrayRequest(url + lat +"&lng="+ lng,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Spbu m = new Spbu();

                                m.setNama_spbu(obj.getString("nama_spbu"));
                                m.setAlamat(obj.getString("alamat"));
                                m.setKode_spbu(obj.getString("kode_spbu"));
                                m.setJenis(obj.getString("jenis"));
                                m.setGambar(obj.getString("gambar"));
                                m.setLat(obj.getString("lat"));
                                m.setLng(obj.getString("lng"));

                                double jarak = Double.parseDouble(obj.getString("jarak"));

                                m.setJarak(""+round(jarak, 2));

                                itemList.add(m);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // memberitahu adapter jika ada perubahan data
                        adapter.notifyDataSetChanged();

                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        // menambah permintaan ke queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        // untuk melihat latitude longitude posisi device pengguna pada logcat ditemukan atau tidak
        Log.d(TAG, " "+ latitude +", "+longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRefresh() {
        lokasi();
    }

    @Override
    public void onBackPressed(){
        finish();
        System.exit(0);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.type_name));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void cariData(final String keyword) {
        pDialog = new ProgressDialog(ActivitySpbu.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.dismiss();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        itemList.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            Spbu data = new Spbu();

                            data.setKode_spbu(obj.getString(TAG_ID));
                            data.setNama_spbu(obj.getString(TAG_NAMA));
                            data.setGambar(obj.getString(TAG_GAMBAR));
                            data.setAlamat(obj.getString(TAG_ALAMAT));
                            data.setJenis(obj.getString(TAG_JENIS));
                            data.setLat(obj.getString(TAG_LAT));
                            data.setLng(obj.getString(TAG_LNG));


                            double jarak = Double.parseDouble(obj.getString(TAG_JARAK));



                            data.setJarak(""+round(jarak, 2)+" M/");



                            itemList.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
