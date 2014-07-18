package com.tugasbesar.hopefind;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.tugasbesar.hopefind.library.MemberFunctions;

/**
 * Created by Akhinahasan on 09/07/2014.
 */
public class SplashActivity extends Activity {
    private static final int TIME = 4 * 1000;// 4 seconds
    MemberFunctions userFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //Cek koneksi
        ConnectivityManager connMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            userFunctions = new MemberFunctions();
            if (userFunctions.isUserLoggedIn(getApplicationContext())) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        Intent login = new Intent(getApplicationContext(), MainActivityMember.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                        finish();

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                }, TIME);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, TIME);

            } else {
                // user is not logged in show login screen

                // Closing dashboard screen
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        Intent login = new Intent(getApplicationContext(), MainActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                        finish();

                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                }, TIME);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, TIME);

            }

        } else {
            // display error
            Toast.makeText(this, "tidak ada koneksi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Mohon Maaf");
            builder1.setMessage("Aplikasi ini Membutuhkan Internet");
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
        //

        //

    }


    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
