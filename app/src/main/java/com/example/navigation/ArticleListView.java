package com.example.navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.util.Objects;


public class ArticleListView extends ArrayAdapter<String> {
    private String[] libelle;
    private String[] prix;
    private String[] type;
    private String[] imagepath;
    private Fragment context;
    private Bitmap bitmap;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArticleListView(Fragment context , String[] libelle, String[] prix, String[] imagepath , String[]  type){
    super(Objects.requireNonNull(context.getActivity()), R.layout.fragment_men , libelle );

    this.context = context;
    this.libelle = libelle;
    this.prix = prix;
    this.imagepath = imagepath;
    this.type = type;

}
@NonNull
    @Override
    public View getView(int pos , @Nullable View convertView , @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.fragment_men , null , true);
            viewHolder= new ViewHolder(r);
            r.setTag(viewHolder);

        }
        else
        {
            viewHolder=(ViewHolder)r.getTag();
        }
            viewHolder.tvw1.setText(libelle[pos]);
            viewHolder.tvw2.setText(prix[pos] + " Dt");
            new GetImageFromURL(viewHolder.ivw).execute(imagepath[pos]);
            viewHolder.tvw3.setText(type[pos] );



        return r;
}

class ViewHolder{

        TextView tvw1 ;
        TextView tvw2 ;

         ImageView ivw ;
         TextView tvw3 ;

        ViewHolder(View v) {
            tvw1 = (TextView) v.findViewById(R.id.tvLibelle);
            tvw2 = (TextView) v.findViewById(R.id.tvprix);
            ivw = (ImageView) v.findViewById(R.id.imageView);
            tvw3 = (TextView) v.findViewById(R.id.tvtype);


        }
}

public class GetImageFromURL extends AsyncTask<String,Void,Bitmap>
{
    ImageView imgView;
    public GetImageFromURL(ImageView imgv){
        this.imgView=imgv;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
       String urldisplay =url[0];
       bitmap = null;

       try {
           InputStream ist = new java.net.URL(urldisplay).openStream();
           bitmap = BitmapFactory.decodeStream(ist);


       } catch (Exception e) {
           e.printStackTrace();
       }
        return bitmap;
    }


    @Override
    protected  void  onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        imgView.setImageBitmap(bitmap);

    }
}
}
