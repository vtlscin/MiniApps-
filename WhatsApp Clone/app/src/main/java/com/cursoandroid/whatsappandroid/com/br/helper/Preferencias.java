package com.cursoandroid.whatsappandroid.com.br.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;

public class Preferencias {

    private SharedPreferences preferences;
    private Context contextoRecebido;
    private final String NOME_ARQUIVO = "whatsapp-preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    //private final String CHAVE_NOME = "nome";
    private final String CHAVE_TELEFONE = "telefone";
    private final String CHAVE_TOKEN = "token";

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    public Preferencias(Context contexto) {

        contextoRecebido = contexto;
        preferences = contextoRecebido.getSharedPreferences(NOME_ARQUIVO,0);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token){

        editor.putString(CHAVE_NOME,nome);
        editor.putString(CHAVE_TELEFONE,telefone);
        editor.putString(CHAVE_TOKEN,token);
        editor.commit();

    }

    public HashMap<String,String> getDadosUsuarios(){

        HashMap<String,String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,null));
        dadosUsuario.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE,null));
        dadosUsuario.put(CHAVE_TOKEN,preferences.getString(CHAVE_TOKEN,null));

        return dadosUsuario;
    }

    //Salvando apenas o Nome do usuario

    public void salvarDados(String identificadorUsuario, String nomeUsuario){

        editor.putString(CHAVE_IDENTIFICADOR,identificadorUsuario);
        editor.putString(CHAVE_NOME,nomeUsuario);
        editor.commit();

    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR,null);
    }

    public String getNome(){
        return preferences.getString(CHAVE_NOME,null);
    }

}
