package com.cursoandroid.whatsappandroid.com.br.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity,String[] permissoes){

        if (Build.VERSION.SDK_INT >= 23){

            List<String> listapermissoes = new ArrayList<String>();

            //Percorrer permissoes passadas e verificar uma a uma se ja tem a permissao liberada
            for (String permissao : permissoes){
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listapermissoes.add(permissao);

            }
            //Caso a lista esteja vazia, não é necessario solicitar permissao
            if(listapermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listapermissoes.size()];
            listapermissoes.toArray(novasPermissoes);//Passando elementos da lista para o Array

            //Solicita permissao
            ActivityCompat.requestPermissions(activity,novasPermissoes,requestCode);

        }


        return true;
    }

}
