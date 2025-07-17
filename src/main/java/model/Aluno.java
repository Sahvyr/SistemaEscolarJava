package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import org.hibernate.Session;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.query.Query;

@Entity(name = "aluno")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Aluno extends Pessoa {

	// ATRIBUTO
	@Column(unique = true, length = 20)
	private String matricula;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_turma")
	private Turma turma; // Cada aluno pertence a uma única turma

	// CONSTRUTORES

	public Aluno() {
		super();
	}

	public Aluno(String nome, String cpf, String rg, String telefone, String senha, String matricula, Turma turma) {
		super(nome, cpf, rg, telefone, senha);
		this.matricula = matricula;
		this.turma = turma;
	}

	public Aluno(long id, String nome, String cpf, String rg, String telefone, String senha, String matricula, Turma turma) {
		super(id, nome, cpf, rg, telefone, senha);
		this.matricula = matricula;
		this.turma = turma;
	}

	public Aluno(long id, String nome, String cpf, String rg, String telefone, String senha, String matricula) {
		super(id, nome, cpf, rg, telefone, senha);
		this.matricula = matricula;
	}

	public Aluno(String nome, String cpf, String rg, String telefone, String senha, String matricula) {
		super(nome, cpf, rg, telefone, senha);
		this.matricula = matricula;
	}

	// MÉTODOS
	public boolean verificarCaracteresInvalidosMatricula(String matricula) {
		String caracteresInvalidos = ",./?°!@#$%¨&*()_-+=`´{}[]";
		matricula = matricula.trim();

		for (int i = 0; i < matricula.length(); i++) {
			char c = matricula.charAt(i);
			if (caracteresInvalidos.indexOf(c) != -1) {
				return false;
			}
		}

		return true;
	}

	private boolean validarCampos() {
		if (getNome() == null || getNome().isEmpty()) {
			this.setMsg("NOME INVÁLIDO: CAMPO VAZIO.");
			return false;
		}
		if (!verificarCaracteresInvalidosNome(getNome())) {
			this.setMsg("NOME INVÁLIDO: CONTÉM CARACTERES INVÁLIDOS.");
			return false;
		}
		if (getCpf() == null || getCpf().isEmpty()) {
			this.setMsg("CPF INVÁLIDO: CAMPO VAZIO.");
			return false;
		}
		if (getCpf().length() != 11) {
			this.setMsg("CPF INVÁLIDO: DEVE TER 11 CARACTERES.");
			return false;
		}
		if (!verificarCaracteresInvalidosCpfRg(getCpf())) {
			this.setMsg("CPF INVÁLIDO: FORMATO INCORRETO.");
			return false;
		}
		if (getRg() == null || getRg().isEmpty()) {
			this.setMsg("RG INVÁLIDO: CAMPO VAZIO.");
			return false;
		}
		if (!verificarCaracteresInvalidosCpfRg(getRg())) {
			this.setMsg("RG INVÁLIDO: FORMATO INCORRETO.");
			return false;
		}
		if (getTelefone() == null || getTelefone().isEmpty()) {
			this.setTelefone("----");
		}

		if (getSenha() == null || getSenha().isEmpty()) {
			this.setMsg("SENHA INVÁLIDA: CAMPO VAZIO.");
			return false;
		}
		if (getMatricula() == null || getMatricula().isEmpty()) {
			this.setMsg("MATRÍCULA INVÁLIDA: CAMPO VAZIO.");
			return false;
		}
		if (!verificarCaracteresInvalidosMatricula(getMatricula())) {
			this.setMsg("MATRÍCULA INVÁLIDA: CARACTERES INVÁLIDOS.");
			return false;
		}

		return true;
	}

	public boolean salvar() {

		try {
			if (!validarCampos())
				return false;

			setNome(tratarNome(getNome()));

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO:" + HibernateUtil.erro);
				return false;
			}

			try {
				session.beginTransaction();

				if (this.turma != null && this.turma.getId() == 0) {
					session.save(this.turma); // Salva a turma primeiro, caso ela ainda não tenha id
				}

				session.save(this); // Salva o aluno com o id_turma correto

				session.getTransaction().commit();
				HibernateUtil.fecharSession();
				this.setMsg("CADASTRO REALIZADO COM SUCESSO!");
				return true;

			} catch (Exception e) {
				HibernateUtil.fecharSession();
				this.setMsg("ERRO AO TENTAR SALVAR:" + e.getMessage());

				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO:" + e.getMessage());
		}

		return false;
	}

	public boolean localizarAluno(String cpfLocalizado) {

		try {
			if (cpfLocalizado == null || cpfLocalizado.isEmpty()) {
				this.setMsg("NÃO FOI POSSIVEL LOCALIZAR: SEU CPF ESTÁ NULO OU VAZIO");
				return false;
			} else if (cpfLocalizado.length() != 11) {
				this.setMsg("NÃO FOI POSSIVEL LOCALIZAR:SEU CPF NÃO CONTÉM 11 CARACTERES");
				return false;
			} else if (!verificarCaracteresInvalidosCpfRg(cpfLocalizado)) {
				this.setMsg("NÃO FOI POSSIVEL LOCALIZAR: SEU CPF CONTÉM CARACTERES INVÁLIDOS. (FORMATO CORRETO: 00000000000)");
				return false;
			} else {

				Session session = HibernateUtil.abrirSession();
				if (session == null) {
					this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
					return false;
				}

				String sql = "SELECT * FROM aluno WHERE cpf = :cpf";
				Query<Aluno> query = session.createNativeQuery(sql, Aluno.class);
				query.setParameter("cpf", cpfLocalizado);

				query.setMaxResults(1);
				Aluno aluno = (Aluno) query.uniqueResult();

				if (aluno != null) {
					this.setId(aluno.getId());
					this.setNome(aluno.getNome());
					this.setCpf(aluno.getCpf());
					this.setRg(aluno.getRg());
					this.setTelefone(aluno.getTelefone());
					this.setMatricula(aluno.getMatricula());
					this.setSenha(aluno.getSenha());

					this.setMsg("ALUNO LOCALIZADO COM SUCESSO!");
					return true;
				} else {
					this.setMsg("NENHUM ALUNO LOCALIZADO COM ESTE CPF.");
					return false;
				}
			}

		} catch (Exception e) {

			this.setMsg("ERRO AO LOCALIZAR ALUNO: " + e.getMessage());
			return false;
		}

	}

	public boolean excluirAluno() {

		try {
			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			try {
				session.beginTransaction();

				// Desvincula o aluno da turma antes de excluir
				if (this.turma != null) {
					this.turma = session.get(Turma.class, this.turma.getId());

					this.turma.getAlunos().remove(this); // Remover o aluno da lista de alunos da turma
					session.update(this.turma); // Atualiza a turma no banco de dados
				}

				// Exclui o aluno
				session.delete(this);
				session.getTransaction().commit();
				HibernateUtil.fecharSession();
				this.setMsg("ALUNO EXCLUÍDO COM SUCESSO!");
				return true;

			} catch (Exception e) {
				HibernateUtil.fecharSession();
				this.setMsg("ERRO AO TENTAR EXCLUIR ALUNO: " + e.getMessage());
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO: " + e.getMessage());
			return false;
		}
	}

	public boolean editarAluno() {
		try {
			if (!validarCampos())
				return false;

			setNome(tratarNome(getNome()));

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			try {
				session.beginTransaction();
				session.update(this);
				session.getTransaction().commit();
				HibernateUtil.fecharSession();
				this.setMsg("DADOS ATUALIZADOS COM SUCESSO!");
				return true;
			} catch (Exception e) {
				HibernateUtil.fecharSession();
				this.setMsg("ERRO AO ATUALIZAR DADOS: " + e.getMessage());
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO: " + e.getMessage());
			return false;
		}
	}

	// GETSET
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

}
