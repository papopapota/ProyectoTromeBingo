package com.bingoTrome.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_Detalle_Bingo")
@Data
public class DetalleBingo {
	@Id
	private int id ;
	private String idBingo ;
	private String B ;
	private String I ;
	private String N ;
	private String G ;
	private String O ;
}
