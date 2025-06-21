package br.dev.zancanela.taskboard.repository;

import br.dev.zancanela.taskboard.entity.Coluna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColunaRepository extends JpaRepository<Coluna, Long> {
    List<Coluna> findAllByBoard(Long boardId);

}
