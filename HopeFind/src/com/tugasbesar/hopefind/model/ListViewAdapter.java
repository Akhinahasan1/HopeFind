package com.tugasbesar.hopefind.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.tugasbesar.hopefind.HomeFragment;
import com.tugasbesar.hopefind.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        /*
        TextView rank;
        TextView country;
        TextView population;
        ImageView flag;
        */
        TextView idcontent,title,type,namakategori,namalokasi,detail,create,username;
        ImageView gambar;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.content_costume_listview, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        title = (TextView) itemView.findViewById(R.id.idjudul);
        type = (TextView) itemView.findViewById(R.id.idtype);
        create = (TextView) itemView.findViewById(R.id.idbuat);
        username=(TextView) itemView.findViewById(R.id.idmember);

        // Locate the ImageView in listview_item.xml
        gambar = (ImageView) itemView.findViewById(R.id.idgambar);

        // Capture position and set results to the TextViews
        title.setText(resultp.get(HomeFragment.TITLE));
        type.setText(resultp.get(HomeFragment.TYPE));
        create.setText(resultp.get(HomeFragment.CREATE));
        username.setText(resultp.get(HomeFragment.USERNAME));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(HomeFragment.GAMBAR), gambar);
        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, SingleItemView.class);
                intent.putExtra("title", resultp.get(HomeFragment.TITLE));
                intent.putExtra("type", resultp.get(HomeFragment.TYPE));
                intent.putExtra("create", resultp.get(HomeFragment.CREATE));
                intent.putExtra("username", resultp.get(HomeFragment.USERNAME));
                intent.putExtra("gambar", resultp.get(HomeFragment.GAMBAR));
                intent.putExtra("idcontent", resultp.get(HomeFragment.IDCONTENT));
                intent.putExtra("namalokasi", resultp.get(HomeFragment.NAMALOKASI));
                intent.putExtra("detail", resultp.get(HomeFragment.DETAIL));
                intent.putExtra("namakategori", resultp.get(HomeFragment.NAMAKATEGORI));



                /*/ Pass all data rank
                intent.putExtra("rank", resultp.get(HomeFragment.RANK));
                // Pass all data country
                intent.putExtra("country", resultp.get(HomeFragment.COUNTRY));
                // Pass all data population
                intent.putExtra("population", resultp.get(HomeFragment.POPULATION));
                // Pass all data flag
                intent.putExtra("flag", resultp.get(HomeFragment.FLAG));*/
                // Start SingleItemView Class
                context.startActivity(intent);

            }
        });
        return itemView;
    }
}
