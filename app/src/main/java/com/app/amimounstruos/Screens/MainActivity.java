package com.app.amimounstruos.Screens;

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

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Verificar si el usuario ya está registrado
    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
    boolean registrado = prefs.getBoolean("usuario_registrado", false);

//    if (registrado) {
//      // Redirigir directamente al mapa
//      Intent intent = new Intent(MainActivity.this, MapActivity.class);
//      startActivity(intent);
//      finish(); // Importante para no volver atrás
//      return;
//    }

    // Si no está registrado, mostrar pantalla inicial normalmente
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    ImageButton boton = findViewById(R.id.imageButton);
    boton.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivity(intent);
    });
  }
}
