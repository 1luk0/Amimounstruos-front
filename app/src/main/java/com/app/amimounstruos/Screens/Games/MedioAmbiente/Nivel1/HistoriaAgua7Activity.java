package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Components.DialogPopUpActivity;
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;
import com.app.amimounstruos.Services.ProgresoService;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class HistoriaAgua7Activity extends BaseActivity {
  private static final String TAG = "HistoriaAgua7Activity";
  private final int CURSO_ID = 22;
  // Se establece el progreso al NIVEL 1, ya que se acaba de completar.
  private final int NIVEL_DESTINO = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_historia_agua7);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    ImageButton botonContinue = findViewById(R.id.botonContinuar);
    ImageButton botonSalir = findViewById(R.id.salirButton);

    botonContinue.setOnClickListener(v -> {
      SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
      int usuarioId = prefs.getInt("user_id", -1);

      if (usuarioId == -1) {
        Log.e(TAG, "ID de usuario no encontrado.");
        makeText(this, "Error: No se encontró el ID de usuario.", Toast.LENGTH_SHORT).show();
        return;
      }

      // Crear el objeto Progreso para actualizar el Nivel 1 como 'completado'
      Progreso progreso = new Progreso(usuarioId, CURSO_ID, NIVEL_DESTINO, "completado");

      Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BuildConfig.BACKEND_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

      ProgresoService service = retrofit.create(ProgresoService.class);

      // Llamada a la función de actualización (PUT)
      Call<Progreso> call = service.updateProgreso(usuarioId, progreso);

      call.enqueue(new Callback<Progreso>() {
        @Override
        public void onResponse(Call<Progreso> call, Response<Progreso> response) {
          if (response.isSuccessful()) {
            Log.d(TAG, "Progreso actualizado a nivel " + NIVEL_DESTINO + " con éxito.");
            // Ahora que la actualización fue exitosa, navegamos a la pantalla de niveles
            Intent intent = new Intent(HistoriaAgua7Activity.this, NivelesmaActivity.class);
            startActivity(intent);
            finish();
          } else {
            Log.e(TAG, "Error en la respuesta: " + response.code() + " - " + response.message());
            makeText(HistoriaAgua7Activity.this, "Error al actualizar el progreso. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
          }
        }

        @Override
        public void onFailure(Call<Progreso> call, Throwable t) {
          Log.e(TAG, "Fallo en la petición: " + t.getMessage(), t);
          makeText(HistoriaAgua7Activity.this, "Error de red. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
        }
      });
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
