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

public class PreguntaAnimalesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pregunta_animales);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageButton botonCorrecto = findViewById(R.id.opcion2);
      ImageButton botonInCorrecto = findViewById(R.id.opcion1);
      ImageButton botonSalir = findViewById(R.id.salirButton);

      botonCorrecto.setOnClickListener(v -> {
        Intent intent = new Intent(PreguntaAnimalesActivity.this, CorrectoAnimalesActivity.class);
        startActivity(intent);
      });



      botonInCorrecto.setOnClickListener(v -> {
        Intent intent = new Intent(PreguntaAnimalesActivity.this, IncorrectoAnimalesActivity.class);
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
