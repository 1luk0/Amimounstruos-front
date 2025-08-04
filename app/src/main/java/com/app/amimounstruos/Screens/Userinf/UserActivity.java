package com.app.amimounstruos.Screens.Userinf;

import android.content.Intent;
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

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageButton botonConfiguracion = findViewById(R.id.configurations);

      ImageButton botonMapa = findViewById(R.id.mapButton);

      ImageButton botonAmigos = findViewById(R.id.amigosButton);

      botonConfiguracion.setOnClickListener(v -> {
        Intent intent = new Intent(UserActivity.this, configuracionActivity.class);
        startActivity(intent);
      });

      botonMapa.setOnClickListener(v -> {
        Intent intent = new Intent(UserActivity.this, MapActivity.class);
        startActivity(intent);
      });

      botonAmigos.setOnClickListener(v -> {
        Intent intent = new Intent(UserActivity.this, AmigosActivity.class);
        startActivity(intent);
      });
    }
}
