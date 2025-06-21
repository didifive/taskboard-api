package br.dev.zancanela.taskboard.repository;

import br.dev.zancanela.taskboard.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
