package cl.rdrp.planilla_shopper.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import cl.rdrp.planilla_shopper.R;

public class ParametrosCalculosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros_calculos);

        setTitle("Parámetros de cálculo");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // habilita el botón atrás
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
