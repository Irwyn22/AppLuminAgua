package com.example.appluminagua;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appluminagua.api.ApiService;
import com.example.appluminagua.api.RetrofitClient;
import com.example.appluminagua.models.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearUsuarioActivity extends AppCompatActivity {

    EditText editNombre, editNuevoUsuario, editNuevaPassword;
    Button btnRegistrar, btnVolverLogin;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        editNombre = findViewById(R.id.editNombre);
        editNuevoUsuario = findViewById(R.id.editNuevoUsuario);
        editNuevaPassword = findViewById(R.id.editNuevaPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
        btnVolverLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void registrarUsuario() {
        String nombre = editNombre.getText().toString();
        String usuario = editNuevoUsuario.getText().toString();
        String password = editNuevaPassword.getText().toString();

        if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<UsuarioResponse> call = apiService.crearUsuario(nombre, usuario, password);

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body().status) {
                    Toast.makeText(CrearUsuarioActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CrearUsuarioActivity.this, "El usuario ya existe o hubo un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(CrearUsuarioActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

