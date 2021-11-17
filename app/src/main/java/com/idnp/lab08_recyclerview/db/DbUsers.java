package com.idnp.lab08_recyclerview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.idnp.lab08_recyclerview.entidades.Usuarios;

import java.util.ArrayList;

public class DbUsers extends DbHelper {

    Context context;

    public DbUsers(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long agregarUsuario(String nombres,String apellidos, String telefono, String dni, String correo_electronico) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombres", nombres);
            values.put("apellidos", apellidos);
            values.put("telefono", telefono);
            values.put("dni", dni);
            values.put("correo_electronico", correo_electronico);

            id = db.insert(TABLE_USUARIOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Usuarios> mostrarUsuarios() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        Usuarios usuario;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " ORDER BY nombres ASC", null);

        if (cursorUsuarios.moveToFirst()) {
            do {
                usuario = new Usuarios();
                usuario.setId(cursorUsuarios.getInt(0));
                usuario.setNombres(cursorUsuarios.getString(1));
                usuario.setApellidos(cursorUsuarios.getString(2));
                usuario.setTelefono(cursorUsuarios.getString(3));
                usuario.setDni(cursorUsuarios.getString(4));
                usuario.setCorreo_electronico(cursorUsuarios.getString(5));
                listaUsuarios.add(usuario);
            } while (cursorUsuarios.moveToNext());
        }

        cursorUsuarios.close();

        return listaUsuarios;
    }

    public Usuarios verUsuario(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Usuarios usuario = null;
        Cursor cursorUsuarios;

        cursorUsuarios = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorUsuarios.moveToFirst()) {
            usuario = new Usuarios();
            usuario.setId(cursorUsuarios.getInt(0));
            usuario.setNombres(cursorUsuarios.getString(1));
            usuario.setApellidos(cursorUsuarios.getString(2));
            usuario.setTelefono(cursorUsuarios.getString(3));
            usuario.setDni(cursorUsuarios.getString(4));
            usuario.setCorreo_electronico(cursorUsuarios.getString(5));
        }

        cursorUsuarios.close();

        return usuario;
    }

    public boolean editarUsuario(int id, String nombres, String apellidos, String telefono, String dni, String correo_electronico) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_USUARIOS + " SET nombres = '"+ nombres+"', apellidos = '"+ apellidos+"', telefono = '"+ telefono+"', dni = '"+ dni+"', correo_electronico = '"+ correo_electronico+"' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarUsuario(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_USUARIOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}