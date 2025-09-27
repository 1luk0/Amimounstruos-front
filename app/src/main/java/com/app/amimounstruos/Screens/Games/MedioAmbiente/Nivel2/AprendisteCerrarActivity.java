package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2;

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

import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;
import com.app.amimounstruos.Services.ProgresoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AprendisteCerrarActivity extends BaseActivity {
  private static final String TAG = "AprendisteCerrarActivity";
  private final int CURSO_ID = 22;
  // Se establece el progreso al NIVEL 2, ya que se acaba de completar.
  private final int NIVEL_DESTINO = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_aprendiste_cerrar);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    ImageButton botonContinue = findViewById(R.id.botonContinuar);


    botonContinue.setOnClickListener(v -> {
      updateProgresoAndNavigate();
    });
  }



  private void updateProgresoAndNavigate() {
    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
    int usuarioId = prefs.getInt("user_id", -1);

    if (usuarioId == -1) {
      makeText(this, "Error: No se encontró el ID de usuario.", Toast.LENGTH_SHORT).show();
      return;
    }

    Progreso progreso = new Progreso(usuarioId, CURSO_ID, NIVEL_DESTINO, "completado");

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    ProgresoService service = retrofit.create(ProgresoService.class);

    // --- Log de Diagnóstico ---
    Log.d(TAG, "Intentando PUT para Usuario: " + usuarioId + ", Nivel: " + NIVEL_DESTINO);
    // -------------------------

    Call<Progreso> call = service.updateProgreso(usuarioId, progreso);

    call.enqueue(new Callback<Progreso>() {
      @Override
      public void onResponse(Call<Progreso> call, Response<Progreso> response) {
        if (response.isSuccessful()) {
          Log.d(TAG, "Progreso actualizado a nivel " + NIVEL_DESTINO + " con éxito.");
          Intent intent = new Intent(AprendisteCerrarActivity.this, NivelesmaActivity.class);
          startActivity(intent);
          finish();
        } else {
          Log.e(TAG, "Error en la respuesta PUT: " + response.code() + " - " + response.message());
          // Añadimos navegación de fallback en caso de fallo, para que el usuario no se quede atascado
          makeText(AprendisteCerrarActivity.this, "Error al actualizar. Regresando a niveles.", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(AprendisteCerrarActivity.this, NivelesmaActivity.class);
          startActivity(intent);
          finish();
        }
      }

      @Override
      public void onFailure(Call<Progreso> call, Throwable t) {
        Log.e(TAG, "Fallo en la petición PUT: " + t.getMessage(), t);
        // Añadimos navegación de fallback en caso de fallo de red
        makeText(AprendisteCerrarActivity.this, "Error de red. Regresando a niveles.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AprendisteCerrarActivity.this, NivelesmaActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }
}
