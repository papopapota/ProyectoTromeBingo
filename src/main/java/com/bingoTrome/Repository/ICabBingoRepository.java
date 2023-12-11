package com.bingoTrome.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bingoTrome.Model.CabBingo;
import com.bingoTrome.Model.DetalleBingo;

public interface ICabBingoRepository extends JpaRepository<CabBingo, String> {

	public CabBingo findByIdUsu(int idusu );
}
