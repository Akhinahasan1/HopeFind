package com.tugasbesar.hopefind.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tugasbesar.hopefind.R;

public class SingleItemView extends Activity {
    // Declare Variables
    /*String rank;
    String country;
    String population;
    String flag;*/
    String idcontent,title,type,namakategori,namalokasi,detail,create,username,gambar;
    String position;
    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_content_view);

        Intent i = getIntent();
        idcontent = i.getStringExtra("idcontent");
        title = i.getStringExtra("title");
        type = i.getStringExtra("type");
        namakategori = i.getStringExtra("namakategori");
        namalokasi = i.getStringExtra("namalokasi");
        detail = i.getStringExtra("detail");
        create = i.getStringExtra("create");
        username = i.getStringExtra("username");
        gambar = i.getStringExtra("gambar");

        /*/ Get the result of rank
        rank = i.getStringExtra("rank");
        // Get the result of country
        country = i.getStringExtra("country");
        // Get the result of population
        population = i.getStringExtra("population");
        // Get the result of flag
        flag = i.getStringExtra("flag");*/

        // Locate the TextViews in singleitemview.xml
        TextView txtidcontent,txttitle,txttype,txtnamakategori,txtnamalokasi,txtdetail,txtcreate,txtusername;
         txtidcontent= (TextView) findViewById(R.id.itemId);
        txttitle= (TextView) findViewById(R.id.itemJudul);
        txtcreate= (TextView) findViewById(R.id.itemCreate);
        txttype= (TextView) findViewById(R.id.itemType);
        txtnamakategori= (TextView) findViewById(R.id.itemKategori);
        txtnamalokasi= (TextView) findViewById(R.id.itemLokasi);
        txtdetail= (TextView) findViewById(R.id.itemDetail);
        txtusername= (TextView) findViewById(R.id.itemUsername);

        /*
        TextView txtrank = (TextView) findViewById(R.id.rank);
        TextView txtcountry = (TextView) findViewById(R.id.country);
        TextView txtpopulation = (TextView) findViewById(R.id.population);

        // Locate the ImageView in singleitemview.xml
        ImageView imgflag = (ImageView) findViewById(R.id.flag);
        */
        ImageView imgGambar= (ImageView) findViewById(R.id.imageitem);

        // Set results to the TextViews
        txtidcontent.setText(idcontent);
        txttitle.setText(title);
        txttype.setText(type);
        txtnamakategori.setText(namakategori);
        txtnamalokasi.setText(namalokasi);
        txtdetail.setText(detail);
        txtcreate.setText(create);
        txtusername.setText(username);
        /*
        txtpopulation.setText(population);*/

        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(gambar, imgGambar);
    }
}