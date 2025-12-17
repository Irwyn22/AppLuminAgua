package com.example.appluminagua.api;

import com.example.appluminagua.models.LoginResponse;
import com.example.appluminagua.models.ReporteResponse;
import com.example.appluminagua.models.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/reportes")
    Call<ReporteResponse> enviarReporte(
            @Field("nombre") String nombre,
            @Field("calle1") String calle1,
            @Field("calle2") String calle2,
            @Field("colonia") String colonia,
            @Field("cp") String cp,
            @Field("descripcion") String descripcion,
            @Field("imagen") String imagen
    );

    // Reporte de Luz
    @FormUrlEncoded
    @POST("api/reportes-luz")
    Call<ReporteResponse> enviarReporteLuz(
            @Field("nombre") String nombre,
            @Field("calle1") String calle1,
            @Field("calle2") String calle2,
            @Field("colonia") String colonia,
            @Field("cp") String cp,
            @Field("descripcion") String descripcion,
            @Field("imagen") String imagen
    );

    @FormUrlEncoded
    @POST("api/usuarios")
    Call<UsuarioResponse> crearUsuario(
            @Field("nombre") String nombre,
            @Field("usuario") String usuario,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/login")
    Call<LoginResponse> login(
            @Field("usuario") String usuario,
            @Field("password") String password
    );
}
