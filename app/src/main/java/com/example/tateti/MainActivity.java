package com.example.tateti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    private enum Dificultad { FACIL, MEDIA, DIFICIL }
    private Dificultad dificultadSeleccionada = Dificultad.DIFICIL; // Puedes setear esto desde el Intent o un Spinner

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
        String dificultadString = getIntent().getStringExtra("dificultad");
        if (dificultadString != null) {
            switch (dificultadString) {
                case "FACIL": dificultadSeleccionada = Dificultad.FACIL; break;
                case "MEDIA": dificultadSeleccionada = Dificultad.MEDIA; break;
                case "DIFICIL": dificultadSeleccionada = Dificultad.DIFICIL; break;
            }
        }
        else{
            dificultadSeleccionada=Dificultad.FACIL;
        }


        binding.jugadorNombre.setText(getNombreJugador); //Se recupero el nombre y se coloca en el lugar
        Toast.makeText(this, "Dificultad: " + dificultadSeleccionada.name(), Toast.LENGTH_SHORT).show();

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
                String dificultadString = getIntent().getStringExtra("dificultad");
                intent.putExtra("dificultad",dificultadString);
                startActivity(intent);
                //Ganó el jugador
                //pendiente


            } else if (boxesSeleccionadas == 9) {
                Intent intent = new Intent(MainActivity.this, ResultDialog.class);
                intent.putExtra("ganador",ganador);
                String getNombreJugador=getIntent().getStringExtra("jugadorNombre");
                intent.putExtra("nombreJugador",getNombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                String dificultadString = getIntent().getStringExtra("dificultad");
                intent.putExtra("dificultad",dificultadString);
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
                String dificultadString = getIntent().getStringExtra("dificultad");
                intent.putExtra("dificultad",dificultadString);
                startActivity(intent);
                //ganó la maquina
                //pendiente

            } else if (boxesSeleccionadas == 9) {
                Intent intent = new Intent(MainActivity.this, ResultDialog.class);
                String getNombreJugador=getIntent().getStringExtra("jugadorNombre");
                intent.putExtra("nombreJugador",getNombreJugador);
                intent.putExtra("jugarConCruces",jugarConCruces);
                String dificultadString = getIntent().getStringExtra("dificultad");
                intent.putExtra("dificultad",dificultadString);
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

    public void juegaMaquina() {
        switch (dificultadSeleccionada) {
            case FACIL:
                juegaFacil();
                break;
            case MEDIA:
                juegaMedia();
                break;
            case DIFICIL:
                juegaDificil();
                break;
        }
    }
    public void juegaFacil() {
        int boxRandom = (int) (Math.random() * 9);
        while (!isBoxSeleccionable(boxRandom)) {
            boxRandom = (int) (Math.random() * 9);
        }
        realizarMovimientoMaquina(boxRandom);
    }
    public void juegaMedia() {
        // 1. Ver si la máquina puede ganar en el próximo movimiento
        for (int i = 0; i < 9; i++) {
            if (isBoxSeleccionable(i)) {
                boxPositions[i] = 2; // Supongamos que la máquina juega aquí
                if (chequearResultados()) {
                    realizarMovimientoMaquina(i);
                    return;
                }
                boxPositions[i] = 0; // Deshacer movimiento
            }
        }

        // 2. Ver si el jugador puede ganar en el próximo movimiento y bloquear
        for (int i = 0; i < 9; i++) {
            if (isBoxSeleccionable(i)) {
                boxPositions[i] = 1; // Supongamos que el jugador juega aquí
                if (chequearResultados()) {
                    boxPositions[i] = 0; // Deshacer movimiento
                    realizarMovimientoMaquina(i);
                    return;
                }
                boxPositions[i] = 0; // Deshacer movimiento
            }
        }

        // 3. Tomar el centro si está libre
        if (isBoxSeleccionable(4)) {
            realizarMovimientoMaquina(4);
            return;
        }

        // 4. Tomar una esquina si está libre
        int[] esquinas = {0, 2, 6, 8};
        for (int esquina : esquinas) {
            if (isBoxSeleccionable(esquina)) {
                realizarMovimientoMaquina(esquina);
                return;
            }
        }

        // 5. Elegir cualquier otra casilla libre
        for (int i = 0; i < 9; i++) {
            if (isBoxSeleccionable(i)) {
                realizarMovimientoMaquina(i);
                return;
            }
        }
    }

    // Este método asocia el índice con su ImageView correspondiente
    private void realizarMovimientoMaquina(int posicion) {
        ImageView view = null;
        switch (posicion) {
            case 0: view = binding.image1; break;
            case 1: view = binding.image2; break;
            case 2: view = binding.image3; break;
            case 3: view = binding.image4; break;
            case 4: view = binding.image5; break;
            case 5: view = binding.image6; break;
            case 6: view = binding.image7; break;
            case 7: view = binding.image8; break;
            case 8: view = binding.image9; break;
        }
        if (view != null) {
            performanceAction(view, posicion);
        }
    }

    public void juegaDificil() {
        int mejorPuntaje = Integer.MIN_VALUE;
        int mejorMovimiento = -1;

        for (int i = 0; i < 9; i++) {
            if (boxPositions[i] == 0) {
                boxPositions[i] = 2;
                int puntaje = minimax(boxPositions.clone(), false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                boxPositions[i] = 0;

                if (puntaje > mejorPuntaje || (puntaje == mejorPuntaje && esMejorPosicion(i, mejorMovimiento))) {
                    mejorPuntaje = puntaje;
                    mejorMovimiento = i;
                }
            }
        }

        if (mejorMovimiento != -1) {
            realizarMovimientoMaquina(mejorMovimiento);
        } else {
            juegaFacil(); // fallback en caso de error
        }
    }

    private int minimax(int[] tablero, boolean esTurnoJugador, int profundidad, int alfa, int beta) {
        if (chequearGanador(tablero, 2)) return 10 - profundidad;
        if (chequearGanador(tablero, 1)) return profundidad - 10;
        if (esEmpate(tablero)) return 0;

        if (esTurnoJugador) {
            int mejor = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (tablero[i] == 0) {
                    tablero[i] = 1;
                    int score = minimax(tablero, false, profundidad + 1, alfa, beta);
                    tablero[i] = 0;
                    mejor = Math.min(mejor, score);
                    beta = Math.min(beta, score);
                    if (beta <= alfa) break;
                }
            }
            return mejor;
        } else {
            int mejor = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (tablero[i] == 0) {
                    tablero[i] = 2;
                    int score = minimax(tablero, true, profundidad + 1, alfa, beta);
                    tablero[i] = 0;
                    mejor = Math.max(mejor, score);
                    alfa = Math.max(alfa, score);
                    if (beta <= alfa) break;
                }
            }
            return mejor;
        }
    }

    private boolean chequearGanador(int[] tablero, int jugador) {
        return (tablero[0] == jugador && tablero[1] == jugador && tablero[2] == jugador) ||
                (tablero[3] == jugador && tablero[4] == jugador && tablero[5] == jugador) ||
                (tablero[6] == jugador && tablero[7] == jugador && tablero[8] == jugador) ||
                (tablero[0] == jugador && tablero[3] == jugador && tablero[6] == jugador) ||
                (tablero[1] == jugador && tablero[4] == jugador && tablero[7] == jugador) ||
                (tablero[2] == jugador && tablero[5] == jugador && tablero[8] == jugador) ||
                (tablero[0] == jugador && tablero[4] == jugador && tablero[8] == jugador) ||
                (tablero[2] == jugador && tablero[4] == jugador && tablero[6] == jugador);
    }

    private boolean esEmpate(int[] tablero) {
        for (int casilla : tablero) {
            if (casilla == 0) return false;
        }
        return true;
    }

    private boolean esMejorPosicion(int nueva, int anterior) {
        int[] prioridad = {4, 0, 2, 6, 8, 1, 3, 5, 7}; // centro > esquinas > bordes
        int prioridadNueva = 1000, prioridadAnterior = 1000;

        for (int i = 0; i < prioridad.length; i++) {
            if (prioridad[i] == nueva) prioridadNueva = i;
            if (prioridad[i] == anterior) prioridadAnterior = i;
        }

        return prioridadNueva < prioridadAnterior;
    }

}