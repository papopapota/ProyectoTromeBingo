package com.bingoTrome.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "tb_dias_Semana")
@Data
@Entity
public class DiaSemana {
	@Id
	 private int  id ;
	 private String dia_semana ;
}
