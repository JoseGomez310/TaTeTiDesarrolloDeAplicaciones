package com.example.tateti;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPlayer extends AppCompatActivity {


    boolean jugarConCruces=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //fuerza tema claro

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_player);

        EditText jugador=findViewById(R.id.jugador);
        Button botonJugar=findViewById(R.id.botonJugar);
        Button botonCruz=findViewById(R.id.botonCruz);
        Button botonCirculo=findViewById(R.id.botonCirculo);
        LinearLayout cruzLayout=findViewById(R.id.cruzLayout);
        LinearLayout circuloLayout=findViewById(R.id.circuloLayout);
        RadioGroup radioGroupDificultad = findViewById(R.id.radioGroupDificultad);
        String dificultad = "MEDIA"; // valor por defecto

        int selectedId = radioGroupDificultad.getCheckedRadioButtonId();

        if (selectedId == R.id.radioFacil) {
            dificultad = "FACIL";
        } else if (selectedId == R.id.radioMedia) {
            dificultad = "MEDIA";
        } else if (selectedId == R.id.radioDificil) {
            dificultad = "DIFICIL";
        }

        botonCruz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugarConCruces=true;
                cruzLayout.setBackgroundResource(R.drawable.black_border);
                circuloLayout.setBackgroundResource(R.drawable.verde_claro_border);

            }
        });

        botonCirculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugarConCruces=false;
                cruzLayout.setBackgroundResource(R.drawable.verde_claro_border);
                circuloLayout.setBackgroundResource(R.drawable.black_border);
            }
        });

        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getNombreJugador = jugador.getText().toString();

                int selectedId = radioGroupDificultad.getCheckedRadioButtonId();
                String dificultad = "MEDIA"; // valor por defecto

                if (selectedId == R.id.radioFacil) {
                    dificultad = "FACIL";
                } else if (selectedId == R.id.radioMedia) {
                    dificultad = "MEDIA";
                } else if (selectedId == R.id.radioDificil) {
                    dificultad = "DIFICIL";
                }

                if (getNombreJugador.isEmpty()) {
                    Toast.makeText(AddPlayer.this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddPlayer.this, MainActivity.class);
                    intent.putExtra("jugadorNombre", getNombreJugador);
                    intent.putExtra("jugarConCruces", jugarConCruces);
                    intent.putExtra("dificultad", dificultad);
                    startActivity(intent);
                }
            }
        });


    }
}