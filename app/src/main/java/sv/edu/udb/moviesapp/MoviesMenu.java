package sv.edu.udb.moviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesMenu extends AppCompatActivity {
    //////////////////////////////////////////////////////////////////////////////////////////
    //Realtime Database
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refMovies = database.getReference("movies");

    // Ordenamiento
    Query consultaOrdenada1 = refMovies.orderByChild("title");
    Query consultaOrdenada2 = refMovies.orderByChild("premierYear");
    Query consultaOrdenada3 = refMovies.orderByChild("score");

    List<Movie> movies;
    ListView moviesList;
    //////////////////////////////////////////////////////////////////////////////////////////

    TextView logout,tvYear,tvScore;
    ImageView imgImagen;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_menu);
        startUp();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movies = new ArrayList<>();
                // Cambiarlo refProductos a consultaOrdenada para ordenar lista
                consultaOrdenada2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Procedimiento que se ejecuta cuando hubo algun cambio
                        // en la base de datos
                        // Se actualiza la coleccion de personas
                        movies.removeAll(movies);
                        for (DataSnapshot dato : dataSnapshot.getChildren()) {
                            Movie movie = dato.getValue(Movie.class);
                            movie.setKey(dato.getKey());
                            movies.add(movie);
                        }

                        AdapterMovie adapter = new AdapterMovie(MoviesMenu.this,
                                movies);
                        moviesList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        tvScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movies = new ArrayList<>();
                // Cambiarlo refProductos a consultaOrdenada para ordenar lista
                consultaOrdenada3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Procedimiento que se ejecuta cuando hubo algun cambio
                        // en la base de datos
                        // Se actualiza la coleccion de personas
                        movies.removeAll(movies);
                        for (DataSnapshot dato : dataSnapshot.getChildren()) {
                            Movie movie = dato.getValue(Movie.class);
                            movie.setKey(dato.getKey());
                            movies.add(movie);
                        }

                        AdapterMovie adapter = new AdapterMovie(MoviesMenu.this,
                                movies);
                        moviesList.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MoviesMenu.this, MainActivity.class);
                startActivity(intent);
                signOut();
            }
        });

    }

    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(MoviesMenu.this,MainActivity.class));
            }
        });
    }


    public void startUp(){
        logout = findViewById(R.id.logout);
        tvYear = findViewById(R.id.tvYear);
        tvScore = findViewById(R.id.tvScore);
        imgImagen = findViewById(R.id.imgImagen);

        ////////////////////////////////////////////////////////////////////
        //  Realtime Database
        FloatingActionButton fab_agregar= findViewById(R.id.fab_agregar);
        moviesList = findViewById(R.id.moviesList);

        // Cuando el usuario haga clic en la lista (para editar registro)
        moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), AddMovie.class);

                intent.putExtra("accion","e"); // Editar
                intent.putExtra("key", movies.get(i).getKey());
                intent.putExtra("title",movies.get(i).getTitle());
                intent.putExtra("description",movies.get(i).getDescription());
                intent.putExtra("premierYear",movies.get(i).getPremierYear());
                intent.putExtra("score",movies.get(i).getScore());
                intent.putExtra("imagen",movies.get(i).getImagen());
                startActivity(intent);
            }
        });


        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        moviesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                AlertDialog.Builder ad = new AlertDialog.Builder(MoviesMenu.this);
                ad.setMessage("Está seguro de eliminar registro?")
                        .setTitle("Confirmación");

                ad.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MoviesMenu.refMovies
                                .child(movies.get(position).getKey()).removeValue();

                        Toast.makeText(MoviesMenu.this,
                                "Registro borrado!",Toast.LENGTH_SHORT).show();
                    }
                });
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MoviesMenu.this,
                                "Operación de borrado cancelada!",Toast.LENGTH_SHORT).show();
                    }
                });

                ad.show();
                return true;
            }
        });

        fab_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando el usuario quiere agregar un nuevo registro
                Intent i = new Intent(getBaseContext(), AddMovie.class);
                i.putExtra("accion","a"); // Agregar
                i.putExtra("key","");
                i.putExtra("title","");
                i.putExtra("description","");
                i.putExtra("premierYear","");
                i.putExtra("score","");
                i.putExtra("imagen","");
                startActivity(i);
            }
        });

        movies = new ArrayList<>();

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                movies.removeAll(movies);
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Movie movie = dato.getValue(Movie.class);
                    movie.setKey(dato.getKey());
                    movies.add(movie);
                }

                AdapterMovie adapter = new AdapterMovie(MoviesMenu.this,
                        movies );
                moviesList.setAdapter(adapter);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });
        //
        ////////////////////////////////////////////////////////////////////
    }
}