package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.util.Log; // Asegúrate de importar esto

import com.app.amimounstruos.BaseActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.CentroAyuda.CentroAyudaActivity;
import com.app.amimounstruos.Screens.MapActivity;

public class TuberiaActivity extends BaseActivity {

  private ImageButton tuberia1, tuberia2, tuberia3, tuberia4, tuberia5, tuberia6;
  private ImageButton continueArrow;

  private final int[] correctAngles = {270, 90, 45, 90, 270, 270}; // Ajusta según lo que consideres correcto
  private final int[] currentAngles = {0, 0, 0, 0, 0, 0};


  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tuberia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    tuberia1 = findViewById(R.id.tuberia1Button);
    tuberia2 = findViewById(R.id.tuberia2Button);
    tuberia3 = findViewById(R.id.tuberia3Button);
    tuberia4 = findViewById(R.id.tuberia4Button);
    tuberia5 = findViewById(R.id.tuberia5Button);
    tuberia6 = findViewById(R.id.tuberia6Button);
    continueArrow = findViewById(R.id.continueArrow);

    continueArrow.setVisibility(View.INVISIBLE); // Ocultar inicialmente

    setRotateListener(tuberia1, 0, 90);
    setRotateListener(tuberia2, 1, 90);
    setRotateListener(tuberia3, 2, 45); // Solo esta rota de 45°
    setRotateListener(tuberia4, 3, 90);
    setRotateListener(tuberia5, 4, 90);
    setRotateListener(tuberia6, 5, 90);

    continueArrow.setOnClickListener(v -> {
      Intent intent = new Intent(TuberiaActivity.this, TuberiasReparadasActivity.class);
      startActivity(intent);
    });

  }


  private void setRotateListener(ImageButton button, int index, int angleStep) {
    button.setOnClickListener(v -> {
      currentAngles[index] = (currentAngles[index] + angleStep) % 360;
      button.setRotation(currentAngles[index]);
      Log.d("TUBERIA_ROTATION", "Tubería " + (index + 1) + " rotación: " + currentAngles[index] + "°");
      checkAllCorrect();
    });
  }


  private void checkAllCorrect() {
    StringBuilder estado = new StringBuilder("Rotaciones actuales: ");

    for (int i = 0; i < correctAngles.length; i++) {
      estado.append("T").append(i + 1).append(": ").append(currentAngles[i]).append("° ");

      // Caso especial: tubería 3 (índice 2) acepta cualquier múltiplo de 45
      if (i == 2) {
        if (currentAngles[i] % 45 != 0) {
          continueArrow.setVisibility(View.INVISIBLE);
          Log.d("TUBERIA_VALIDACION", "Tubería 3 inválida: " + currentAngles[i] + "°");
          return;
        }
      } else {
        // Comparación normal para el resto
        if (currentAngles[i] != correctAngles[i]) {
          continueArrow.setVisibility(View.INVISIBLE);
          Log.d("TUBERIA_VALIDACION", "No coincide la tubería " + (i + 1));
          return;
        }
      }
    }

    Log.d("TUBERIA_VALIDACION", estado.toString());
    Log.d("TUBERIA_VALIDACION", "¡Todas las tuberías están en la posición correcta!");
    continueArrow.setVisibility(View.VISIBLE);
  }





}
