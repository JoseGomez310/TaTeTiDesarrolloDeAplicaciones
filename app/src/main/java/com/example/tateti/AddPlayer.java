package com.example.tateti;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPlayer extends AppCompatActivity {


    boolean jugarConCruces=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        EditText jugador=findViewById(R.id.jugador);
        Button botonJugar=findViewById(R.id.botonJugar);
        Button botonCruz=findViewById(R.id.botonCruz);
        Button botonCirculo=findViewById(R.id.botonCirculo);
        LinearLayout cruzLayout=findViewById(R.id.cruzLayout);
        LinearLayout circuloLayout=findViewById(R.id.circuloLayout);

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

                String getNombreJugador=jugador.getText().toString();

                if (getNombreJugador.isEmpty()){
                    Toast.makeText(AddPlayer.this,"Ingrese su nombre", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(AddPlayer.this, MainActivity.class);
                    intent.putExtra("jugadorNombre",getNombreJugador);
                    intent.putExtra("jugarConCruces",jugarConCruces);
                    startActivity(intent);
                }
            }
        });


    }
}