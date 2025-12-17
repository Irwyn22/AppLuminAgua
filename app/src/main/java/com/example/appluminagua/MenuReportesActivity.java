package com.example.appluminagua;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;


public class MenuReportesActivity extends AppCompatActivity {

    Button btnAgua, btnLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reportes);

        btnAgua = findViewById(R.id.btnAgua);
        btnLuz = findViewById(R.id.btnLuz);

        btnAgua.setOnClickListener(v -> {
            Intent intent = new Intent(MenuReportesActivity.this, ReporteAguaActivity.class);
            startActivity(intent);
        });

        btnLuz.setOnClickListener(v -> {
            Intent intent = new Intent(MenuReportesActivity.this, ReporteLuzActivity.class);
            startActivity(intent);
    });
    }
}
