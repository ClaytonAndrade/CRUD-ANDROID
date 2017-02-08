package com.clayton.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button btnCadastrar = (Button)findViewById(R.id.btn_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                EditText txtNome = (EditText)findViewById(R.id.edt_name);
                EditText txtEmail = (EditText)findViewById(R.id.edt_email);

                SQLiteDatabase db = openOrCreateDatabase("crud.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);

                ContentValues cvt = new ContentValues();
                cvt.put("nome", txtNome.getText().toString());
                cvt.put("email", txtEmail.getText().toString());

                if(db.insert("clientes", "_id", cvt)> 0){
                    Toast.makeText(getBaseContext(),"Cadastro Realizado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}
