package Injecao;

import Base.Dados;
/**
 *
 * @author eduardo
 */
public interface Injecao {
    public boolean gravar(String nome,Dados d);
    public Dados ler(String nome);
}
