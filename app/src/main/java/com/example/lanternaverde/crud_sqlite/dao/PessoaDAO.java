package com.example.lanternaverde.crud_sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lanternaverde.crud_sqlite.model.Pessoa;

import java.util.ArrayList;

/**
 * Created by Lanterna Verde on 27/08/2017.
 * classe que vai fazer todas as interações com o banco de dados
 */

public class PessoaDAO extends SQLiteOpenHelper {
    //dados do banco:
    private static final String NOME_BANCO = "dbPessoa.db";
    private static final int VERSAO = 4;
    private static final String TABELA = "pessoa";

    //colunas da tabela:
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String ENDERECO = "endereco";
    private static final String CPF = "cpf";
    private static final String TELEFONE = "telefone";

    //metodo construtor:
    public PessoaDAO(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criando a estrutura do banco de dados
        String sql = "CREATE TABLE "+TABELA+" ( " +
                " "+ID+" INTEGER PRIMARY KEY, "+
                " "+NOME+" TEXT, "+ENDERECO+" TEXT, "+CPF+" TEXT, "+ TELEFONE+" TEXT);";
        //executando o SQL
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //caso haja atualizacao no banco de dados
        String sql = "DROP TABLE IF EXISTS "+TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    //salvando novo registro no banco de dados
    public long salvarCadastro(Pessoa p){
        ContentValues values = new ContentValues(); //vai armazenar os dados do objeto
        long retornoDB;

        values.put(NOME, p.getNome());
        values.put(ENDERECO, p.getEndereco());
        values.put(CPF, p.getCpf());
        values.put(TELEFONE, p.getTelefone());

        retornoDB = getWritableDatabase().insert(TABELA, null, values); //fazendo o insert
        return retornoDB; //retorna se deu certo ou nao a execucao
    }

    //atualizando registro no banco de dados
    public long atualizarCadastro(Pessoa p){
        ContentValues values = new ContentValues(); //vai armazenar os dados do objeto
        long retornoDB;

        values.put(NOME, p.getNome());
        values.put(ENDERECO, p.getEndereco());
        values.put(CPF, p.getCpf());
        values.put(TELEFONE, p.getTelefone());

        String[] argumentos = {String.valueOf(p.getId())}; //coletando o ID em questão
        retornoDB = getWritableDatabase().update(TABELA, values, ID+"=?",argumentos); //fazendo o update onde o id= a alguma coisa
        return retornoDB; //retorna se deu certo ou nao a execucao
    }

    //atualizando registro no banco de dados
    public long removerRegistro(Pessoa p){
        long retornoDB;

        String[] argumentos = {String.valueOf(p.getId())}; //coletando o ID em questão
        retornoDB = getWritableDatabase().delete(TABELA, ID+"=?",argumentos);
        return retornoDB; //retorna se deu certo ou nao a execucao
    }

    //fazedo select *
    public ArrayList<Pessoa> selectAllPessoas(){
        String[] colunas = {ID, NOME, ENDERECO,CPF,TELEFONE};
        Cursor cursor = getWritableDatabase().query(TABELA, colunas, null,null,null,null,"nome ASC");

        //array que vai armazenar os registros
        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();

        //enquanto houver registros, adiciona a pessoa a listagem
        while (cursor.moveToNext()){
            Pessoa pessoa = new Pessoa();
            pessoa.setId(cursor.getInt(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setEndereco(cursor.getString(2));
            pessoa.setCpf(cursor.getString(3));
            pessoa.setTelefone(cursor.getString(4));
            //adicionando o elemento completo na listagem:
            listPessoa.add(pessoa);
        }
        return listPessoa; //volta a listagem completa
    }
}
