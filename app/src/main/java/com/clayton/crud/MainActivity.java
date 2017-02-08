package com.clayton.crud;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private SimpleCursorAdapter adt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criar Banco Dados
        db = openOrCreateDatabase("crud.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        String clientes = "CREATE TABLE IF NOT EXISTs clientes (_id INTEGER PRIMARY KEY autoincrement, " +
                "nome text, email text)";
        db.execSQL(clientes);

        try {
            //Testa a conexão com banco
            /*AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Banco Criado com sucesso! ");
            dlg.setNeutralButton("Ok", null);
            dlg.show();*/
        } catch (SQLException ex) {

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setMessage("Erro ao criar o banco! " + ex.getMessage());
            dlg.setNeutralButton("Ok", null);
            dlg.show();
        }

        Button btnCliente = (Button) findViewById(R.id.btn_cliente);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), CadastroActivity.class));
            }
        });

        final EditText txtBusca = (EditText) findViewById(R.id.txtBuscar);
        txtBusca.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //Preecher Listview
                String[] busca = new String[]{"%" + txtBusca.getText().toString() + "%"};
                Cursor cursor = db.query("clientes", new String[]{"_id", "nome", "email"}, " nome LIKE ?", busca, null, null, "_id ASC", null);
                adt.changeCursor(cursor);

                return false;
            }
        });
    }
        @Override
        public void onResume(){
            super.onResume();

            // Preencher o ListView
            Cursor cursor = db.rawQuery("SELECT _id, nome, email FROM clientes ORDER BY _id ASC", null);
            String[] campos = {"_id", "nome"};
            int[] ids = {R.id.txvID, R.id.txvNOME};

            adt = new SimpleCursorAdapter(getBaseContext(), R.layout.model_clientes, cursor, campos, ids, 0);
            ListView ltwDados = (ListView) findViewById(R.id.ltw_dados);
            ltwDados.setAdapter(adt);
            ltwDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Cursor retornaCursosr = (Cursor) adt.getItem(position);
                    Intent it = new Intent(getBaseContext(), EditarActivity.class);
                    it.putExtra("codigo", retornaCursosr.getInt(0));
                    it.putExtra("nome", retornaCursosr.getString(1));
                    it.putExtra("email", retornaCursosr.getString(2));
                    startActivity(it);
                }
            });
        }
}




