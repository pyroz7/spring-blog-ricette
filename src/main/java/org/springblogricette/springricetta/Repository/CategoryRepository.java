package org.springblogricette.springricetta.Repository;


import org.springblogricette.springricetta.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
