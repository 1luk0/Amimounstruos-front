package com.app.amimounstruos;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectCharacterActivity extends AppCompatActivity {

    private ImageView imgPersonaje;
    private int personajeActual = 0;
    private int[] personajes = {
            R.drawable.monster1,
            R.drawable.monster2,
            R.drawable.monster3,
            R.drawable.monster4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_character);

        imgPersonaje = findViewById(R.id.imgPersonaje);
        ImageButton btnIzquierda = findViewById(R.id.btizquierda);
        ImageButton btnDerecha = findViewById(R.id.btderecha);
        ImageButton btnConfirmar = findViewById(R.id.btcheck);

        actualizarImagen();

        btnIzquierda.setOnClickListener(v -> {
            personajeActual = (personajeActual - 1 + personajes.length) % personajes.length;
            actualizarImagen();
        });

        btnDerecha.setOnClickListener(v -> {
            personajeActual = (personajeActual + 1) % personajes.length;
            actualizarImagen();
        });

        btnConfirmar.setOnClickListener(v -> {
            confirmarSeleccion(personajeActual + 1);
        });
    }

    private void actualizarImagen() {
        imgPersonaje.setImageResource(personajes[personajeActual]);
    }

    private void confirmarSeleccion(int personaje) {
        Toast.makeText(this, "Seleccionaste el personaje " + personaje, Toast.LENGTH_SHORT).show();
        // Aquí puedes pasar el personaje a otra actividad si quieres
    }
}