import models.Usuario;
import models.UsuarioDAO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // inserir usuario
        System.out.println("Inserindo novo usuário...");
        boolean inserido = usuarioDAO.inserir("Neymar", "neymarjr@gmail.com", "bruna");
        System.out.println("Usuário inserido? " + inserido);

        // listar os usuarios
        System.out.println("\nListando usuários...");
        List<Usuario> usuarios = usuarioDAO.obterTodos();
        for (Usuario u : usuarios) {
            System.out.println(u);
        }

        // obter um usuário especifico
        System.out.println("\nBuscando usuário com ID = 2...");
        Usuario usuario = usuarioDAO.obter(2);
        if (usuario != null) {
            System.out.println("Encontrado: " + usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }

        // atualizar um usuario
        System.out.println("\nAtualizando usuário com ID = 2...");
        boolean atualizado = usuarioDAO.atualizar("Messi", "messi@email.com", "lionel", 2);
        System.out.println("Atualizado? " + atualizado);

        // remover um usuario
        System.out.println("\nRemovendo usuário com ID = 1...");
        boolean removido = usuarioDAO.remover(1);
        System.out.println("Removido? " + removido);
    }
}
