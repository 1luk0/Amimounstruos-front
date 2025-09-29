package com.app.amimounstruos.Screens.Userinf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.MapActivity;

public class AnadirAmigoActivity extends AppCompatActivity {

  private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anadir_amigo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageButton botonMapa = findViewById(R.id.mapButton);
      ImageButton botonPerfil = findViewById(R.id.perfilButton);
      ImageButton botonConfiguracion = findViewById(R.id.configurations);
      ImageButton botonReturn = findViewById(R.id.returnButton);

      // Lógica de avatar (sin cambios)
      int personajeSeleccionado = prefs.getInt("personaje_seleccionado", -1);
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

      // Listeners de Navegación (sin cambios)
      botonMapa.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
      botonConfiguracion.setOnClickListener(v -> startActivity(new Intent(this, configuracionActivity.class)));
      botonPerfil.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));
      botonReturn.setOnClickListener(v -> startActivity(new Intent(this, AmigosActivity.class)));
    }
}
