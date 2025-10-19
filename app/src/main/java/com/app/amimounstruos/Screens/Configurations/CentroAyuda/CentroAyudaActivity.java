package com.app.amimounstruos.Screens.Configurations.CentroAyuda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.CentroAyuda.Notificaciones.NotificacionesActivity;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;

public class CentroAyudaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_centro_ayuda);

      ImageButton botonMapa = findViewById(R.id.salirButton);
      ImageButton botonRegreso = findViewById(R.id.returnButton);
      ImageButton botonPerfil = findViewById(R.id.perfilButton);

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(CentroAyudaActivity.this, MapActivity.class);
        startActivity(intent);
      });
      botonRegreso.setOnClickListener(v -> {
        Intent intent = new Intent(CentroAyudaActivity.this, configuracionActivity.class);
        startActivity(intent);
      });

      botonPerfil.setOnClickListener(v -> {
        Intent intent = new Intent(CentroAyudaActivity.this, UserActivity.class);
        startActivity(intent);
      });
    }
}
