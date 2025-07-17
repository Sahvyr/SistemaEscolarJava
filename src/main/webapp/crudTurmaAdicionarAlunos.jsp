<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Aluno"%>


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

<!-- Meu CSS  -->
<link rel="stylesheet" href="css/indexADM.css">
<link rel="stylesheet" href="css/crud.css">
</head>

<%
Aluno aluno = (Aluno) request.getAttribute("aluno");

@SuppressWarnings("unchecked")
List<Aluno> alunosAdicionados = (List<Aluno>) request.getSession().getAttribute("alunosAdicionados");
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
		<h1 class="mb-4 text-center" >Sistema Escolar</h1>
		<h2 class="mb-4">Registro de Turma</h2>

		<!-- Formulário de Localizar Aluno -->
		<div class="card bg-midnight border-0 p-4 shadow">
			<h5>Localizar Aluno</h5>
			<form action="SvTurmaLocalizarAluno" method="get" class="d-flex">
				<input type="text" class="form-control me-2 input-dark" maxlength="11" placeholder="Digite o CPF do Aluno" name="cpfProcurado" id="ilocalizarAluno" required>
				<button type="submit" class="btn btn-purple">Localizar</button>
			</form>
		</div>

		<!-- Exibição do aluno localizado -->
		<%
		if (aluno != null) {
		%>
		<div class="card bg-midnight border-0 p-3 mt-4 shadow">
			<h5>Aluno Localizado</h5>
			<form action="SvTurmaAdicionarAlunos" method="get" class="d-flex align-items-center justify-content-between">
				<div class="d-flex flex-grow-1 gap-4 align-items-center">
					<div>
						<strong>Nome:</strong>
						<%=aluno.getNome()%></div>
					<div>
						<strong>CPF:</strong>
						<%=aluno.getCpf()%></div>

					<div>
						<strong>RG:</strong>
						<%=aluno.getRg()%></div>

					<div>
						<strong>Telefone:</strong>
						<%=aluno.getTelefone()%></div>

					<div>
						<strong>Matricula:</strong>
						<%=aluno.getMatricula()%></div>

				</div>
				<input type="hidden" name="cpfAluno" value="<%=aluno.getCpf()%>">
				<button type="submit" class="btn btn-success">Adicionar</button>
			</form>
		</div>
		<%
		} else if (msg != null) {
		%>
		<div class="alert alert-warning mt-3"><%=msg%></div>
		<%
		}
		%>

		<!-- Lista de alunos já adicionados -->
		<%
		if (alunosAdicionados != null && !alunosAdicionados.isEmpty()) {
		%>
		<div class="card bg-midnight border-0 p-3 mt-4 shadow">
			<h5>Alunos Adicionados à Turma</h5>
			<table class="table table-dark table-bordered mt-2">
				<thead>
					<tr>
						<th>Nome</th>
						<th>CPF</th>
						<th>RG</th>
						<th>Telefone</th>
						<th>Matrícula</th>
						<th>Remover Aluno</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (int i = 0; i < alunosAdicionados.size(); i++) {
						Aluno a = alunosAdicionados.get(i);
					%>
					<tr>
						<td><%=a.getNome()%></td>
						<td><%=a.getCpf()%></td>
						<td><%=a.getRg()%></td>
						<td><%=a.getTelefone()%></td>
						<td><%=a.getMatricula()%></td>
						<td>
							<!-- Botão de remover aluno -->
							<form action="SvTurmaRemoverAluno" method="get" style="display: inline;">
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
			<!-- Botão Avançar -->
			<form action="crudTurma.jsp" method="get" class="text-center mt-3">
				<button type="submit" class="btn btn-primary">Avançar</button>
			</form>
		</div>
		<%
		}
		%>






	</main>


</body>