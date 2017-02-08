package com.clayton.crud;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {

    private  SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editar);
        db= openOrCreateDatabase("crud.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);

        final Intent it = getIntent();
        final EditText txtNome = (EditText)findViewById(R.id.edt_name);
        final EditText txtEmail = (EditText)findViewById(R.id.edt_email);

        txtNome.setText(it.getStringExtra("nome"));
        txtEmail.setText(it.getStringExtra("email"));

        Button btnApagar = (Button)findViewById(R.id.btn_apagar);
        btnApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (db.delete("clientes", "_id = ?", new String[]{String.valueOf(it.getIntExtra("codigo", 0))})> 0){
                    Toast.makeText(getBaseContext(),"Atualização efetuada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        Button btnAtualizar = (Button)findViewById(R.id.btn_Atualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues ctv = new ContentValues();
                ctv.put("nome", txtNome.getText().toString());
                ctv.put("email",txtEmail.getText().toString());
                if (db.update("clientes", ctv,"_id = ?", new String[]{String.valueOf(it.getIntExtra("codigo", 0))})> 0){
                    Toast.makeText(getBaseContext(),"Atualização efetuada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
