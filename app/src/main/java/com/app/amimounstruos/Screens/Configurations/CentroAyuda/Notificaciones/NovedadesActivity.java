package com.app.amimounstruos.Screens.Configurations.CentroAyuda.Notificaciones;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;

public class NovedadesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novedades);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonRegreso = findViewById(R.id.returnButton);

      ImageButton botonAjustes = findViewById(R.id.salirButton);

      ImageButton botonPerfil = findViewById(R.id.perfilButton);


      botonPerfil.setOnClickListener(v -> {
        Intent intent = new Intent(NovedadesActivity.this, UserActivity.class);
        startActivity(intent);
      });


      botonAjustes.setOnClickListener(v -> {
        Intent intent = new Intent(NovedadesActivity.this, configuracionActivity.class);
        startActivity(intent);
      });

      botonRegreso.setOnClickListener(v -> {
        Intent intent = new Intent(NovedadesActivity.this, NotificacionesActivity.class);
        startActivity(intent);
      });
    }
}
