package org.springblogricette.springricetta.Repository;

import org.springblogricette.springricetta.Model.Repice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepiceRepository extends JpaRepository<Repice,Integer> {

    List<Repice> findByTitleContainingOrIngredientsContaining (String searchTitle, String searchIngredients);


}
