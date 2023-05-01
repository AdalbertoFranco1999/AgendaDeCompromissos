package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.app.AlertDialog;

import android.widget.*;





public class MainActivity extends AppCompatActivity {

    EditText et_local, et_titulo, et_descricao, et_email, editTextSubject,editTextContent, editTextToEmail;
    Button bt_data, bt_hora, bt_salvar, bt_enviar;
    String horas, data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_local=(EditText) findViewById(R.id.et_local);
        et_titulo=(EditText) findViewById(R.id.et_titulo);
        et_descricao=(EditText) findViewById(R.id.et_descricao);
        et_email=(EditText) findViewById(R.id.et_email);
        bt_data=(Button) findViewById(R.id.bt_data);
        bt_hora=(Button) findViewById(R.id.bt_hora);
        bt_salvar=(Button) findViewById(R.id.bt_salvar);
        bt_enviar=(Button)findViewById(R.id.bt_enviar);

        editTextSubject= findViewById(R.id.et_titulo );
        editTextContent= findViewById(R.id.et_descricao);
        editTextToEmail= findViewById(R.id.et_email);

        BancoDados.abrirBanco(this);
        BancoDados.abrirTabela(this);
        BancoDados.fecharDB();

        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_titulo, et_descricao, et_email;
                et_titulo = editTextSubject.getText().toString();
                et_descricao = editTextContent.getText().toString();
                et_email = editTextToEmail.getText().toString();
                String descricaoData = et_descricao.concat("\n No dia: "+bt_data.getText().toString());
                String descricaoDataHora = descricaoData.concat("\n As: "+bt_hora.getText().toString());
                String descricaoCompleta = descricaoDataHora.concat("\n Local: "+et_local.getText().toString());
                enviarEmail(et_titulo,descricaoCompleta,et_email);
            }
        });


        bt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });

        bt_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });
        bt_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               inserirRegistro(v);
            }
        });
    }

    public void enviarEmail(String et_titulo, String et_descricao, String et_email){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{et_email });
        intent.putExtra(Intent.EXTRA_SUBJECT, et_titulo );
        intent.putExtra(Intent.EXTRA_TEXT, et_descricao );
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Escolha o email:"));
    }
    public void abrir_tela_consulta(View v){
        Intent it_tela_consulta =new Intent(this,TelaConsulta.class);
        startActivity(it_tela_consulta);
    }

    public void inserirRegistro(View v){
        String st_local,st_titulo,st_descricao,st_data,st_horas;
        st_local=et_local.getText().toString();
        st_titulo=et_titulo.getText().toString();
        st_descricao=et_descricao.getText().toString();
        st_data=data;
        st_horas=horas;


        BancoDados.inserirRegistro(st_local,st_titulo,st_descricao,st_data,st_horas, this);

        et_titulo.setText(null);
        et_descricao.setText(null);
        et_local.setText(null);
        bt_data.setText("DATA");
        bt_hora.setText("HORA");
    }

    public void selectTime() {
        final Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        String horaSelecionada = String.format("%02d:%02d", hourOfDay, minute);
                        bt_hora.setText(horaSelecionada);
                        horas = horaSelecionada;
                    }
                }, hora, minuto, true);
        timePickerDialog.show();
    }
    public void selectDate() {
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dataSelecionada = day + "/" + (month + 1) + "/" + year;
                        bt_data.setText(dataSelecionada);

                        data = dataSelecionada;


                    }
                }, ano, mes, dia);
        datePickerDialog.show();
    }
}

