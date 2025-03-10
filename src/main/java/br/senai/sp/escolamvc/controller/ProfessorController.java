package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping ("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;
    @PostMapping("/buscar")
    public String buscar (Model model, @Param("Nome") String nome) {
        if (nome == null){
            return "redirect:/aluno";
        }
        List<Professor> ProfessoresPesquisados = professorRepository.findProfessorByNomeContaining(nome);
        model.addAttribute("Professores", ProfessoresPesquisados);
        return "/aluno/listagem";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Professor professor, BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()) {
            if(professor.getId() != null){
                return "aluno/alterar";
            }
            return "professor/inserir";
        }
        professorRepository.save(professor);
        attributes.addFlashAttribute("mensagem", "Professor salvo com sucesso!");
        return "redirect:/professor/";
    }

    @GetMapping ("/novo")
    public String novo(Model model){
        model.addAttribute("professor", new Professor());
        return "professor/inserir";
    }

    @GetMapping("/")
    public String listar(Model model){
        model.addAttribute("professores", professorRepository.findAll());
        return "professor/listagem";
    }

    /*
     * Método para excluir um professor
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Professor professor = professorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        professorRepository.delete(professor);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Professor excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/professor/";
    }


    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o professor no banco de dados
        Professor professor = professorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Adiciona o professor no objeto model para ser carregado no formulário
        model.addAttribute("professor", professor);

        // Retorna o template professor/alterar.html
        return "professor/alterar";
    }




}
