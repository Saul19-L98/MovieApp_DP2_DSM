package sv.edu.udb.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddMovie extends AppCompatActivity {

    EditText edtTitle, edtDescription, edtPremierYear, edtScore, edtImagen;
    String key="",title="",description="",premierYear="",score="",accion="", imagen="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        inicializar();

    }

    private void inicializar() {
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);

        edtPremierYear = findViewById(R.id.edtPremierYear);
        edtScore = findViewById(R.id.edtScore);

        edtImagen=findViewById(R.id.edtImagen);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        key = datos.getString("key");
        title = datos.getString("title");
        description = datos.getString("description");

        premierYear = datos.getString("premierYear");
        score = datos.getString("score");

        imagen = datos.getString("imagen");

        accion=datos.getString("accion");
        edtTitle.setText(title);
        edtDescription.setText(description);
        edtPremierYear.setText(premierYear);
        edtScore.setText(score);
    }

    public void save(View v){
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        String premierYear = edtPremierYear.getText().toString();
        String score = edtScore.getText().toString();
        String imagen = edtImagen.getText().toString();

        // Se forma objeto persona
        Movie movie = new Movie(title,description,premierYear,score);

        if (accion.equals("a")) { //Agregar usando push()
            MoviesMenu.refMovies.push().setValue(movie);
        }
        else // Editar usando setValue
        {
            MoviesMenu.refMovies.child(key).setValue(movie);
        }
        finish();
    }
    public void cancel(View v){
        finish();
    }
}