package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.Components.DialogPopUpActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;

public class CorrectoPersonasActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_correcto_personas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonContinue = findViewById(R.id.botonContinuar);
      ImageButton botonSalir = findViewById(R.id.salirButton);

      botonContinue.setOnClickListener(v -> {
        Intent intent = new Intent(CorrectoPersonasActivity.this, HistoriaAgua6Activity.class);
        startActivity(intent);
      });

      botonSalir.setOnClickListener(v -> {
        DialogPopUpActivity dialog = DialogPopUpActivity.newInstance(
          NivelesmaActivity.class,      // Clase de destino
          R.drawable.salirbk            // Imagen de "Estás seguro que deseas salir?"
        );
        dialog.show(getSupportFragmentManager(), "confirmacion_salida_nivel");
      });
    }
}
