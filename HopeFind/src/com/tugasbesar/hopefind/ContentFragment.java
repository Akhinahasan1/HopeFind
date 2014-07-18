package com.tugasbesar.hopefind;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.tugasbesar.hopefind.library.JSONParser;
import com.tugasbesar.hopefind.model.EntitasKategori;
import com.tugasbesar.hopefind.model.EntitasLokasi;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ContentFragment extends Fragment implements View.OnClickListener {
    JSONObject jsonobject, jsonobject2;
    JSONArray jsonarray, jsonarray2;
    ProgressDialog mProgressDialog;
    ArrayList<String> kategorilist;
    ArrayList<EntitasKategori> kategori;
    ArrayList<String> lokasilist;
    ArrayList<EntitasLokasi> lokasi;
    Button bPublish,btngambar;
    //
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;

    private String upLoadServerUri = null;
    private String imagepath=null;
    private ImageView imageview;
    private TextView messageText;


    public ContentFragment() {
        // TODO Auto-generated constructor stub

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        this.setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        //

        upLoadServerUri = "http://hopefindid.com/android/UploadToServer.php";



        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            new DownloadJSON().execute();
        } else {
            // display error
            Toast.makeText(getActivity(), "tidak ada koneksi", Toast.LENGTH_SHORT).show();
            this.getActivity().finish();
        }
    }

    //


    //
    private void openAddPhoto() {

        String[] addPhoto=new String[]{ "Camera" , "Gallery" };
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setTitle("Pilih Gambar : ");

        dialog.setItems(addPhoto,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {

                if(id==0){
                    // Call camera Intent
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,1);
                }
                if(id==1){
                    //call gallery
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
                }
            }
        });

        dialog.setNeutralButton("cancel",new android.content.DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }});
        dialog.show();
    }
    //

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getData().getPath();

            Uri selectedImageUri = data.getData();
            imagepath =getPath(selectedImageUri);
            Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
            imageview.setImageBitmap(bitmap);
            //messageText.setText("Uploading file path:" +imagepath);
            Toast.makeText(getActivity(),imagepath,Toast.LENGTH_LONG).show();

        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //
    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"+imagepath);

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    //messageText.setText("Source File not exist :"+ imagepath);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                  //  +" http://android.hopefindid.com/uploads/";
                            //messageText.setText(msg);
                            Toast.makeText(getActivity(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(getActivity(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(getActivity(), "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
    //

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPublikasi:
                dialog = ProgressDialog.show(getActivity(), "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {

                        uploadFile(imagepath);

                    }
                }).start();

                break;
            case R.id.btngambar:
                openAddPhoto();
                break;
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        bPublish = (Button) rootView.findViewById(R.id.bPublikasi);
        btngambar= (Button) rootView.findViewById(R.id.btngambar);
        imageview = (ImageView)rootView.findViewById(R.id.idgmbr1);
        ImageView img= new ImageView(getActivity());
        btngambar.setOnClickListener(this);
        bPublish.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub

        inflater.inflate(R.menu.activity_main_actions, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

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
            // Locate the Entitas kategori Class
            kategori = new ArrayList<EntitasKategori>();
            lokasi = new ArrayList<EntitasLokasi>();
            // Create an array to kategori the spinner
            kategorilist = new ArrayList<String>();
            lokasilist = new ArrayList<String>();
            // JSON file URL address
            jsonobject = JSONParser
                    .getJSONfromURL("http://android.hasansanusi.web.id/api_kategori.txt");
            jsonobject2 = JSONParser.getJSONfromURL("http://android.hasansanusi.web.id/api_lokasi.txt");

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
            Spinner mySpinner = (Spinner) getView().findViewById(R.id.idSpinnerKategori);
            Spinner spinnerLokasi = (Spinner) getView().findViewById(R.id.idSpinner3);

            // Spinner adapter
            mySpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategorilist));
            spinnerLokasi.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lokasilist));

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


}
