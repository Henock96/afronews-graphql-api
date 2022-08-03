package com.dreesis.afronews_graphql;

import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.service.ArticleService;
import com.dreesis.afronews_graphql.utils.ExtractionSource;
import com.dreesis.afronews_graphql.utils.ExtractionSource2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AfronewsGraphqlApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(AfronewsGraphqlApplication.class, args);
    }

    final ArticleService articleService;

    public AfronewsGraphqlApplication(ArticleService articleService) {
        this.articleService = articleService;
    }
    @Override
    public void run(String... args) throws Exception {
        /*List<Article> articleList = ExtractionSource.getJournalDeBrazza();
        //List<Article> articles = ExtractionSource.getLinfodrome2();
        //List<Article> articlesG = ExtractionSource.getGabonReview();
        List<Article> articles1 = ExtractionSource.getAdiacCongo();
        List<Article> articles2 = ExtractionSource.getMedia24();
        List<Article> articles3 = ExtractionSource.getDakarActu();
        //List<Article> articles4 = ExtractionSource.getScidev();
        List<Article> articles6 = ExtractionSource.getAfrikfoot();
        //**List<Article> articles5 = ExtractionSource.getSenego();
        //**List<Article> articles7 = ExtractionSource.getAfrimag();
        List<Article> articles8 = ExtractionSource.getAfriqueitnews();
        List<Article> articles9 = ExtractionSource.getAfriquelatribuneEconomie();
        List<Article> articles10 = ExtractionSource.getAfriquelatribunePolitique();
        List<Article> articles11 = ExtractionSource.getAfriquelatribuneTech();
        List<Article> articles12= ExtractionSource.getAfriquelatribuneTechTelecoms();
        //List<Article> articles13 = ExtractionSource.getAps();
        List<Article> articles14= ExtractionSource.getJeuneAfrique();
        List<Article> articles15= ExtractionSource.getTelquel();
        List<Article> articles16 = ExtractionSource.getKoaciExtract();
        List<Article> articles17= ExtractionSource.getLesEchosExtraction();
        List<Article> articles01 = ExtractionSource2.getGalsen221();
        List<Article> articles02 = ExtractionSource2.get7sur7CD();
        List<Article> articles03 = ExtractionSource2.getActualiteCD();
        List<Article> articles04 = ExtractionSource2.getPolitico();
        List<Article> articles05 = ExtractionSource2.getRadioOkapi();
        List<Article> articles06 = ExtractionSource2.getSeneweb();
        articleService.saveAllArticles(articles01);
        articleService.saveAllArticles(articles02);
        articleService.saveAllArticles(articles03);
        articleService.saveAllArticles(articles04);
        articleService.saveAllArticles(articles05);
        articleService.saveAllArticles(articles06);

        //articles01.forEach(System.out::println);
        articleService.saveAllArticles(articleList);
        //articleService.saveAllArticles(articles);
        //articleService.saveAllArticles(articlesG);
        articleService.saveAllArticles(articles1);
        //articleService.saveAllArticles(articles2);
        articleService.saveAllArticles(articles3);
        //articleService.saveAllArticles(articles4);
        articleService.saveAllArticles(articles15);
        articleService.saveAllArticles(articles6);
        articleService.saveAllArticles(articles17);
        articleService.saveAllArticles(articles8);
        articleService.saveAllArticles(articles9);
        articleService.saveAllArticles(articles10);
        articleService.saveAllArticles(articles11);
        articleService.saveAllArticles(articles12);
        //articleService.saveAllArticles(articles13);
        articleService.saveAllArticles(articles14);
        articleService.saveAllArticles(articles16);*/
        //List<Article> articleMosaic = ExtractionSource2.getGuineeNews();
        //articleMosaic.forEach(System.out::println);




        //articles.forEach(System.out::println);
    }
}
