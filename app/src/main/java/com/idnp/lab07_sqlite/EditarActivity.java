package com.idnp.lab07_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.idnp.lab07_sqlite.db.DbUsers;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idnp.lab07_sqlite.entidades.Usuarios;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombres, txtApellidos, txtTelefono,txtDni, txtCorreo;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Usuarios Usuario;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDni = findViewById(R.id.txtDni);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbUsers DbUsers = new DbUsers(EditarActivity.this);
        Usuario = DbUsers.verUsuario(id);

        if (Usuario != null) {
            txtNombres.setText(Usuario.getNombres());
            txtApellidos.setText(Usuario.getApellidos());
            txtTelefono.setText(Usuario.getTelefono());
            txtDni.setText(Usuario.getDni());
            txtCorreo.setText(Usuario.getCorreo_electronico());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombres.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {
                    correcto =  DbUsers.editarUsuario(id, txtNombres.getText().toString(), txtApellidos.getText().toString(), txtTelefono.getText().toString(), txtDni.getText().toString(), txtCorreo.getText().toString());;

                    if(correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, VerActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}