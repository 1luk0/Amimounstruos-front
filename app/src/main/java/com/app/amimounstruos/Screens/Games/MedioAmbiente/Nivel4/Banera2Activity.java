package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.Components.DialogPopUpActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;

public class Banera2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banera2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      ImageView baneraButton = findViewById(R.id.baneraButton);
      ImageButton botonSalir = findViewById(R.id.salirButton);

      baneraButton.setOnClickListener(v -> {
        Intent intent = new Intent(Banera2Activity.this, Banera3Activity.class);
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

  protected boolean usarAnimacionTransicion() {
    return false; // por defecto todas las activities usan animación
  }
}
