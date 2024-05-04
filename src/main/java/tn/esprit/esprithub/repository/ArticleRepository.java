package tn.esprit.esprithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.esprithub.entities.Article;
import tn.esprit.esprithub.entities.Mycategory;
import tn.esprit.esprithub.entities.Mycondition;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<Article> findByCategoryArticle(Mycategory categoryArticle);
    List<Article> findByConditionArticle(Mycondition conditionArticle);

    List<Article> findByCategoryArticleAndConditionArticle(Mycategory categoryArticle,Mycondition conditionArticle);

    @Query(value = "SELECT MAX(priceArticle) FROM Article")
    Float findMaxPrice();

    @Query(value = "SELECT Min(priceArticle) FROM Article")
    Float findMinPrice();
    @Query("SELECT a FROM Article a WHERE a.priceArticle = :price")
    List<Article> findByPriceArticle(@Param("price") Float price);
   /*@Query("SELECT a FROM Article a WHERE a.priceArticle >= :minPrice AND a.priceArticle <= :maxPrice")
   List<Article> findByPriceArticle(@Param("minPrice") Float minPrice, @Param("maxPrice") Float maxPrice);*/




    List <Article> findByCategoryArticleAndConditionArticleAndPriceArticle(Mycategory categoryArticle, Mycondition conditionArticle, Float priceArticle);
    @Query("SELECT a FROM Article a JOIN FETCH a.users")
    List<Article> findAllWithUsers();
    @Query("SELECT a.articleId, a.categoryArticle, a.conditionArticle, a.imgArticle, a.descriptionArticle, a.priceArticle, a.users.userId, u.username FROM Article a JOIN a.users u")
    List<Object[]> findAllArticlesWithUserIdsAndUsernames();






    @Query("SELECT a.categoryArticle, COUNT(a) FROM Article a GROUP BY a.categoryArticle")
    List<Object[]> countArticlesByCategory();


    @Query("SELECT a.conditionArticle, COUNT(a) FROM Article a GROUP BY a.conditionArticle")
    List<Object[]> countByCondition();


    @Query("SELECT a.categoryArticle, a.conditionArticle, COUNT(a) FROM Article a GROUP BY a.categoryArticle, a.conditionArticle")
    List<Object[]> countByCategoryAndCondition();









}

