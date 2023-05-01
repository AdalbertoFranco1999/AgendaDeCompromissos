package com.example.agenda;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor; //Navegar entre os registros
import android.content.ContextWrapper;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import static android.content.Context.MODE_PRIVATE;

public class BancoDados {


    static SQLiteDatabase db=null;
    static Cursor cursor;

    public static void abrirBanco(Activity act){
        try{
            ContextWrapper cw=new ContextWrapper(act);
            db=cw.openOrCreateDatabase("bancoAgenda",MODE_PRIVATE,null);
        }catch (Exception ex){
            Msg.mostrar("Erro ao abrir ou criar Banco de Dados",act);
        }finally {

        }
    }



    public static void abrirTabela(Activity act){
        try{


            db.execSQL("CREATE TABLE IF NOT EXISTS agenda (id INTEGER PRIMARY KEY,local TEXT,titulo TEXT,descricao TEXT, datas DATE, horas TIME);");
        }catch(Exception ex){
            Msg.mostrar(ex.getMessage(),act);
        }
    }

    public static void fecharDB(){
        db.close();
    }


    public static void inserirRegistro(String local,String titulo, String descricao, String data,String horas, Activity act){

        abrirBanco(act);

        try {
            // Formatando a data para o formato do banco de dados
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yy/MM/dd");
            String dataFormatada = "";
            try {
                Date date = sdfInput.parse(data);
                dataFormatada = sdfOutput.format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            db.execSQL("INSERT INTO agenda(local,titulo,descricao,datas,horas) VALUES ('" + local + "','" + titulo + "','" + descricao + "','" + dataFormatada + "','" + horas + "')");
        } catch (Exception ex) {
            Msg.mostrar(ex.getMessage(), act);
        } finally {
            Msg.mostrar("Registro inserido", act);

        }
        fecharDB();
    }

    public static Cursor buscarDados(Activity act) {
        fecharDB();
        abrirBanco(act);

        cursor = db.query("agenda",
                new String[]{"local", "titulo", "descricao", "datas", "horas"},
                null,
                null,
                null,
                null,
                "datas ASC",
                null
        );
        cursor.moveToFirst();
        return cursor;
    }
}