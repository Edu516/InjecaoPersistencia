package Base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eduardo
 */
public class Dados {
    private List<Nota> notas = new ArrayList<>();

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public boolean addNota(Nota nota) {
        return notas.add(nota);
    }

    public boolean removerNota(Nota nota) {
        return notas.remove(nota);
    }

    public boolean editarNota(Nota notaAntiga, Nota notaNova) {
        int i = notas.indexOf(notaAntiga);
        if (i != -1) {
            notas.set(i, notaNova);
            return true;
        }
        return false;
    }
}
