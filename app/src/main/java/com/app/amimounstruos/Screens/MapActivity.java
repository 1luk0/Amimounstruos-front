package com.app.amimounstruos.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2.AprendamosAguaActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;

public class MapActivity extends BaseActivity {

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

      ImageButton botonPerfil = findViewById(R.id.btn_perfil);

      SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
      int personajeSeleccionado = prefs.getInt("personaje_seleccionado", -1); // -1 por si no hay

      // Mapea personajeSeleccionado con el drawable correspondiente (botón)
      int avatarBtnResId;
      switch (personajeSeleccionado) {
        case 1:
          avatarBtnResId = R.drawable.monster1btn;
          break;
        case 2:
          avatarBtnResId = R.drawable.monster2btn;
          break;
        case 3:
          avatarBtnResId = R.drawable.monster3btn;
          break;
        case 4:
          avatarBtnResId = R.drawable.monster4btn;
          break;
        default:
          avatarBtnResId = R.drawable.monster1btn;
          break;
      }


      botonPerfil.setImageResource(avatarBtnResId);


      botonPerfil.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, UserActivity.class);
        startActivity(intent);
      });

      botonMedioAmbiente.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, NivelesmaActivity.class);
        startActivity(intent);
      });

      botonConfiguracion.setOnClickListener(v -> {
        Intent intent = new Intent(MapActivity.this, configuracionActivity.class);
        startActivity(intent);
      });
    }
    }
