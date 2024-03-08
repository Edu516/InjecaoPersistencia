package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author eduardo
 */
public class ConexaoPostgres {
    public static Connection conectar() throws SQLException {
        String url     = "jdbc:postgresql://localhost:5432/faculdade";
        String usuario = "postgres";
        String senha   = "postgres";

        return DriverManager.getConnection(url, usuario, senha);
    }
}
