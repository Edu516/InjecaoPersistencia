package Injecao;

import Base.Dados;
import Base.Nota;
import Conexao.ConexaoPostgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author eduardo
 */
public class Postgres implements Injecao {

    @Override
    public boolean gravar(String nome, Dados dados) {
        try (Connection conexao = ConexaoPostgres.conectar()) {
            String sql = "INSERT INTO tabela_notas (titulo, texto, data_criacao) VALUES (?, ?, ?)";
            
            for (Nota nota : dados.getNotas()) {
                try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                    preparedStatement.setString(1, nota.getTitulo());
                    preparedStatement.setString(2, nota.getTexto());
                    preparedStatement.setDate(3, new java.sql.Date(nota.getDataCriacao().getTime()));

                    int resultado = preparedStatement.executeUpdate();
                    if (resultado <= 0) {
                        return false;
                    }
                }
            }
            return true; 
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    @Override
    public Dados ler(String nome) {
        try (Connection conexao = ConexaoPostgres.conectar()) {
            String sql = "SELECT * FROM tabela_notas WHERE titulo = ?";
            try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                preparedStatement.setString(1, nome);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    Dados dados = new Dados();
                    
                    while (resultSet.next()) {
                        Nota nota = new Nota();
                        nota.setCod(resultSet.getInt("cod"));
                        nota.setTitulo(resultSet.getString("titulo"));
                        nota.setTexto(resultSet.getString("texto"));
                        nota.setDataCriacao(resultSet.getDate("data_criacao"));
                        
                        dados.addNota(nota);
                    }
                    
                    return dados;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
