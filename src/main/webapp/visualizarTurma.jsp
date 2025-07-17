<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Professor"%>
<%@ page import="model.Turma"%>
<%@ page import="model.Aluno"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin - Sistema Escolar</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<!-- Google Font: Montserrat -->
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">

<!-- Meu CSS -->
<link rel="stylesheet" href="css/indexADM.css">
<link rel="stylesheet" href="css/crud.css">
</head>

<%
Professor professor = (Professor) request.getAttribute("professor");
Turma turma = (Turma) request.getSession().getAttribute("turma");
String status = (String) request.getAttribute("status");
String msg = (String) request.getAttribute("msg");
%>

<body>
	<!-- Barra Lateral -->
	<nav class="bg-midnight p-4 vh-100 d-flex flex-column" style="width: 250px;">
		<div>
			<h2 class="text-purple mb-4">
				<i class="bi bi-person-gear me-1"></i> Admin
			</h2>

			<ul class="nav flex-column gap-2">
				<li class="nav-item"><a class="nav-link text-white" href="indexADM.jsp"> <i class="bi bi-house-door me-2"></i> Início
				</a></li>
				<li class="nav-item"><a class="nav-link text-white" href="crudProfessor.jsp"> <i class="bi bi-person-lines-fill me-2"></i> Professores
				</a></li>
				<li class="nav-item"><a class="nav-link text-white" href="crudAluno.jsp"> <i class="bi bi-person-badge me-2"></i> Alunos
				</a></li>
				<!-- Dropdown de Turmas -->
				<li class="nav-item dropdown"><a class="nav-link dropdown-toggle text-white" href="#" id="turmasDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i class="bi bi-house-door me-2"></i> Turmas
				</a>
					<ul class="dropdown-menu bg-dark" aria-labelledby="turmasDropdown">
						<li><a class="dropdown-item text-white" href="visualizarTurma.jsp">Visualizar Turmas</a></li>
						<li><a class="dropdown-item text-white" href="crudTurmaLocalizarProfessor.jsp">Cadastrar Turmas</a></li>
					</ul></li>
				<li class="nav-item mt-5"><a href="SvLogout" class="nav-link text-danger"> <i class="bi bi-door-closed me-2"></i> Sair
				</a></li>
			</ul>
		</div>

		<!-- Rodapé da barra lateral -->
		<div class="mt-auto text-center text-secondary small pt-4">&copy; 2025 @Sistema Escolar</div>
	</nav>

	<main>

		<!-- Mensagem de sucesso ou erro após a edição -->
		<%
		String statusSessao = (String) session.getAttribute("status");
		String msgSessao = (String) session.getAttribute("msg");

		if ("editadoComSucesso".equals(statusSessao) && msgSessao != null) {
		%>
		<div class="alert alert-success alert-dismissible fade show mx-4 mt-3" role="alert">
			<%=msgSessao%>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%
		// Limpar os atributos após exibir
		session.removeAttribute("status");
		session.removeAttribute("msg");
		}
		%>

		<h1 class="mb-4 text-center">Sistema Escolar</h1>
		<h2 class="mb-4">Localizar Turma</h2>

		<!-- Formulário de Localizar Professor -->
		<div class="card bg-midnight border-0 p-4 shadow">
			<h5>Localizar Turma</h5>
			<form action="SvLocalizarTurma" method="get" class="d-flex">
				<input type="text" class="form-control me-2 input-dark" maxlength="11" placeholder="Digite o nome da turma" name="nomeProcurado" id="ilocalizarTurma" required>
				<button type="submit" class="btn btn-purple">Localizar</button>
			</form>
		</div>

		<%
		if (status != null && status.equals("turmaEncontrada")) {
			Turma turmaEncontrada = (Turma) request.getAttribute("turma");
		%>
		<div class="card bg-dark text-white mt-4 p-4 shadow">
			<h5>Turma Localizada</h5>
			<p>
				<strong>Nome:</strong>
				<%=turma.getNome()%></p>
			<p>
				<strong>Turno:</strong>
				<%=turma.getTurno()%></p>
			<p>
				<strong>Matéria:</strong>
				<%=turma.getDisciplina()%></p>
			<p>
				<strong>Professor:</strong>
				<%=turma.getProfessor().getNome()%></p>
			<p>
				<strong>Alunos:</strong>
			</p>

			<%
			if (turma != null && turma.getAlunos() != null) {
				List<Aluno> listaAlunos = turma.getAlunos();
				if (!listaAlunos.isEmpty()) {
			%>
			<table class="table table-dark table-striped table-bordered mt-3">
				<thead>
					<tr>
						<th>#</th>
						<th>Nome</th>
						<th>CPF</th>
						<th>RG</th>
						<th>Telefone</th>
						<th>Remover Aluno</th>

					</tr>
				</thead>
				<tbody>
					<%
					int i = 1;
					for (int j = 0; j < listaAlunos.size(); j++) {
						Aluno a = listaAlunos.get(j);
					%>
					<tr>
						<td><%=i++%></td>
						<td><%=a.getNome()%></td>
						<td><%=a.getCpf()%></td>
						<td><%=a.getRg()%></td>
						<td><%=a.getTelefone()%></td>

						<td>
							<!-- Botão de remover aluno -->
							<form action="SvRemoverAlunoLista" method="get" style="display: inline;">
								<input type="hidden" name="cpfAluno" value="<%=a.getCpf()%>">
								<button type="submit" class="btn btn-danger btn-sm">Remover</button>
							</form>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>

			</table>
			<%
			} else {
			%>
			<p class="text-warning">Nenhum aluno cadastrado para esta turma.</p>
			<%
			}
			} else {
			%>
			<p class="text-danger">Turma não encontrada ou sem alunos.</p>
			<%
			}
			%>

			<!-- Botões -->

			<%
			if (msg != null) {
			%>
			<p class="text-warning text-center fw-semibold mt-3"><%=msg%></p>
			<%
			}
			%>



			<div class="mt-3 d-flex justify-content-center gap-2">

				<form action="SvExcluirTurma" method="get">
					<input type="hidden" name="nomeDaTurma" value="<%=turma.getNome()%>">
					<button type="submit" class="btn btn-danger ">
						<i class="bi bi-trash"></i> Excluir
					</button>
				</form>

				<form action="editarTurma.jsp" method="get">
					<input type="hidden" name="nomeDaTurma" value="<%=turma.getNome()%>">
					<button type="submit" class="btn btn-warning">
						<i class="bi bi-pencil-square"></i> Editar
					</button>
				</form>
			</div>
		</div>
		<%
		} else if (msg != null) {
		%>
		<div class="alert alert-warning mt-4"><%=msg%></div>
		<%
		}
		%>


	</main>

</body>
</html>
