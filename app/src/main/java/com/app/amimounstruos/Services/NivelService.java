package com.app.amimounstruos.Services;

import com.app.amimounstruos.Models.Nivel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NivelService {
    @GET("/niveles/{id}")
    Call<Nivel> getNivelById(@Path("id") int id);


}
