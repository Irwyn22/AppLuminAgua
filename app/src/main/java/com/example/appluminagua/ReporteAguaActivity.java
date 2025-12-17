package com.example.appluminagua;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appluminagua.api.ApiService;
import com.example.appluminagua.api.RetrofitClient;
import com.example.appluminagua.models.ReporteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReporteAguaActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 100;

    EditText editNombre, editCalle1, editCalle2, editColonia, editCP, editDescripcion;
    Button btnFoto, btnGuardar;
    ImageView imagePreview;
    Uri imageUri = null;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_agua);

        // INICIALIZAR RETROFIT  <<<<<< MUY IMPORTANTE
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Referencias
        editNombre = findViewById(R.id.editNombre);
        editCalle1 = findViewById(R.id.editCalle1);
        editCalle2 = findViewById(R.id.editCalle2);
        editColonia = findViewById(R.id.editColonia);
        editCP = findViewById(R.id.editCP);
        editDescripcion = findViewById(R.id.editDescripcion);
        btnFoto = findViewById(R.id.btnFoto);
        imagePreview = findViewById(R.id.imagePreview);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Abrir galería
        btnFoto.setOnClickListener(v -> openGallery());

        // Guardar reporte vía API
        btnGuardar.setOnClickListener(v -> enviarReporte());
    }

    private void enviarReporte() {

        String nombre = editNombre.getText().toString();
        String calle1 = editCalle1.getText().toString();
        String calle2 = editCalle2.getText().toString();
        String colonia = editColonia.getText().toString();
        String cp = editCP.getText().toString();
        String descripcion = editDescripcion.getText().toString();

        String imagen = (imageUri != null) ? imageUri.toString() : "";

        Call<ReporteResponse> call = apiService.enviarReporte(
                nombre,
                calle1,
                calle2,
                colonia,
                cp,
                descripcion,
                imagen
        );

        call.enqueue(new Callback<ReporteResponse>() {
            @Override
            public void onResponse(Call<ReporteResponse> call, Response<ReporteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ReporteAguaActivity.this, "Reporte enviado correctamente", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ReporteAguaActivity.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteResponse> call, Throwable t) {
                Toast.makeText(ReporteAguaActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imagePreview.setImageURI(imageUri);
        }
    }
}