package com.idnp.lab08_recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.idnp.lab08_recyclerview.R;
import com.idnp.lab08_recyclerview.db.DbUsers;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtTelefono, txtDni, txtCorreoElectronico;
    Button btnGuarda;
    private static final String TAG = "NuevoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        getSupportActionBar().hide();
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDni = findViewById(R.id.txtDni);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNombres.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {

                    DbUsers dbUsuarios = new DbUsers(NuevoActivity.this);
                    long id = dbUsuarios.agregarUsuario(txtNombres.getText().toString(),txtApellidos.getText().toString(), txtTelefono.getText().toString(),txtDni.getText().toString(), txtCorreoElectronico.getText().toString());

                    if (id > 0) {
                        Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO ", Toast.LENGTH_LONG).show();
                        limpiar();
                        regreso();
                    } else {
                        Log.d(TAG,Long.toString(id));
                        Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO" + Long.toString(id), Toast.LENGTH_LONG).show();
                        Log.d(TAG,Long.toString(id));
                    }
                } else {
                    Toast.makeText(NuevoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void regreso(){
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("ID", id);
        startActivity(intent);
    }

    private void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtDni.setText("");
        txtCorreoElectronico.setText("");
    }
}