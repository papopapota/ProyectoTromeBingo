package com.bingoTrome.Model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_Cab_Bingo")
@Data
public class CabBingo {
	@Id
	 @Column(name = "id_bingo")
	private String idBingo ;
	private int idUsu ;
	private boolean estado;
	private Boolean revision;
}
