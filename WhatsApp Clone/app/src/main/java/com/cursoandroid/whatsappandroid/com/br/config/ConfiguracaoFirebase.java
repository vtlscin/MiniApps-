package com.cursoandroid.whatsappandroid.com.br.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// ultilizando final para nao ser extendida
public final class ConfiguracaoFirebase {

    // ultilizando static para nao alterar o valor da variavel
    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    // ultilizando static para nao precisar instaciar a classe para usar os metodos
    public static DatabaseReference getFirebase(){

        if(referenciaFirebase == null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

}
