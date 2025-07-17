<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Professor"%>
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
Professor professor = (Professor) request.getSession().getAttribute("professor");
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

		<h1 class="mb-4 text-center">Sistema Escolar</h1>
		<h2 class="mb-4">Registro de Turma</h2>

		<!-- Formulário de Localizar Professor -->
		<div class="card bg-midnight border-0 p-4 shadow">
			<h5>Localizar Professor</h5>
			<form action="SvTurmaLocalizarProfessor" method="get" class="d-flex">
				<input type="text" class="form-control me-2 input-dark" maxlength="11" placeholder="Digite o CPF do Professor" name="cpfProcurado" id="ilocalizarProfessor" required>
				<button type="submit" class="btn btn-purple">Localizar</button>
			</form>
		</div>

		<!-- Mensagem de sucesso ou erro após cadastro -->
		<%
		String statusSessao = (String) session.getAttribute("status");
		String msgSessao = (String) session.getAttribute("msg");

		if ("cadastrado".equals(statusSessao) && msgSessao != null) {
		%>
		<div class="alert alert-success alert-dismissible fade show mx-4 mt-3 text-center fw-bold" role="alert">
			<%=msgSessao%>
			<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		</div>
		<%
		// Limpar os atributos após exibir
		session.removeAttribute("status");
		session.removeAttribute("msg");
		}
		%>

		<!-- Exibição de mensagem de erro caso o professor não seja encontrado -->
		<%
		if ("semProfessor".equals(status)) {
		%>
		<p class="alert alert-danger mt-3"><%=msg != null ? msg : "Professor não encontrado. Tente novamente."%></p>
		<%
		}
		%>

		<%
		if ("professorComTurma".equals(status)) {
		%>
		<p class="alert alert-danger mt-3"><%=msg != null ? msg : "Este professor já está associado a uma turma."%></p>
		<%
		}
		%>

		<%
		if ("professorLocalizado".equals(status) && professor != null) {
		%>
		<div class="card bg-midnight border-0 p-4 mt-4 shadow">
			<h5>Professor Encontrado</h5>
			<table class="table table-dark table-bordered">
				<tr>
					<th>Nome</th>
					<td><%=professor.getNome()%></td>
				</tr>
				<tr>
					<th>CPF</th>
					<td><%=professor.getCpf()%></td>
				</tr>
				<tr>
					<th>RG</th>
					<td><%=professor.getRg()%></td>
				</tr>
				<tr>
					<th>Telefone</th>
					<td><%=professor.getTelefone()%></td>
				</tr>
				<tr>
					<th>Especialidade</th>
					<td><%=professor.getEspecialidade()%></td>
				</tr>
				<tr>
					<th>Salário</th>
					<td>R$<%=professor.getSalario()%></td>
				</tr>
			</table>

			<form action="crudTurmaAdicionarAlunos.jsp">
				<div class="d-flex justify-content-center mt-3">
					<button type="submit" class="btn btn-success">Avançar</button>
				</div>
			</form>
		</div>
		<%
		}
		%>
	</main>
</body>
</html>
