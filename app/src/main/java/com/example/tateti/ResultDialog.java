package com.example.tateti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class ResultDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_dialog);
        boolean jugarConCruces=getIntent().getBooleanExtra("jugarConCruces",true);

        TextView mensajeResultado=findViewById(R.id.messageText);
        TextView mensajeResultadoAbajo=findViewById(R.id.messageTextAbajo);
        Button reiniciar=findViewById(R.id.botonJugarOtraVez);
        Button inicio=findViewById(R.id.botonInicio);
        String resultadoGanador=getIntent().getStringExtra("ganador");
        String nombreJugador=getIntent().getStringExtra("nombreJugador");

        if (Objects.equals(resultadoGanador, "jugador")){
            mensajeResultado.setText(nombreJugador);
        } else if (Objects.equals(resultadoGanador, "maquina")) {
            mensajeResultado.setText("Maquina");
            mensajeResultadoAbajo.setText("es la ganadora");
        }else{
            mensajeResultado.setText("El resultado");
            mensajeResultadoAbajo.setText("salio empate");
        }

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultDialog.this, MainActivity.class);
                intent.putExtra("jugadorNombre",nombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                startActivity(intent);
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultDialog.this, AddPlayer.class);
                startActivity(intent);
            }
        });

    }
}