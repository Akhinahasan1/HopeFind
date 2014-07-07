package com.tugasbesar.hopefind;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import com.tugasbesar.hopefind.library.JSONfunctions;
import com.tugasbesar.hopefind.model.EntitasKategori;
import com.tugasbesar.hopefind.model.EntitasLokasi;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContentFragment extends Fragment {
    JSONObject jsonobject,jsonobject2;
    JSONArray jsonarray,jsonarray2;
    ProgressDialog mProgressDialog;
    ArrayList<String> kategorilist;
    ArrayList<EntitasKategori> kategori;
    ArrayList<String> lokasilist;
    ArrayList<EntitasLokasi> lokasi;

	public ContentFragment() {
		// TODO Auto-generated constructor stub
		
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        new DownloadJSON().execute();
		this.setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
	}

    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Pilih Kategori");
            // Set progressdialog message
            mProgressDialog.setMessage("Mengambil Data...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            kategori = new ArrayList<EntitasKategori>();
            // Create an array to populate the spinner
            kategorilist = new ArrayList<String>();
            lokasi = new ArrayList<EntitasLokasi>();
            lokasilist=new ArrayList<String>();
            // Create an array to populate the spinner
            kategorilist = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONfunctions
                    .getJSONfromURL("http://android.hasansanusi.web.id/api_kategori.txt");
            jsonobject2=JSONfunctions.getJSONfromURL("http://android.hasansanusi.web.id/api_lokasi.txt");

            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("kategori");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    EntitasKategori kateg = new EntitasKategori();

                    kateg.setIdkategori(jsonobject.optInt("idkategori"));
                    kateg.setNama_kategori(jsonobject.optString("namakategori"));
                    kategori.add(kateg);

                    // Populate spinner with country names
                    kategorilist.add(jsonobject.optString("namakategori"));

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            try {
                // Locate the NodeList name
                jsonarray2 = jsonobject2.getJSONArray("lokasi");
                for (int i = 0; i < jsonarray2.length(); i++) {
                    jsonobject2 = jsonarray2.getJSONObject(i);

                    EntitasLokasi lok = new EntitasLokasi();

                    lok.setIdlokasi(jsonobject2.optInt("idlokasi"));
                    lok.setNamalokasi(jsonobject2.optString("namalokasi"));
                    lokasi.add(lok);

                    // Populate spinner with country names
                    lokasilist.add(jsonobject2.optString("namalokasi"));

                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the spinner in activity_main.xml
            Spinner mySpinner = (Spinner)getView().findViewById(R.id.idSpinnerKategori);
            Spinner spinnerLokasi=(Spinner)getView().findViewById(R.id.idSpinner3);

            // Spinner adapter
           mySpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,kategorilist));
            spinnerLokasi.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,lokasilist));

            // Spinner on item click listener
            spinnerLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // Locate the textviews in activity_main.xml

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

           mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });

            mProgressDialog.dismiss();

        }
    }





	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView= inflater.inflate(R.layout.fragment_content,container,false);
		return rootView;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		
		inflater.inflate(R.menu.activity_main_actions, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

	}



	

}
