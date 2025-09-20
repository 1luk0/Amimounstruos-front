package com.app.amimounstruos.Screens.Configurations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.CentroAyuda.CentroAyudaActivity;
import com.app.amimounstruos.Screens.Configurations.CentroAyuda.Notificaciones.NotificacionesActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;

public class configuracionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageButton botonAjustesPrivacidad = findViewById(R.id.btn_privacidad);
      ImageButton botonNotificaciones = findViewById(R.id.btn_notificaciones);
      ImageButton botonCentroAyuda = findViewById(R.id.btn_ayuda);
      ImageButton botonMapa = findViewById(R.id.configurationsButton);
      ImageButton botonPerfil = findViewById(R.id.btn_perfil);

      botonCentroAyuda.setOnClickListener(v -> {
        Intent intent = new Intent(configuracionActivity.this, CentroAyudaActivity.class);
        startActivity(intent);
      });

      botonNotificaciones.setOnClickListener(v -> {
        Intent intent = new Intent(configuracionActivity.this, NotificacionesActivity.class);
        startActivity(intent);
      });

      botonAjustesPrivacidad.setOnClickListener(v -> {
        Intent intent = new Intent(configuracionActivity.this, AjustesPrivacidadActivity.class);
        startActivity(intent);
      });

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(configuracionActivity.this, MapActivity.class);
        startActivity(intent);
      });


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
        Intent intent = new Intent(configuracionActivity.this, UserActivity.class);
        startActivity(intent);
      });

    }
}
