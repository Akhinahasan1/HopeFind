package com.tugasbesar.hopefind;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tugasbesar.hopefind.library.DatabaseHandler;
import com.tugasbesar.hopefind.library.MemberFunctions;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        this.setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            // Importing all assets like buttons, text fields


        } else {
            // display error
            Toast.makeText(getActivity(), "Tidak ada koneksi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle("Mohon Maaf");
            builder1.setMessage("Aplikasi ini Membutuhkan Internet");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) rootView.findViewById(R.id.btnLinkToRegisterScreen);
        btnLogin.setOnClickListener(this);
        btnLinkToRegister.setOnClickListener(this);
        return rootView;
    }


    public void login(){
        inputEmail = (EditText) getActivity().findViewById(R.id.loginEmail);
        inputPassword = (EditText) getActivity().findViewById(R.id.loginPassword);
        loginErrorMsg = (TextView) getActivity().findViewById(R.id.login_error);

        // Login button Click Event
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        MemberFunctions userFunction = new MemberFunctions();
        Log.d("Button", "Login");
        JSONObject json = userFunction.loginUser(email, password);


        // check for login response
        try {
            if (json.getString(KEY_SUCCESS) != null) {
                loginErrorMsg.setText("");
                String res = json.getString(KEY_SUCCESS);
                if (Integer.parseInt(res) == 1) {
                    // user successfully logged in
                    // Store user details in SQLite Database
                    DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
                    JSONObject json_user = json.getJSONObject("user");

                    // Clear all previous data in database
                    userFunction.logoutUser(getActivity().getApplicationContext());
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(getActivity().getApplicationContext(), MainActivityMember.class);

                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);

                    // Close Login Screen
                    getActivity().finish();
                } else {
                    // Error in login
                    loginErrorMsg.setText("Incorrect username/password");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());

        if (networkInfo != null && networkInfo.isConnected()) {
        //
        switch (v.getId()) {
            case R.id.btnLogin:
                login();

                break;
            case R.id.btnLinkToRegisterScreen:
                Intent dashboard = new Intent(getActivity().getApplicationContext(), RegisterActivity.class);

                // Close all views before launching Dashboard
                dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashboard);

                break;
        }
        //

            // fetch data

            // Link to Register Screen
        } else {
            // display error
            Toast.makeText(getActivity(), "Tidak ada koneksi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle("Mohon Maaf");
            builder1.setMessage("Aplikasi ini Membutuhkan Internet");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }

    }
}
