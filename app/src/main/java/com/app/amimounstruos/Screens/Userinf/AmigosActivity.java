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
    setContentView(R.layout.activity_amigos);

    ImageButton AgregarAmigosButton = findViewById(R.id.AgregarAmigosButton);

    AgregarAmigosButton.setOnClickListener(v -> startActivity(new Intent(this, AnadirAmigoActivity.class)));

    listaAmigos = findViewById(R.id.listaAmigos);

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BuildConfig.BACKEND_URL) // cambia si estás en dispositivo real
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    AmigoService amigoService = retrofit.create(AmigoService.class);

    SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
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
    listaAmigos.addView(item);
  }
}
