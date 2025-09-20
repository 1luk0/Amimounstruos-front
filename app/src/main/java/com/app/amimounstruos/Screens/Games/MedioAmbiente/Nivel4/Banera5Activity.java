package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2.PequenoHeroeActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;
import com.app.amimounstruos.Screens.MapActivity;

public class Banera5Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banera5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      ImageView toallaButton = findViewById(R.id.toallaButton);
      ImageButton botonNiveles = findViewById(R.id.mapButton);

      toallaButton.setOnClickListener(v -> {
        Intent intent = new Intent(Banera5Activity.this, Banera6Activity.class);
        startActivity(intent);
      });

      botonNiveles.setOnClickListener(v -> {
        Intent intent = new Intent(Banera5Activity.this, NivelesmaActivity.class);
        startActivity(intent);
      });

    }

  protected boolean usarAnimacionTransicion() {
    return false; // por defecto todas las activities usan animación
  }

}
