package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@DiscriminatorValue(value = "A")
@Setter
@Getter
@Entity
public class Aluno extends Pessoa {
    @NotEmpty(message = "O campo matrícula, não pode estar vazio")
    private String matricula;
}



