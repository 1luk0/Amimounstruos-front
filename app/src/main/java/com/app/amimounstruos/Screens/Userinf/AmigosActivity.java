package com.app.amimounstruos.Screens.Userinf;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.amimounstruos.R;
import com.app.amimounstruos.Services.AmigoService;
import com.app.amimounstruos.Services.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmigosActivity extends AppCompatActivity {

    private LinearLayout listaAmigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos); // Asegúrate que se llame así

        listaAmigos = findViewById(R.id.listaAmigos);

        SharedPreferences prefs = getSharedPreferences("amimonstruos_prefs", MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3333/") // Cambia esto si usas dispositivo real
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AmigoService amigoService = retrofit.create(AmigoService.class);
        UserService usuarioService = retrofit.create(UserService.class);

        amigoService.getAmigoByUserId(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(AmigosActivity.this, "Error al obtener amigos", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String json = response.body().string();
                    JSONArray amigosArray = new JSONArray(json);

                    for (int i = 0; i < amigosArray.length(); i++) {
                        int amigoId = amigosArray.getInt(i);

                        usuarioService.obtenerusuario(amigoId).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (!response.isSuccessful() || response.body() == null) return;

                                try {
                                    String userJson = response.body().string();
                                    JSONObject obj = new JSONObject(userJson);
                                    String nombre = obj.getString("nombre");
                                    int amimounstruo = obj.getInt("amimounstruo");

                                    runOnUiThread(() -> {
                                        AmigoItem item = new AmigoItem(AmigosActivity.this);
                                        item.setNombre(nombre);
                                        item.setNumero(amimounstruo);
                                        listaAmigos.addView(item);
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AmigosActivity.this, "Error de formato en respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AmigosActivity.this, "Fallo de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}