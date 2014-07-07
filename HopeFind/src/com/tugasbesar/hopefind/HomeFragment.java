package com.tugasbesar.hopefind;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.ListView;
import android.widget.SearchView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tugasbesar.hopefind.library.JSONfunctions;
import com.tugasbesar.hopefind.model.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    public static String RANK = "rank";
    public static String COUNTRY = "country";
    public static String POPULATION = "population";
    public static String FLAG = "flag";



    public HomeFragment() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.setHasOptionsMenu(true);
		super.onCreate(savedInstanceState);
        new DownloadJSON().execute();
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
				// Retrieve JSON Objects from the given URL address
				jsonobject = JSONfunctions
						.getJSONfromURL("http://www.androidbegin.com/tutorial/jsonparsetutorial.txt");

				try {
					// Locate the array name in JSON
					jsonarray = jsonobject.getJSONArray("worldpopulation");

					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						// Retrive JSON Objects
						map.put("rank", jsonobject.getString("rank"));
						map.put("country", jsonobject.getString("country"));
						map.put("population", jsonobject.getString("population"));
						map.put("flag", jsonobject.getString("flag"));
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
				// Close the progressdialog
				mProgressDialog.dismiss();
			}
		}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout .fragment_home,container ,false);
		return rootView ;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.activity_main_actions, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
	    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		super.onCreateOptionsMenu(menu, inflater);
	}
	

}
