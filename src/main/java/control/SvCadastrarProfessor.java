package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;

import java.io.IOException;

@WebServlet("/SvCadastrarProfessor")
public class SvCadastrarProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvCadastrarProfessor() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");
		String rg = request.getParameter("rg");
		String telefone = request.getParameter("telefone");
		String senha = request.getParameter("senha");
		String especialidade = request.getParameter("especialidade");
		double salario = Double.parseDouble(request.getParameter("salario"));

		Professor x = new Professor(nome, cpf, rg, telefone, senha, especialidade, salario);

		if (x.salvar()) {
			request.setAttribute("status", "cadastrado");
			request.setAttribute("msg", x.getMsg());
		} else {
			request.setAttribute("professor", x);
			request.setAttribute("status", "erro");
			request.setAttribute("msg", x.getMsg()); 
		}

		request.getRequestDispatcher("crudProfessor.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
