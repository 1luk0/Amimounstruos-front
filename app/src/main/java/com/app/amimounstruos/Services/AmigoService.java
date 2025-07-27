package com.app.amimounstruos.Services;

import com.app.amimounstruos.Models.Amigo;
import com.app.amimounstruos.Models.Progreso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface AmigoService {
  @GET("/amigos/{id}")
  Call<ResponseBody> getAmigoByUserId(@Path("id") Number id);

  @POST("/amigos") // cambia esto por la ruta real de tu backend
  Call<Void> registrarAmigo(@Body Amigo amigo);
}
