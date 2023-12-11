package com.bingoTrome.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bingoTrome.Model.DetalleBingo;

public interface IDetalleBingoRepository extends JpaRepository<DetalleBingo, Integer>  {
	public List<DetalleBingo> findAllByIdBingo(String idBingo);
}
