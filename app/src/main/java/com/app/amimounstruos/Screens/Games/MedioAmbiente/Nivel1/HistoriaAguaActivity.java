package com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1;

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
import com.app.amimounstruos.Components.DialogPopUpActivity;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles.NivelesmaActivity;
import com.app.amimounstruos.Services.ProgresoService;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1.GuardianAguaActivity; // Asumo que esta es la siguiente Activity

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoriaAguaActivity extends BaseActivity {

  private static final String TAG = "HistoriaAguaActivity";
  // Usamos final para las constantes
  private final int CURSO_ID = 22; // ID del curso Medio Ambiente (Ajusté a 21 basado en tu código anterior)
  private final int NIVEL_INICIAL = 1;
  private final String ESTADO_INICIAL = "en curso";

  // Declaramos SharedPreferences y userId a nivel de clase para fácil acceso
  private SharedPreferences prefs;
  private int userId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_historia_agua);

    // Inicializar SharedPreferences y obtener userId
    prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
    userId = prefs.getInt("user_id", 0);

    if (userId != 0) {
      // Llama a la función de creación de progreso tan pronto como se inicia el nivel 1
      // Se usa registrarProgreso para coincidir con el servicio
      registrarProgreso(userId, CURSO_ID, NIVEL_INICIAL, ESTADO_INICIAL);
    } else {
      Toast.makeText(this, "ID de usuario no encontrado. Progreso no guardado.", Toast.LENGTH_LONG).show();
    }

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    ImageButton botonContinue = findViewById(R.id.botonContinuar);
    ImageButton botonSalir = findViewById(R.id.salirButton);

    botonContinue.setOnClickListener(v -> {
      Intent intent = new Intent(HistoriaAguaActivity.this, GuardianAguaActivity.class);
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

  /**
   * Intenta crear o actualizar el progreso del usuario en el backend.
   * Utiliza el método registrarProgreso del ProgresoService.
   */
  private void registrarProgreso(int userId, int cursoId, int nivelId, String estado) {
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    ProgresoService progresoService = retrofit.create(ProgresoService.class);

    // Crear el objeto Progreso con los datos iniciales
    Progreso progresoInicial = new Progreso(userId, cursoId, nivelId, estado);

    // Se utiliza progresoService.registrarProgreso para coincidir con la interfaz
    progresoService.registrarProgreso(progresoInicial).enqueue(new Callback<Void>() {
      @Override
      public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
          Log.d(TAG, "Progreso inicial registrado (en curso): " + nivelId);
        } else {
          // Si el código de respuesta es 4xx o 5xx
          Log.e(TAG, "Error al registrar progreso: Código " + response.code() + ", Mensaje: " + response.message());
        }
      }

      @Override
      public void onFailure(Call<Void> call, Throwable t) {
        Log.e(TAG, "Fallo de red al registrar progreso: " + t.getMessage());
      }
    });
  }
}
