package Injecao;

import Base.Dados;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author eduardo
 */
public class Gson implements Injecao {

    @Override
    public boolean gravar(String nome,Dados d) {
        if(nome.length()<1){
            nome =  "Notas";
        }
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            FileWriter writer = new FileWriter(nome+".json");
            writer.write(gson.toJson(d));
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Dados ler(String texto) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
          if(texto.length()<1){
            texto =  "Notas";
        }

        try {
            builder = new GsonBuilder();
            gson = builder.create();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(texto+".json"));

            // Corrigir o Type para Dados
            Type dataType = new TypeToken<Dados>() {
            }.getType();

            Dados dados = gson.fromJson(bufferedReader, dataType);
            return dados;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
