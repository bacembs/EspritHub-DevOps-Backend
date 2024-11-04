package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.esprithub.entities.Article;


import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteListRepository/* extends JpaRepository<FavoriteList, Long>*/ {

   /* @Query("SELECT DISTINCT f.article.category FROM FavoriteList f WHERE f.user.userId = :userId")
    List<String> findFavoriteCategoriesByUserId(@Param("userId") Long userId);

    Optional<FavoriteList> findByUserUserId(Long userId);*/


}
