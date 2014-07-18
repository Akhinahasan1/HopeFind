package com.tugasbesar.hopefind;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.tugasbesar.hopefind.library.JSONParser;
import com.tugasbesar.hopefind.model.EntitasContent;
import com.tugasbesar.hopefind.model.EntitasKategori;
import com.tugasbesar.hopefind.model.ListViewAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

   /* public static String RANK = "rank";
    public static String COUNTRY = "country";
    public static String POPULATION = "population";
    public static String FLAG = "flag";*/
    public static String IDCONTENT = "idcontent";
    public static String TITLE = "title";
    public static String TYPE = "type";
    public static String NAMAKATEGORI = "namakategori";
    public static String NAMALOKASI = "namalokasi";
    public static String GAMBAR= "gambar";
    public static String DETAIL = "detail";
    public static String CREATE = "create";
    public static String USERNAME= "username";


    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<EntitasKategori> kategori;
    ArrayList<EntitasContent> content;
    ArrayList<String> kategorilist;
    ArrayList<String> contentlist;
    ArrayList<HashMap<String, String>> arraylist;
    Boolean isInternetPresent = false;

    // Connection detector class


    public HomeFragment() {
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        this.setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

//Cek koneksi
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            new DownloadJSON().execute();

        } else {
            // display error
            Toast.makeText(getActivity(), "tidak ada koneksi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle("Mohon Maaf");
            builder1.setMessage("Aplikasi ini Membutuhkan Internet");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }


    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.activity_main_actions, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );
        SearchView sv = new SearchView(getActivity().getActionBar().getThemedContext());
        sv.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        sv.setSubmitButtonEnabled(true);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }



    // DownloadJSON AsyncTask
    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Hope Find");
            // Set progressdialog message
            mProgressDialog.setMessage("Mengambil Data...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            kategori = new ArrayList<EntitasKategori>();
            content = new ArrayList<EntitasContent>();

            // Retrieve JSON Objects from the given URL address
            jsonobject = JSONParser
                    .getJSONfromURL("http://android.hasansanusi.web.id/contohContent.txt");

            try {
                // Locate the array name in JSON
                //jsonarray = jsonobject.getJSONArray("worldpopulation");
                jsonarray = jsonobject.getJSONArray("content");

                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();

                    jsonobject = jsonarray.getJSONObject(i);
                    // Retrive JSON Objects
                    map.put("idcontent", jsonobject.getString("idcontent"));
                    map.put("title", jsonobject.getString("title"));
                    map.put("type", jsonobject.getString("type"));
                    map.put("namakategori", jsonobject.getString("namakategori"));
                    map.put("namalokasi", jsonobject.getString("namalokasi"));
                    map.put("detail", jsonobject.getString("detail"));
                    map.put("create", jsonobject.getString("create"));
                    map.put("username", jsonobject.getString("username"));
                    map.put("gambar", jsonobject.getString("gambar"));
                    /*
                    map.put("rank", jsonobject.getString("rank"));
                    map.put("country", jsonobject.getString("country"));
                    map.put("population", jsonobject.getString("population"));
                    map.put("flag", jsonobject.getString("flag"));*/
                    // Set the JSON Objects into the array
                    arraylist.add(map);

                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            listview = (ListView) getView().findViewById(R.id.listView1);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(getActivity(), arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            listview.setTextFilterEnabled(true);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }


}
