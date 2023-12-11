package com.bingoTrome.Model;

import com.bingoTrome.Enum.EnumDiaSemana;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name = "numerosobtenidos")
public class Numero {
	@Id
	private int id ;
	private int numero ;
	private Integer dia_semana;
	
	@ManyToOne
	@JoinColumn(name="dia_semana" , insertable=false, updatable= false)
	private DiaSemana objtDiaSemana ;
}
