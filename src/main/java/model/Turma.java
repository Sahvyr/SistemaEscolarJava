package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.query.Query;

@Entity(name = "turma")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Turma {
	// MÉTODOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true, nullable = false, length = 50)
	private String nome;

	@Column(nullable = false, length = 100)
	private String disciplina;

	@Column(length = 20)
	private String turno;

	@OneToOne
	@JoinColumn(name = "id_professor", nullable = false, unique = true)
	private Professor professor;

	@OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
	private List<Aluno> alunos = new ArrayList<Aluno>();

	@Transient
	private String msg;

	// CONSTRUTORES

	public Turma() {

	}

	public Turma(long id, String nome, String disciplina, String turno) {
		super();
		this.id = id;
		this.nome = nome;
		this.disciplina = disciplina;
		this.turno = turno;
	}

	public Turma(String nome, String disciplina, String turno) {
		super();
		this.nome = nome;
		this.disciplina = disciplina;
		this.turno = turno;
	}

	public Turma(String nome, String disciplina, String turno, Professor professor, List<Aluno> alunos) {
		super();
		this.nome = nome;
		this.disciplina = disciplina;
		this.turno = turno;
		this.professor = professor;
		this.alunos = alunos;
	}

	public Turma(long id, String nome, String disciplina, String turno, Professor professor, List<Aluno> alunos) {
		super();
		this.id = id;
		this.nome = nome;
		this.disciplina = disciplina;
		this.turno = turno;
		this.professor = professor;
		this.alunos = alunos;
	}

	// MÉTODOS

	// Validação dos campos da Turma
	public boolean validarCampos() {
		if (nome == null || nome.trim().isEmpty()) {
			setMsg("O NOME DA TURMA É OBRIGATÓRIO.");
			return false;
		}
		if (disciplina == null || disciplina.trim().isEmpty()) {
			setMsg("A DISCIPLINA É OBRIGATÓRIA.");
			return false;
		}
		if (turno == null || turno.trim().isEmpty()) {
			setMsg("O TURNO É OBRIGATÓRIO.");
			return false;
		}
		if (professor == null) {
			setMsg("A TURMA DEVE TER UM PROFESSOR.");
			return false;
		}
		return true;
	}

	public boolean verificarSeProfessorJaTemTurma(long professorId) {

		try (Session session = HibernateUtil.abrirSession()) {
			if (session == null) {
				setMsg("ERRO AO ABRIR SESSÃO COM O BANCO.");
				return false;
			}

			String hql = "SELECT COUNT(*) FROM turma t WHERE t.professor.id = :professorId";

			try {
				Long count = (Long) session.createQuery(hql).setParameter("professorId", professorId).getSingleResult();
				System.out.println("count resultado: " + count);
				return count > 0;
			} catch (Exception e) {
				System.out.println("Erro na execução da consulta: " + e.getMessage());
				setMsg("ERRO NA CONSULTA: " + e.getMessage());
				return false;
			}
		} catch (Exception e) {
			setMsg("ERRO NA CONSULTA: " + e.getMessage());
			return false; // Caso ocorra um erro na consulta
		}
	}

	public boolean salvar() {
		if (!validarCampos()) {
			return false;
		}

		try (Session session = HibernateUtil.abrirSession()) {
			if (session == null) {
				setMsg("ERRO AO ABRIR SESSÃO COM O BANCO.");
				return false;
			}

			// Verifica se já existe uma turma com o mesmo nome
			String hql = "FROM turma WHERE nome = :nome";
			Query<Turma> query = session.createQuery(hql, Turma.class);
			query.setParameter("nome", this.nome.toLowerCase());
			List<Turma> turmasExistentes = query.list();

			if (!turmasExistentes.isEmpty()) {
				setMsg("JÁ EXISTE UMA TURMA COM ESSE NOME.");
				return false;
			}

			try {
				session.beginTransaction();
				session.save(this);
				session.getTransaction().commit();
				setMsg("TURMA CADASTRADA COM SUCESSO!");
				return true;
			} catch (Exception e) {
				setMsg("ERRO AO SALVAR TURMA: " + e.getMessage());
				return false;
			}
		} catch (Exception e) {
			setMsg("ERRO AO SALVAR OS DADOS: " + e.getMessage());
			return false;
		}
	}

	public boolean excluirTurma() {
		try {
			// Abrir a sessão do Hibernate
			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			try {
				session.beginTransaction();

				// Recarregar a turma completa com os alunos associados
				Turma turmaParaExcluir = session.get(Turma.class, this.getId());

				if (turmaParaExcluir == null) {
					this.setMsg("TURMA NÃO ENCONTRADA.");
					session.getTransaction().rollback();
					return false;
				}

				// Verificar se há alunos associados
				if (turmaParaExcluir.getAlunos() != null && !turmaParaExcluir.getAlunos().isEmpty()) {
					this.setMsg("A TURMA NÃO PODE SER EXCLUÍDA, POIS HÁ ALUNOS ASSOCIADOS A ELA.");
					session.getTransaction().rollback();
					return false;
				}

				// Excluir a turma
				session.delete(turmaParaExcluir);
				session.getTransaction().commit();
				this.setMsg("TURMA EXCLUÍDA COM SUCESSO!");
				return true;

			} catch (Exception e) {
				session.getTransaction().rollback();
				this.setMsg("ERRO AO TENTAR EXCLUIR A TURMA: " + e.getMessage());
				return false;

			} finally {
				HibernateUtil.fecharSession();
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO: " + e.getMessage());
			return false;
		}
	}

	public boolean removerAluno(Aluno aluno) {
		try {
			if (aluno == null || this.getAlunos() == null) {
				this.setMsg("ALUNO INVÁLIDO OU LISTA VAZIA.");
				return false;
			}

			List<Aluno> lista = this.getAlunos();
			Aluno alunoParaRemover = null;

			// Percorre a lista com índice tradicional
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getId() == aluno.getId()) {
					alunoParaRemover = lista.get(i);
					break;
				}
			}

			if (alunoParaRemover != null) {
				lista.remove(alunoParaRemover);

				// Abre a sessão do Hibernate
				Session session = HibernateUtil.abrirSession();
				if (session == null) {
					this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
					return false;
				}

				try {
					session.beginTransaction();
					session.update(this); // Atualiza a turma no banco com a nova lista de alunos
					session.getTransaction().commit();
					HibernateUtil.fecharSession();

					this.setMsg("ALUNO REMOVIDO DA TURMA.");
					return true;
				} catch (Exception e) {
					HibernateUtil.fecharSession();
					this.setMsg("ERRO AO REMOVER ALUNO: " + e.getMessage());
					return false;
				}

			} else {
				this.setMsg("ALUNO NÃO ENCONTRADO NA TURMA.");
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO: " + e.getMessage());
			return false;
		}
	}

	public boolean localizarTurma(String nomeLocalizado) {
		try {
			if (nomeLocalizado == null || nomeLocalizado.trim().isEmpty()) {
				this.setMsg("NÃO FOI POSSÍVEL LOCALIZAR: O NOME ESTÁ NULO OU VAZIO.");
				return false;
			}

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			String sql = "SELECT * FROM turma WHERE nome = :nome";
			Query<Turma> query = session.createNativeQuery(sql, Turma.class);
			query.setParameter("nome", nomeLocalizado.toLowerCase());
			query.setMaxResults(1);

			Turma t = query.uniqueResult();

			if (t != null) {
				// Força o carregamento da lista de alunos antes de fechar a sessão
				t.getAlunos().size();

				// Agora pode fechar a sessão com segurança
				session.close();

				// Preenche os dados da turma atual
				this.setId(t.getId());
				this.setNome(t.getNome());
				this.setTurno(t.getTurno());
				this.setDisciplina(t.getDisciplina());
				this.setProfessor(t.getProfessor());
				this.setAlunos(t.getAlunos());

				this.setMsg("TURMA LOCALIZADA COM SUCESSO!");
				return true;
			} else {
				session.close();
				this.setMsg("NENHUMA TURMA LOCALIZADA COM ESSE NOME.");
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO AO LOCALIZAR TURMA: " + e.getMessage());
			return false;
		}
	}

	public boolean localizarTurma(Long idProcurado) {
		try {

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			String sql = "SELECT * FROM turma WHERE id = :id";
			Query<Turma> query = session.createNativeQuery(sql, Turma.class);
			query.setParameter("id", idProcurado);
			query.setMaxResults(1);

			Turma t = query.uniqueResult();

			if (t != null) {
				// Força o carregamento da lista de alunos antes de fechar a sessão
				t.getAlunos().size();

				// Agora pode fechar a sessão com segurança
				session.close();

				// Preenche os dados da turma atual
				this.setId(t.getId());
				this.setNome(t.getNome());
				this.setTurno(t.getTurno());
				this.setDisciplina(t.getDisciplina());
				this.setProfessor(t.getProfessor());
				this.setAlunos(t.getAlunos());

				this.setMsg("TURMA LOCALIZADA COM SUCESSO!");
				return true;
			} else {
				session.close();
				this.setMsg("NENHUMA TURMA LOCALIZADA COM ESSE NOME.");
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO AO LOCALIZAR TURMA: " + e.getMessage());
			return false;
		}
	}

	public boolean editarTurma() {
		try {
			if (!validarCampos())
				return false;

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			// Verifica se já existe uma turma com o mesmo nome
			String hql = "FROM turma WHERE nome = :nome";
			Query<Turma> query = session.createQuery(hql, Turma.class);
			query.setParameter("nome", this.nome.toLowerCase());
			List<Turma> turmasExistentes = query.list();

			if (!turmasExistentes.isEmpty()) {
				setMsg("JÁ EXISTE UMA TURMA COM ESSE NOME.");
				return false;
			}

			try {
				session.beginTransaction();
				session.update(this); // Atualiza o professor com base no ID
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
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
