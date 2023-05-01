package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import java.text.SimpleDateFormat;

public class TelaConsulta extends AppCompatActivity {

    EditText et_local, et_titulo, et_descricao;
    Button btn_anterior, btn_proximo, btn_voltar, bt_data, bt_hora;

    SQLiteDatabase db=null;

    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consulta);


        et_local=(EditText)findViewById(R.id.et_local_consulta);
        et_titulo=(EditText)findViewById(R.id.et_titulo_consulta);
        et_descricao=(EditText)findViewById(R.id.et_descricao_consulta);

        btn_anterior=(Button) findViewById(R.id.btn_anterior);
        btn_proximo=(Button)findViewById(R.id.btn_proximo);
        btn_voltar=(Button)findViewById(R.id.btn_voltar_consulta);
        bt_data=(Button) findViewById(R.id.btn_data_consulta);
        bt_hora=(Button) findViewById(R.id.btn_hora_consulta);

        cursor = BancoDados.buscarDados(this);

        if(cursor.getCount()!=0){
            mostrarDados();
        }else{
            Msg.mostrar("Nenhum registro encontrado",this);
        }
    }
    public void fechar_tela_consulta(View v){
        this.finish();
    }



    public void proximoRegistro(View v) {
        try {
            cursor.moveToNext();
            mostrarDados();
        } catch (Exception ex) {
            if (cursor.isAfterLast()) {
                Msg.mostrar("Não existe mais registros",this);

            } else {
                Msg.mostrar("Erro ao navegar entre registros",this);
            }
        }
    }
    public void registroAnterior(View v){
        try {
            cursor.moveToPrevious();
            mostrarDados();
        } catch (Exception ex) {
            if (cursor.isBeforeFirst()) {
                Msg.mostrar("Não existe mais registros",this);

            } else {
                Msg.mostrar("Erro ao navegar entre registros",this);
            }
        }
    }

    private String formatarData(String data) {
        try {
            SimpleDateFormat sdfInput = new SimpleDateFormat("yy/MM/dd");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yy");
            Date date = sdfInput.parse(data);
            return sdfOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void mostrarDados(){


        et_local.setText(cursor.getString(0));
        et_titulo.setText(cursor.getString(1));
        et_descricao.setText(cursor.getString(2));
        //bt_data.setText(cursor.getString(3));
        String data = cursor.getString(3);
        String dataFormatada = formatarData(data); // Formata a data
        bt_data.setText(dataFormatada);
        bt_hora.setText(cursor.getString(4));
    }
}