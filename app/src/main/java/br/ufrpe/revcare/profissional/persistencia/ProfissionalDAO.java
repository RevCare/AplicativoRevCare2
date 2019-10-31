package br.ufrpe.revcare.profissional.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import br.ufrpe.revcare.infra.persistencia.DBHelper;
import br.ufrpe.revcare.profissional.dominio.Profissional;
import static br.ufrpe.revcare.infra.persistencia.DBHelper.COL_EMAIL_PROFISSIONAL;
import static br.ufrpe.revcare.infra.persistencia.DBHelper.COL_SENHA_PROFISSIONAL;
import static br.ufrpe.revcare.infra.persistencia.DBHelper.TABELA_PROFISSIONAL;

public class ProfissionalDAO {

    private DBHelper dbHelper;

    public ProfissionalDAO(Context context) {

        dbHelper = new DBHelper(context);
    }

    public long cadastrar(Profissional profissional){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COL_NOME_PROFISSIONAL, profissional.getNome());
        values.put(DBHelper.COL_CPF_PROFISSIONAL, profissional.getCpf());
        values.put(DBHelper.COL_NASCIMENTO_PROFISSIONAL, profissional.getDataNascimento());
        values.put(DBHelper.COL_ENDERECO_PROFISSIONAL, profissional.getEndereco());
        values.put(COL_EMAIL_PROFISSIONAL, profissional.getEmail());
        values.put(DBHelper.COL_TELEFONE_PROFISSIONAL, profissional.getTelefone());
        values.put(DBHelper.COL_CERTIFICADO, profissional.getCertificado());
        values.put(COL_SENHA_PROFISSIONAL, profissional.getSenha());

        long id = db.insert(TABELA_PROFISSIONAL, null, values);
        db.close();
        return id;

    }
    public Profissional consultar(String email, String senha){
        Profissional result = null;
        String query =
                " SELECT * " +
                        " FROM " + TABELA_PROFISSIONAL +
                        " WHERE " + COL_EMAIL_PROFISSIONAL + " = ? " +
                        " AND " + COL_SENHA_PROFISSIONAL + " = ? ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email,senha});
        if (cursor.moveToFirst()){
            result = criarProfissional(cursor);
        }
        return result;
    }


    public Profissional consultar(String email) {
        Profissional result = null;
        String query =
                " SELECT * " +
                        " FROM " + TABELA_PROFISSIONAL +
                        " WHERE " + COL_EMAIL_PROFISSIONAL + " = ? ";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.moveToFirst()){
            result = criarProfissional(cursor);
        }
        return result;
    }

    private Profissional criarProfissional(Cursor cursor) {
        Profissional result = new Profissional();
        result.setId(cursor.getInt(0));
        result.setNome(cursor.getString(1));
        result.setTelefone(cursor.getString(3));
        result.setDataNascimento(cursor.getString(2));
        result.setEndereco(cursor.getString(6));
        result.setCpf(cursor.getString(5));
        result.setEmail(cursor.getString(4));
        result.setCertificado(cursor.getString(7));
        result.setSenha(cursor.getString(8));

        return result;
    }
}
