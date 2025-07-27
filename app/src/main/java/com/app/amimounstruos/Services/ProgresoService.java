package com.app.amimounstruos.Services;


import com.app.amimounstruos.Models.Progreso;
import com.app.amimounstruos.Models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProgresoService {

  @POST("/progresos") // cambia esto por la ruta real de tu backend
  Call<Void> registrarProgreso(@Body Progreso progreso);
  @GET("/progresos/{id}")
  Call<ResponseBody> getProgresoByUserId(@Path("id") Number id);
}
