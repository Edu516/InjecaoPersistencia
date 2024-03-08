package Injecao;

import Base.Dados;
import Injecao.Injecao;

/**
 *
 * @author eduardo
 */
public class Persiste implements Injecao {
    
    private Injecao i;

    public Persiste(Injecao i) {
        this.i = i;
    }

    @Override
    public boolean gravar(String nome,Dados d) {
        return i.gravar(nome,d); 
    }

    @Override
    public Dados ler(String texto) {
        return i.ler(texto);
    }

}
