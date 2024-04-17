package com.example.tateti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tateti.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private final List<int[]> combinaciones=new ArrayList<>(); //Lista de combinaciones ganadoras
    private int[] boxPositions={0,0,0,0,0,0,0,0,0}; //Tablero 0(nadie) 1(jugador) 2(maquina)
    private int turno=1; //turno del jugador 1(jugador) 2(maquina)
    private int boxesSeleccionadas=1; //casillas seleccionadas

    String ganador="empate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        combinaciones.add(new int[] {0,1,2});
        combinaciones.add(new int[] {3,4,5});
        combinaciones.add(new int[] {6,7,8});
        combinaciones.add(new int[] {0,3,6});
        combinaciones.add(new int[] {1,4,7});
        combinaciones.add(new int[] {2,5,8});
        combinaciones.add(new int[] {2,4,6});
        combinaciones.add(new int[] {0,4,8});



        String getNombreJugador=getIntent().getStringExtra("jugadorNombre");


        binding.jugadorNombre.setText(getNombreJugador); //Se recupero el nombre y se coloca en el lugar

        boolean jugarConCruces=getIntent().getBooleanExtra("jugarConCruces",true); //recupera jugarConCruces de la pantalla anterior


        //pendiente cambiar x o O del box de arriba jugador/maquina
        if (jugarConCruces==false){
            binding.seleccionJugador.setImageResource(R.drawable.circulo);
            binding.seleccionMaquina.setImageResource(R.drawable.cruz);
        }

        binding.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(0)){
                    performanceAction((ImageView) view,0);
                }
            }
        });

        binding.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(1)){
                    performanceAction((ImageView) view,1);
                }
            }
        });

        binding.image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(2)){
                    performanceAction((ImageView) view,2);
                }
            }
        });

        binding.image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(3)){
                    performanceAction((ImageView) view,3);
                }
            }
        });

        binding.image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(4)){
                    performanceAction((ImageView) view,4);
                }
            }
        });

        binding.image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(5)){
                    performanceAction((ImageView) view,5);
                }
            }
        });

        binding.image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(6)){
                    performanceAction((ImageView) view,6);
                }
            }
        });

        binding.image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(7)){
                    performanceAction((ImageView) view,7);
                }
            }
        });

        binding.image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBoxSeleccionable(8)){
                    performanceAction((ImageView) view,8);
                }
            }
        });

    }

    private void performanceAction(ImageView imageView, int selectedBoxPosition) {
        boolean jugarConCruces=getIntent().getBooleanExtra("jugarConCruces",true); //recupera jugarConCruces de la pantalla anterior
        boxPositions[selectedBoxPosition] = turno;
        if (turno == 1) {
            if (jugarConCruces == true) {
                imageView.setImageResource(R.drawable.cruz);
            } else {
                imageView.setImageResource(R.drawable.circulo);
            }
            if (chequearResultados()) {
                ganador="jugador";
                Intent intent = new Intent(MainActivity.this, ResultDialog.class);
                intent.putExtra("ganador",ganador);
                String getNombreJugador=getIntent().getStringExtra("jugadorNombre");
                intent.putExtra("nombreJugador",getNombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                startActivity(intent);
                //Ganó el jugador
                //pendiente


            } else if (boxesSeleccionadas == 9) {
                Intent intent = new Intent(MainActivity.this, ResultDialog.class);
                intent.putExtra("ganador",ganador);
                String getNombreJugador=getIntent().getStringExtra("jugadorNombre");
                intent.putExtra("nombreJugador",getNombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                startActivity(intent);
                //juego empatado
            } else {
                cambiarTurno(2);
                boxesSeleccionadas++;
                juegaMaquina();
            }
        } else {
            if (jugarConCruces == true) {
                imageView.setImageResource(R.drawable.circulo);
            } else {
                imageView.setImageResource(R.drawable.cruz);
            }
            if (chequearResultados()) {
                ganador="maquina";
                Intent intent = new Intent(MainActivity.this, ResultDialog.class);
                intent.putExtra("ganador",ganador);
                String getNombreJugador=getIntent().getStringExtra("jugadorNombre");
                intent.putExtra("nombreJugador",getNombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                startActivity(intent);
                //ganó la maquina
                //pendiente

            } else if (boxesSeleccionadas == 9) {
                Intent intent = new Intent(MainActivity.this, ResultDialog.class);
                String getNombreJugador=getIntent().getStringExtra("jugadorNombre");
                intent.putExtra("nombreJugador",getNombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                startActivity(intent);
                //juego empatado
            } else {
                cambiarTurno(1);
                boxesSeleccionadas++;
            }
        }
    }

    private void cambiarTurno(int turnoActual){  //cambia el turn0
        turno=turnoActual;
        if (turno==1){
            binding.jugadorLayout.setBackgroundResource(R.drawable.black_border);
            binding.maquinaLayout.setBackgroundResource(R.drawable.white_box);
        }else{
            binding.jugadorLayout.setBackgroundResource(R.drawable.white_box);
            binding.maquinaLayout.setBackgroundResource(R.drawable.black_border);
        }
    }

    private boolean chequearResultados(){  //Comprueba si hay un ganador
        boolean response=false;
        for (int i=0; i<combinaciones.size(); i++){
            final int[] combinacion=combinaciones.get(i);

            if (boxPositions[combinacion[0]]==turno && boxPositions[combinacion[1]]==turno && boxPositions[combinacion[2]]==turno){
                response=true;
            }
        }
        return response;
    }

    public boolean isBoxSeleccionable(int boxPosicion){  //Comprueba si la casilla es seleccionable
        boolean response=false;
        if (boxPositions[boxPosicion]==0){
            response=true;
        }
        return response;
    }

    public void reiniciarJuego(){  //Reinicia el juego
        boxPositions=new int[] {0,0,0,0,0,0,0,0,0};
        turno=1;
        boxesSeleccionadas=1;

        binding.image1.setImageResource(R.drawable.white_box);
        binding.image2.setImageResource(R.drawable.white_box);
        binding.image3.setImageResource(R.drawable.white_box);
        binding.image4.setImageResource(R.drawable.white_box);
        binding.image5.setImageResource(R.drawable.white_box);
        binding.image6.setImageResource(R.drawable.white_box);
        binding.image7.setImageResource(R.drawable.white_box);
        binding.image8.setImageResource(R.drawable.white_box);
        binding.image9.setImageResource(R.drawable.white_box);

    }

    public void juegaMaquina(){
        int minimo = 1; // Valor mínimo
        int maximo = 9; // Valor máximo
        int boxRandom = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
        while (isBoxSeleccionable(boxRandom-1)==false){
            boxRandom = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
        }
        if (boxRandom==1){
            ImageView view=binding.image1;
            if (isBoxSeleccionable(0)){
                performanceAction(view,0);
            }
        }
        if (boxRandom==2){
            ImageView view=binding.image2;
            if (isBoxSeleccionable(1)){
                performanceAction(view,1);
            }
        }
        if (boxRandom==3){
            ImageView view=binding.image3;
            if (isBoxSeleccionable(2)){
                performanceAction(view,2);
            }
        }
        if (boxRandom==4){
            ImageView view=binding.image4;
            if (isBoxSeleccionable(3)){
                performanceAction(view,3);
            }
        }
        if (boxRandom==5){
            ImageView view=binding.image5;
            if (isBoxSeleccionable(4)){
                performanceAction(view,4);
            }
        }
        if (boxRandom==6){
            ImageView view=binding.image6;
            if (isBoxSeleccionable(5)){
                performanceAction(view,5);
            }
        }
        if (boxRandom==7){
            ImageView view=binding.image7;
            if (isBoxSeleccionable(6)){
                performanceAction(view,6);
            }
        }
        if (boxRandom==8){
            ImageView view=binding.image8;
            if (isBoxSeleccionable(7)){
                performanceAction(view,7);
            }
        }
        if (boxRandom==9){
            ImageView view=binding.image9;
            if (isBoxSeleccionable(8)){
                performanceAction(view,8);
            }
        }

    }
}