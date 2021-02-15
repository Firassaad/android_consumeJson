package com.example.navigation;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MenFragment extends Fragment {

    String urladdress = "https://boutique0.000webhostapp.com/pages/CRUD/WEB_services/Articles.php";
    String[] libelle;
    String[] prix;
    String[] imagepath;
    String[] type;
    ListView listView;
    BufferedInputStream is;
    String line = null;
    String result = null;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        collectData();
        ArticleListView articleListView = new ArticleListView(MenFragment.this, libelle,prix,imagepath ,type);
        listView.setAdapter(articleListView);
        return inflater.inflate(R.layout.fragment_men , container , false);
    }

    private void collectData(){
        System.out.println("womeeeeeeeeen---------------------------------------------------------------------");
        //Connection
        try {
            URL url = new URL(urladdress);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());


        }catch (Exception ex){
            ex.printStackTrace();
        }


//content
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");

            }
            is.close();
            result=sb.toString();

        }catch (Exception ex){
            ex.printStackTrace();
        }


//JSON
        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo =null;
            libelle = new String[ja.length()];
            prix = new String[ja.length()];
            imagepath = new String[ja.length()];
            type = new String[ja.length()];

            for (int i = 0; i <= ja.length(); i++) {
                jo = ja.getJSONObject(i);

                libelle[i] = jo.getString("libelle");
                prix[i] = jo.getString("prix");
                imagepath[i] = jo.getString("image");
                type[i] = jo.getString("type");



            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
