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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.malick.apk.Adapter.Adapter_Atm;
import com.example.malick.apk.Adapter.Adapter_Bank;
import com.example.malick.apk.App.AppController;
import com.example.malick.apk.UI.Atm;
import com.example.malick.apk.UI.Bank;
import com.example.malick.apk.UI.Spbu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityBank extends AppCompatActivity implements LocationListener, SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener, OnMapReadyCallback {

    SwipeRefreshLayout swipe;
    ListView list;
    Adapter_Bank adapter;
    List<Bank> itemList = new ArrayList<>();
    Double latitude, longitude;
    Criteria criteria;
    Location location;
    LocationManager locationManager;
    String provider;
    ProgressDialog pDialog;


    ///Map
    private GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
    CameraPosition cameraPosition;
    LatLng center, latLng;
    String title;


    private static final String url = "https://gomaps.000webhostapp.com/json/j_bank.php?lat=";
    private String url2 = "https://gomaps.000webhostapp.com/json/marker_bank.php";
    public static final String url_cari = "https://gomaps.000webhostapp.com/json/cari_bank.php?lat=-6.894796&lng=110.638413";
    private static final String TAG = ActivityAtm.class.getSimpleName();



    String tag_json_obj = "json_obj_req";


    public static final String TAG_ID = "kode_bank";
    public static final String TAG_NAMA = "nama_bank";
    public static final String TAG_ALAMAT = "alamat";
    public static final String TAG_CABANG = "cabang";
    public static final String TAG_TELP= "telp";
    public static final String TAG_WEB= "web";
    public static final String TAG_JARAK= "jarak";
    public static final String TAG_LAT= "lat";
    public static final String TAG_LNG = "lng";

    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Menampilkan toolbar search



        // menyamakan variabel pada layout dan java

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);

        // mengisi data dari adapter ke listview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_Bank(this,itemList);
        recyclerView.setAdapter(adapter);




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
                                Bank m = new Bank();

                                m.setKode(obj.getString("kode_bank"));
                                m.setCabang(obj.getString("cabang"));
                                m.setNama(obj.getString("nama_bank"));
                                m.setTelp(obj.getString("telp"));
                                m.setAlamat(obj.getString("alamat"));
                                m.setWeb(obj.getString("web"));
                                m.setLat(obj.getString("lat"));
                                m.setIng(obj.getString("lng"));
                                m.setGambar(obj.getString("gambar"));
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
        pDialog = new ProgressDialog(ActivityBank.this);
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

                            Bank data = new Bank();

                            data.setKode(obj.getString(TAG_ID));
                            data.setNama(obj.getString(TAG_NAMA));
                            data.setGambar(obj.getString(TAG_GAMBAR));
                            data.setAlamat(obj.getString(TAG_ALAMAT));
                            data.setCabang(obj.getString(TAG_CABANG));
                            data.setTelp(obj.getString(TAG_TELP));
                            data.setWeb(obj.getString(TAG_WEB));
                            data.setLat(obj.getString(TAG_LAT));
                            data.setIng(obj.getString(TAG_LNG));

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

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //gMap = googleMap;

        // Mengarahkan ke
        center = new LatLng(-0.94924, 100.35427);
        cameraPosition = new CameraPosition.Builder().target(center).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        getMarkers();


    }

    private void addMarker(LatLng latlng, final String title) {
        markerOptions.title(title);
        markerOptions.position(latlng);
        if(title.equals("BNI"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bni1));
        else if (title.equals("BRI"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bri));
        else if (title.equals("Bank Danamon"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_d));
        else if (title.equals("Bank Mandiri"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mandiri));
        else if (title.equals("Bank Permata"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_permata));
        else if (title.equals("BCA"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bca));
        else if (title.equals("Bank Panin"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_panin));
        else if (title.equals("Bank CIMB Niaga"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_cimb));
        else if (title.equals("Bank OCBC NISP"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ocb));
        else if (title.equals("Bank Muamalat"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mum));
        else if (title.equals("Bank Mestika Darma"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mustika));
        else if (title.equals("Bank Sinarmas"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mas));
        else if (title.equals("BTN"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_btn));
        else if (title.equals("BTPN"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_btpn));
        else if (title.equals("BRI Syariah"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_s));
        else if (title.equals("Bank Mega"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_mega));
        else if (title.equals("BNI Syariah"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bs));
        else if (title.equals("Bank Bukopin"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_bkp));
        else if (title.equals("Bank Syariah Mandiri"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ms));
        else if (title.equals("Bank Kesejahteraan Ekonomi"))
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ksk));
        else
            mMap.addMarker(markerOptions).setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_nagari));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    // Fungsi get JSON marker
    private void getMarkers() {
        StringRequest strReq = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("json_bank");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TAG_NAMA);
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(TAG_LAT)), Double.parseDouble(jsonObject.getString(TAG_LNG)));

                        // Menambah data marker untuk di tampilkan ke google map
                        addMarker(latLng, title);


                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(ActivityBank.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);


    }









}


