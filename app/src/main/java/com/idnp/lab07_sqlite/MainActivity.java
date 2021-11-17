package com.idnp.lab07_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.idnp.lab07_sqlite.db.DbUsers;
import com.idnp.lab07_sqlite.db.DbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.idnp.lab07_sqlite.adaptadores.ListaUsuariosAdapter;
import com.idnp.lab07_sqlite.entidades.Usuarios;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaUsuarios;
    ArrayList<Usuarios> listaArrayContactos;
    Button fabNuevo;
    ListaUsuariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaUsuarios = findViewById(R.id.listaUsuarios);
        fabNuevo = findViewById(R.id.favNuevo);
        listaUsuarios.setLayoutManager(new LinearLayoutManager(this));

        DbUsers DbUsers = new DbUsers(MainActivity.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListaUsuariosAdapter(DbUsers.mostrarUsuarios());
        listaUsuarios.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}