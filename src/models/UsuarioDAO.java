package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// Importa as variáveis estáticas (BD_URL, BD_USUARIO, etc.) da sua classe Config
import static config.Config.*;

/**
 * Classe que implementa o padrão DAO para a entidade Usuario
 */
public class UsuarioDAO {

    /**
     * Obtém todos os usuários do banco
     */
    public List<Usuario> obterTodos() {
        List<Usuario> resultado = new ArrayList<>();
        String sql = "SELECT id, nome, email, senha FROM usuario";

        // try-with-resources garante que tudo será fechado
        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
                resultado.add(usuario);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();
            // Em caso de erro, retorna a lista vazia (nunca null)
        }
        return resultado;
    }

    /**
     * Obtém um usuário específico pelo ID
     */
    public Usuario obter(int id) {
        Usuario usuario = null;
        String sql = "SELECT id, nome, email, senha FROM usuario WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setSenha(resultSet.getString("senha"));
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return usuario;
    }

    /**
     * Insere um novo usuário no banco
     */
    public boolean inserir(String nome, String email, String senha) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; // Retorna true se 1 linha foi inserida

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza um usuário existente
     */
    public boolean atualizar(String nome, String email, String senha, int id) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);
            preparedStatement.setInt(4, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; // Retorna true se 1 linha foi atualizada

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Remove um usuário do banco
     */
    public boolean remover(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; // Retorna true se 1 linha foi removida

        } catch (SQLException ex) {
            System.err.println("Erro ao remover usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}