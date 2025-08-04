package com.app.amimounstruos.Screens;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2.AprendamosAguaActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesActivity;
import com.app.amimounstruos.Screens.Userinf.AmigosActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageButton botonConfiguracion = findViewById(R.id.configurations);

      ImageButton botonMedioAmbiente = findViewById(R.id.medioAmbiente);

      ImageButton botonModales = findViewById(R.id.modales);

      ImageButton botonPerfil = findViewById(R.id.btn_perfil);

      ImageButton botonAmigos = findViewById(R.id.amigos);

      botonAmigos.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, AmigosActivity.class);
        startActivity(intent);
      });



      botonPerfil.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, UserActivity.class);
        startActivity(intent);
      });


      botonModales.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, AprendamosAguaActivity.class);
        startActivity(intent);
      });

      botonMedioAmbiente.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, NivelesActivity.class);
        startActivity(intent);
      });

      botonConfiguracion.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, configuracionActivity.class);
        startActivity(intent);
      });
    }
    }
