package com.app.amimounstruos.Screens.Configurations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.CentroAyuda.CentroAyudaActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;

public class AjustesPrivacidadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajustes_privacidad);


      ImageButton botonMapa = findViewById(R.id.salirButton);
      ImageButton botonRegreso = findViewById(R.id.returnButton);
      ImageButton botonPerfil = findViewById(R.id.perfilButton);

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(AjustesPrivacidadActivity.this, MapActivity.class);
        startActivity(intent);
      });
      botonRegreso.setOnClickListener(v -> {
        Intent intent = new Intent(AjustesPrivacidadActivity.this, configuracionActivity.class);
        startActivity(intent);
      });

      botonPerfil.setOnClickListener(v -> {
        Intent intent = new Intent(AjustesPrivacidadActivity.this, UserActivity.class);
        startActivity(intent);
      });
    }
}
