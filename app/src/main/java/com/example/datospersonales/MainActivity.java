package com.example.datospersonales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNotificar, btnReset;
    ImageButton btnSpain, btnUK;
    Spinner spinner;
    String[] estados;
    private Locale locale;
    private final Configuration config = new Configuration();
    TextView nombre, apellidos, numero, etiqueta;
    RadioButton hombre, mujer;
    Switch hijos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNotificar = findViewById(R.id.btnNotificar);
        btnNotificar.setOnClickListener(this);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        spinner = (Spinner)findViewById(R.id.selectEstado);
        estados = getResources().getStringArray(R.array.estados);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnSpain = findViewById(R.id.btnSpain);
        btnSpain.setOnClickListener(this);
        btnUK = findViewById(R.id.btnUK);
        btnUK.setOnClickListener(this);

        nombre = findViewById(R.id.txtNombre);
        apellidos = findViewById(R.id.txtApellidos);
        numero = findViewById(R.id.txtNumero);
        hombre = findViewById(R.id.radioHombre);
        mujer = findViewById(R.id.radioMujer);
        hijos = findViewById(R.id.switchHijos);
        hijos.setOnClickListener(this);

        etiqueta = findViewById(R.id.lblEtiqueta);
        etiqueta.setGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnSpain)){
            cambiarIdioma("es");
        }
        if(v.equals(btnUK)){
            cambiarIdioma("en");
        }
        if(v.equals(hijos)){
            textoHijos();
        }
        if(v.equals(btnReset)){
            vaciar();
        }
        if(v.equals(btnNotificar)){
            notificar();
        }
    }

    private void textoHijos() {
        if(hijos.isChecked()==true){
            hijos.setText(getResources().getString(R.string.si));
        } else {
            hijos.setText(getResources().getString(R.string.no));
        }
    }

    private void notificar() {
        String res = "";
        String edad = "";
        String padre = "";
        String genero = "";
        if(nombre.getText().length()==0){
            res = "El nombre no puede estar vacío";
        } else if (apellidos.getText().length()==0){
            res ="El apellido no puede estar vacío";
        } else if(numero.getText().length()==0){
            res = "La edad no puede estar vacía";
        } else if(hombre.isChecked()==false && mujer.isChecked()==false){
            res = "El genero no puede estar sin seleccionar";
        } else if(spinner.getSelectedItem().equals(getResources().getStringArray(R.array.estadosMostrar)[0])){
            res = "El estado civil no puede estar sin seleccionar";
        } else {
            if(Integer.parseInt(String.valueOf(numero.getText()))>=18) {
                edad = "mayor de edad";
            } else {
                edad = "menor de edad";
            }
            if(hijos.getText().equals(getResources().getString(R.string.no))) {
                padre = "sin hijos";
            } else {
                padre = "con hijos";
            }
            if(hombre.isChecked()==true){
                genero = "hombre";
            } else {
                genero = "mujer";
            }
            res = apellidos.getText() + ", " + nombre.getText() + ", " + edad + ", " + genero
                    + " " + spinner.getSelectedItem() + " y " + padre;
            Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            res = "";
        }
        etiqueta.setText(res);
        etiqueta.setTextColor(getResources().getColor(R.color.red));
    }

    private void vaciar() {
        nombre.setText("");
        apellidos.setText("");
        numero.setText("");
        hombre.setChecked(false);
        mujer.setChecked(false);
        spinner.setSelection(0);
        hijos.setChecked(false);
        hijos.setText(getResources().getString(R.string.no));
    }

    private void cambiarIdioma(String idioma) {
        locale = new Locale(idioma);
        config.locale = locale;
        getResources().updateConfiguration(config, null);
        Intent refresh = new Intent(MainActivity.this, MainActivity.class);
        startActivity(refresh);
        finish();
    }


}