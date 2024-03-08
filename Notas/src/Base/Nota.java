package Base;

import java.util.Date;

/**
 *
 * @author eduardo
 */
public class Nota {
    private int cod;
    private String titulo;
    private String texto;
    private Date dataCriacao;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nota{");
        sb.append("cod=").append(cod);
        sb.append(", titulo=").append(titulo);
        sb.append(", texto=").append(texto);
        sb.append(", dataCriacao=").append(dataCriacao);
        sb.append('}');
        return sb.toString();
    }
    
}
