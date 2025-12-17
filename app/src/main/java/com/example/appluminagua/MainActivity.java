package com.example.appluminagua;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appluminagua.api.ApiService;
import com.example.appluminagua.api.RetrofitClient;
import com.example.appluminagua.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editUsuario, editPassword;
    Button btnLogin, btnCrearUsuario;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        editUsuario = findViewById(R.id.editUsuario);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);

        btnLogin.setOnClickListener(v -> iniciarSesion());

        btnCrearUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrearUsuarioActivity.class);
            startActivity(intent);
        });
    }

    private void iniciarSesion() {
        String usuario = editUsuario.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<LoginResponse> call = apiService.login(usuario, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MenuReportesActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
