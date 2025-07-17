package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Turma;

import java.io.IOException;

@WebServlet("/SvExcluirTurma")
public class SvExcluirTurma extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvExcluirTurma() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Recupera o parâmetro e verifica se ele não é nulo ou vazio
		String nomeTurma = request.getParameter("nomeDaTurma");

		if (nomeTurma != null && !nomeTurma.trim().isEmpty()) {
			nomeTurma = nomeTurma.toLowerCase();
		}

		Turma turma = new Turma();

		// Localiza a turma
		if (turma.localizarTurma(nomeTurma)) {
			System.out.println("turma localizada: " + turma.getNome());
			// Exclui a turma, se possível
			if (turma.excluirTurma()) {
				request.setAttribute("status", "excluirEmCurso");
				request.setAttribute("msg", turma.getMsg()); // Mensagem de sucesso ou erro
			} else {
				request.setAttribute("status", "turmaEncontrada"); // mantém a turma visível no JSP
				request.setAttribute("msg", turma.getMsg()); // mostra mensagem de erro
				request.setAttribute("turma", turma); // mantém a turma carregada
			}

		} else {
			// Se a turma não for encontrada, exibe a mensagem
			request.setAttribute("msg", turma.getMsg());
		}

		System.out.println("status: " + request.getAttribute("msg"));
		// Redireciona de volta para a página de CRUD da turma
		request.getRequestDispatcher("visualizarTurma.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
