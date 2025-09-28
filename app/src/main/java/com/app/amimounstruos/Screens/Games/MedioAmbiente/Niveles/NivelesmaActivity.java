package com.app.amimounstruos.Screens.Games.MedioAmbiente.Niveles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.Models.Nivel; // Se mantiene por si es útil en el futuro, pero no se usa en la lógica de carga
import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel1.HistoriaAguaActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel2.AprendamosAguaActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel3.TuberiaActivity;
import com.app.amimounstruos.Screens.Games.MedioAmbiente.Nivel4.Banera1Activity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Screens.Userinf.UserActivity;
import com.app.amimounstruos.Services.NivelService; // Se mantiene, pero no se usa en la carga
import com.app.amimounstruos.Services.ProgresoService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NivelesmaActivity extends BaseActivity {
  private static final int CURSO_ID = 22; // ID del curso Medio Ambiente
  private static final String TAG = "NivelesmaActivity";

  // Mapa para asociar números de nivel con sus clases de actividad
  private final Map<Integer, Class<?>> nivelActivities = new HashMap<>();
  private final Map<Integer, Integer> basurasPorNivel = new HashMap<>();

  // Variables de sesión
  private SharedPreferences prefs;
  private int userId;

  // Retrofit instances
  private ProgresoService progresoService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nivelesma);

    // 1. Inicialización de datos
    nivelActivities.put(1, HistoriaAguaActivity.class);
    nivelActivities.put(2, AprendamosAguaActivity.class);
    nivelActivities.put(3, TuberiaActivity.class);
    nivelActivities.put(4, Banera1Activity.class);

    // Nivel 1 Completado
    basurasPorNivel.put(R.id.basura1, 1);
    basurasPorNivel.put(R.id.basura2, 1);

    // Nivel 2 Completado
    basurasPorNivel.put(R.id.basura3, 2);
    basurasPorNivel.put(R.id.basura5, 2);

    // Nivel 3 Completado
    basurasPorNivel.put(R.id.basura9, 3);
    basurasPorNivel.put(R.id.basura10, 3);

    // Nivel 4 Completado
    basurasPorNivel.put(R.id.basura11, 4);
    basurasPorNivel.put(R.id.basura12, 4);

    // Puedes seguir agregando más pares (basura, nivel) aquí:
    basurasPorNivel.put(R.id.basura13, 5);
    basurasPorNivel.put(R.id.basura18, 5);

    // ... (Agrega el resto de basuras y sus niveles correspondientes aquí) ...
    basurasPorNivel.put(R.id.basura19, 6);
    basurasPorNivel.put(R.id.basura20, 6);
    basurasPorNivel.put(R.id.basura21, 7);
    basurasPorNivel.put(R.id.basura22, 7);
    basurasPorNivel.put(R.id.basura23, 8);

    prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
    userId = prefs.getInt("user_id", 0);

    // 2. Configuración de Retrofit
    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();
    progresoService = retrofit.create(ProgresoService.class);

    // 3. Configuración de Botones de Navegación (se queda en onCreate)
    setupNavigationButtons();
  }

  @Override
  protected void onResume() {
    super.onResume();
    // PASO CLAVE 1: Recargar el progreso cada vez que la Activity es visible
    loadUserProgreso();
  }

  /**
   * Carga el progreso del usuario y actualiza el estado de los botones de nivel.
   */
  private void loadUserProgreso() {
    if (userId == 0) {
      Log.e(TAG, "ID de usuario no encontrado. Desbloqueando solo Nivel 1.");
      Toast.makeText(this, "ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
      habilitarNivelesHasta(1);
      return;
    }

    Log.d(TAG, "Iniciando carga de progreso para Usuario ID: " + userId + " y Curso ID: " + CURSO_ID);


    progresoService.getProgresoByUsuarioYCurso(userId, CURSO_ID).enqueue(new Callback<Progreso>() {
      @Override
      public void onResponse(Call<Progreso> call, Response<Progreso> response) {
        if (response.isSuccessful() && response.body() != null) {
          Progreso progreso = response.body();
          Integer nivelActualNumero = (Integer) progreso.getNumero();

          Log.d(TAG, "API Exitosa. Nivel 'numero' recibido: " + nivelActualNumero);

          if (nivelActualNumero == null) {
            Log.w(TAG, "Campo 'numero' es NULL. Iniciando en Nivel 1.");
            habilitarNivelesHasta(1); // Si el campo es nulo, iniciamos en 1
            return;
          }

          // PASO CLAVE 3: Habilitar el siguiente nivel al que está en progreso
          // Si el progreso dice Nivel 1 (en curso), desbloqueamos el Nivel 2.
          int nivelesADesbloquear = nivelActualNumero + 1;
          habilitarNivelesHasta(nivelesADesbloquear);
          ocultarBasurasHasta(nivelActualNumero);
          Log.d(TAG, "Niveles desbloqueados hasta: " + nivelesADesbloquear);

        } else {
          // Si la API devuelve 404 o falla (ej. sin registro)
          Log.e(TAG, "Error en API de Progreso. Código: " + response.code() + ". Mensaje: " + response.message());
          habilitarNivelesHasta(1);
          ocultarBasurasHasta(0);
          Log.w(TAG, "Desbloqueando solo Nivel 1 por fallo de API.");
        }
      }

      @Override
      public void onFailure(Call<Progreso> call, Throwable t) {
        Log.e(TAG, "Fallo de red al obtener progreso: " + t.getMessage());
        habilitarNivelesHasta(1);
      }
    });
  }

  /**
   * Configura los botones de navegación (Mapa, Perfil, Configuración).
   */
  private void setupNavigationButtons() {
    ImageButton botonMapa = findViewById(R.id.mapButton);
    ImageButton botonPerfil = findViewById(R.id.perfilButton);
    ImageButton botonConfiguracion = findViewById(R.id.configurations);

    // Lógica de avatar (sin cambios)
    int personajeSeleccionado = prefs.getInt("personaje_seleccionado", -1);
    int avatarBtnResId;
    switch (personajeSeleccionado) {
      case 1:
        avatarBtnResId = R.drawable.monster1btn;
        break;
      case 2:
        avatarBtnResId = R.drawable.monster2btn;
        break;
      case 3:
        avatarBtnResId = R.drawable.monster3btn;
        break;
      case 4:
        avatarBtnResId = R.drawable.monster4btn;
        break;
      default:
        avatarBtnResId = R.drawable.monster1btn;
        break;
    }
    botonPerfil.setImageResource(avatarBtnResId);

    // Listeners de Navegación (sin cambios)
    botonMapa.setOnClickListener(v -> startActivity(new Intent(this, MapActivity.class)));
    botonConfiguracion.setOnClickListener(v -> startActivity(new Intent(this, configuracionActivity.class)));
    botonPerfil.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));
  }


  /**
   * Habilita solo los niveles hasta "maxNivelPermitido" y configura los listeners.
   */
  private void habilitarNivelesHasta(int maxNivelPermitido) {
    ColorMatrix matrix = new ColorMatrix();
    matrix.setSaturation(0);
    final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

    for (int i = 1; i <= 9; i++) {
      int resId = getResources().getIdentifier("nivel" + i, "id", getPackageName());
      ImageButton boton = findViewById(resId);

      if (boton != null) {
        if (i <= maxNivelPermitido) {
          boton.setColorFilter(null);
          boton.setEnabled(true);
          final int nivelActual = i;
          boton.setOnClickListener(v -> {
            Class<?> activityClass = nivelActivities.get(nivelActual);
            if (activityClass != null) {
              startActivity(new Intent(this, activityClass));
            } else {
              Toast.makeText(this, "Nivel " + nivelActual + " no disponible", Toast.LENGTH_SHORT).show();
            }
          });
        } else {
          boton.setColorFilter(filter);
          boton.setEnabled(false);
          boton.setOnClickListener(v ->
            Toast.makeText(this, "Este nivel está bloqueado", Toast.LENGTH_SHORT).show()
          );
        }
      }
    }
  }

  private void ocultarBasurasHasta(int nivelCompletado) {
    for (Map.Entry<Integer, Integer> entry : basurasPorNivel.entrySet()) {
      ImageView basura = findViewById(entry.getKey());
      if (basura != null) {
        if (entry.getValue() <= nivelCompletado) {
          basura.setVisibility(View.INVISIBLE);
        } else {
          basura.setVisibility(View.VISIBLE);
        }
      }
    }
  }
}
