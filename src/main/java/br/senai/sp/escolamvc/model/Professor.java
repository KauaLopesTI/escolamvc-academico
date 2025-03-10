package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue(value = "P")
@Setter
@Getter
@Entity
public class Professor extends Pessoa{

}
