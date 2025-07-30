package com.app.amimounstruos.Services;

import com.app.amimounstruos.Models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
  @POST("/usuarios") // cambia esto por la ruta real de tu backend
  Call<User> registrarUsuario(@Body User user);

  @GET("/usuarios/nombre/{nombre}")
  Call<ResponseBody> verificarNombre(@Path("nombre") String nombre);
  @GET("/usuarios/{id}")
  Call<ResponseBody> obtenerusuario(@Path("id") int id);
}
