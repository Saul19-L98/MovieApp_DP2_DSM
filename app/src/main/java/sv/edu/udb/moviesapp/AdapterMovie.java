package sv.edu.udb.moviesapp;

import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.StreamHandler;

public class AdapterMovie extends ArrayAdapter<Movie> {
    List<Movie> movies;
    private Activity context;

    String URL;

    public AdapterMovie(@NonNull Activity context, @NonNull List<Movie> movies){
        super(context, R.layout.movie_layout,movies);
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=null;

        if (view == null)
            rowview = layoutInflater.inflate(R.layout.movie_layout,null);
        else rowview = view;

        TextView tvTitle = rowview.findViewById(R.id.tvTitle);
        TextView tvDescription = rowview.findViewById(R.id.tvDescription);

        TextView tvPremierYear = rowview.findViewById(R.id.tvPremierYear);
        TextView tvScore = rowview.findViewById(R.id.tvScore);

        TextView tvUrl = rowview.findViewById(R.id.tvUrl);
        ImageView imgImagen = rowview.findViewById(R.id.imgImagen);

        tvTitle.setText("Título : "+movies.get(position).getTitle());
        tvDescription.setText("Descripción: " + movies.get(position).getDescription());
        tvPremierYear.setText("Año de estreno: "+movies.get(position).getPremierYear());
        tvScore.setText("Puntuación: "+movies.get(position).getScore());

        //tvUrl.setText(movies.get(position).getImagen());

        Picasso.get().load(String.valueOf(movies)).error(R.mipmap.ic_launcher_round).into(imgImagen);

        return rowview;
    }
}
