<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	page import="model.Aluno"%>

<!DOCTYPE html>
<html lang="pt-BR">
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
String status = (String) request.getAttribute("status");
String msg = (String) request.getAttribute("msg");
%>



<body class="bg-dark-blue text-white">
	<div class="d-flex">

		<!-- Barra Lateral -->
		<nav class="bg-midnight p-4 vh-100 d-flex flex-column" style="width: 250px;">
			<div>
				<h2 class="text-purple mb-4">
					<i class="bi bi-person-gear me-1"></i> Admin
				</h2>

				<ul class="nav flex-column gap-2">
					<!-- Link para voltar ao índice -->
					<li class="nav-item"><a class="nav-link text-white" href="indexADM.jsp"> <i class="bi bi-house-door me-2"></i> Início
					</a></li>

					<!-- Link para a página de Professores -->
					<li class="nav-item"><a class="nav-link text-white" href="crudProfessor.jsp"> <i class="bi bi-person-lines-fill me-2"></i> Professores
					</a></li>

					<!-- Link para a página de Alunos -->
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

		<!-- Área Principal -->
		<main class="flex-grow-1 p-5" style="margin-left: 250px;">
			<h1 class="mb-4 text-center" >Sistema Escolar</h1>
			<h2 class="mb-4">Área do Aluno</h2>

			<!-- Formulário de Localizar Aluno -->
			<div class="card bg-midnight border-0 p-4 shadow">
				<h5>Localizar Aluno</h5>
				<form action="SvLocalizarAluno" method="get" class="d-flex">
					<input type="text" class="form-control me-2 input-dark <%=status != null ? (status.equals("alunoLocalizado") ? "border-success" : (status.equals("semAluno") ? "border-danger" : "")) : ""%>" maxlength="11" placeholder="Digite o CPF do Aluno"
						name="cpfProcurado" id="ilocalizarAluno" required>
					<button type="submit" class="btn btn-purple">Localizar</button>
				</form>
			</div>

			<!-- Mensagem de Localização -->
			<%
			if ("alunoLocalizado".equals(status)) {
			%>
			<div class="alert alert-success mt-3">
				<%=msg%>
			</div>
			<%
			} else if ("semAluno".equals(status)) {
			%>
			<div class="alert alert-danger mt-3">
				<%=msg%>
			</div>
			<%
			}
			%>

			<hr class="my-4">


			<!-- Formulário de Cadastro / Edição de Aluno -->
			<div class="card bg-midnight border-0 p-4 shadow">
				<h5>Cadastrar ou Editar Aluno</h5>

				<!-- Formulário para Cadastro ou Edição -->
				<form method="get">

					<!-- Campo oculto para o ID -->
					<input type="hidden" id="iid" name="id" value="<%=aluno != null ? aluno.getId() : ""%>">

					<!-- Campo de Nome -->
					<div class="mb-3">
						<label for="inome" class="form-label">Nome</label>
						<input type="text" class="form-control input-dark" id="inome" name="nome" maxlength="100" value="<%=aluno != null ? aluno.getNome() : ""%>" required>
					</div>

					<!-- Campo de CPF -->
					<div class="mb-3">
						<label for="icpf" class="form-label">CPF</label>
						<input type="text" class="form-control input-dark" id="icpf" name="cpf" maxlength="11" value="<%=aluno != null ? aluno.getCpf() : ""%>" required>
					</div>

					<!-- Campo de RG -->
					<div class="mb-3">
						<label for="irg" class="form-label">RG</label>
						<input type="text" class="form-control input-dark" id="irg" name="rg" maxlength="9" value="<%=aluno != null ? aluno.getRg() : ""%>" required>
					</div>

					<!-- Campo de Telefone -->
					<div class="mb-3">
						<label for="itelefone" class="form-label">Telefone</label>
						<input type="tel" class="form-control input-dark" id="itelefone" name="telefone" maxlength="11" value="<%=aluno != null ? aluno.getTelefone() : ""%>" required>
					</div>

					<!-- Campo de Senha -->
					<div class="mb-3">
						<label for="isenha" class="form-label">Senha</label>
						<input type="password" class="form-control input-dark" id="isenha" name="senha" value="<%=aluno != null ? aluno.getSenha() : ""%>" maxlength="30" required>
					</div>

					<!-- Campo de Matrícula -->
					<div class="mb-3">
						<label for="imatricula" class="form-label">Matrícula</label>
						<input type="text" class="form-control input-dark" id="imatricula" name="matricula" name="matricula" value="<%=aluno != null ? aluno.getMatricula() : ""%>" required>
					</div>

					<!-- Área de Mensagens de Cadastrar, Editar e Excluir -->
					<%
					if (msg != null && !msg.isEmpty() && !"alunoLocalizado".equals(status) && !"semAluno".equals(status)) {
					%>
					<div class="alert alert-<%=("erro".equals(status) ? "danger" : "success")%>">
						<%=msg%>
					</div>
					<%
					}
					%>

					<div class="d-flex justify-content-between mt-4">
						<%
						boolean alunoCarregado = aluno != null && aluno.getId() != 0;

						boolean desativarCadastrar = alunoCarregado; // se já tem aluno com ID válido, não pode cadastrar
						boolean desativarEditarExcluir = !alunoCarregado; // se não tem aluno com ID válido, não pode editar/excluir
						%>


						<button type="submit" class="btn btn-purple w-25" name="action" formaction="SvCadastrarAluno" value="cadastrar" <%=desativarCadastrar ? "disabled" : ""%>>Cadastrar</button>

						<button type="submit" class="btn btn-warning w-25" name="action" formaction="SvEditarAluno" value="editar" <%=desativarEditarExcluir ? "disabled" : ""%>>Editar</button>

						<button type="submit" class="btn btn-danger w-25" name="action" formaction="SvExcluirAluno" value="excluir" <%=desativarEditarExcluir ? "disabled" : ""%>>Excluir</button>


					</div>

				</form>
			</div>
		</main>
	</div>
</body>
</html>
