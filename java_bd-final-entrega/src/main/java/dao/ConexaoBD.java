package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/EmpresaDeReciclagem";
    private static final String USER = "root";
    private static final String PASSWORD = "Beca2285064*"; // senha MySQL

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD); // conexão driver e com o banco
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null; // caso não conectar com o banco, retorna uma mensagem de erro e encerra o programa
        }
    }
}
