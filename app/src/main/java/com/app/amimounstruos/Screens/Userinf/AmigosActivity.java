package com.app.amimounstruos.Screens.Userinf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.app.amimounstruos.Components.BaseActivity;
import com.app.amimounstruos.BuildConfig;
import com.app.amimounstruos.R;
import com.app.amimounstruos.Screens.Configurations.configuracionActivity;
import com.app.amimounstruos.Screens.MapActivity;
import com.app.amimounstruos.Services.AmigoService;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmigosActivity extends BaseActivity {

  private LinearLayout listaAmigos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);

    setContentView(R.layout.activity_amigos);

    ImageButton AgregarAmigosButton = findViewById(R.id.AgregarAmigosButton);
    ImageButton botonMapa = findViewById(R.id.mapButton);
    ImageButton botonPerfil = findViewById(R.id.perfilButton);
    ImageButton botonConfiguracion = findViewById(R.id.configurations);
    ImageButton botonReturn = findViewById(R.id.returnButton);

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
    botonReturn.setOnClickListener(v -> startActivity(new Intent(this, UserActivity.class)));

    AgregarAmigosButton.setOnClickListener(v -> startActivity(new Intent(this, AnadirAmigoActivity.class)));
    listaAmigos = findViewById(R.id.listaAmigos);

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL) // cambia si estás en dispositivo real
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    AmigoService amigoService = retrofit.create(AmigoService.class);
    int userId = prefs.getInt("user_id", -1);  // ← Usa la misma clave: "user_id"
    amigoService.getAmigoByUserId(userId).enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (!response.isSuccessful() || response.body() == null) {
          Log.e("Amigos", "Respuesta inválida");
          return;
        }

        try {
          String json = response.body().string();
          JSONArray amigos = new JSONArray(json);

          for (int i = 0; i < amigos.length(); i++) {
            JSONObject obj = amigos.getJSONObject(i);
            String nombre = obj.getString("nombre");
            int amimounstruo = obj.getInt("amimounstruo");

            runOnUiThread(() -> {
              AmigoItem item = new AmigoItem(AmigosActivity.this);
              item.setNombre(nombre);
              item.setNumero(amimounstruo);

              // Ancho para que ocupe TODO el espacio disponible.
              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // <-- ¡Este es el cambio!
                LinearLayout.LayoutParams.WRAP_CONTENT
              );

// Opcional: puedes ajustar los márgenes si quieres un poco de espacio vertical
// entre los items, pero los márgenes horizontales deberían ser 0.
              params.setMargins(0, 8, 0, 8); // (izquierda, arriba, derecha, abajo)

              item.setLayoutParams(params);

              listaAmigos.addView(item);
            });

          }

        } catch (Exception e) {
          Log.e("Amigos", "Error procesando JSON", e);
        }
      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e("Amigos", "Error de red", t);
      }
    });
  }


  private void agregarAmigo(String nombre, int numero) {
    AmigoItem item = new AmigoItem(this);
    item.setNombre(nombre);
    item.setNumero(numero);
//    listaAmigos.addView(item);
  }
}
