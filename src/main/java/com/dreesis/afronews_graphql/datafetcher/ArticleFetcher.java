package com.dreesis.afronews_graphql.datafetcher;

import com.dreesis.afronews_graphql.connection.CursorUtil;
import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Contact;
import com.dreesis.afronews_graphql.entities.Source;
import com.dreesis.afronews_graphql.service.ArticleService;
import com.dreesis.afronews_graphql.service.ContactService;
import com.dreesis.afronews_graphql.service.SourceService;
import com.netflix.graphql.dgs.*;
import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.List;

@DgsComponent
@Slf4j
public class ArticleFetcher {
    private final ArticleService service;
    private final SourceService sourceService;
    private final ContactService contactService;
    private final CursorUtil cursorUtil;

    public ArticleFetcher(ArticleService service, SourceService sourceService, ContactService contactService, CursorUtil cursorUtil){
        this.service = service;
        this.sourceService = sourceService;
        this.contactService = contactService;
        this.cursorUtil = cursorUtil;
    }
    @DgsQuery(field = "findArticleByCategorie")
    public Connection<Article> findArticleByCategorie(DataFetchingEnvironment env, @InputArgument("categorie") String categorie, @InputArgument("first")  int first, @InputArgument("after")  @Nullable String after){
        log.info("Categorie "+ categorie);
        /*List<Edge<Article>> edgeList = new ArrayList<>();
        findCategorie(categorie, after).stream().limit(first).forEach(
                article -> {
                    edgeList.add(new DefaultEdge<>(article, cursorUtil.createCursorWith(article.getId())));
                }
        );
        var pageInfo = new DefaultPageInfo(
                cursorUtil.getFirstCursorFrom(edgeList),
                cursorUtil.getLastCursorFrom(edgeList),
                after != null,
                edgeList.size() >= first
        );
        return new DefaultConnection<>(edgeList, pageInfo).ge;*/
        List<Article> categ = service.findArticleByCategorie(categorie);
        return new SimpleListConnection<>(categ).get(env);
    }
    public List<Article> findCategorie(String categorie, String cursor){
        if(cursor == null){
            return service.findArticleByCategorie(categorie);
        }
        return service.findArticleByCategorieAfter(cursorUtil.decodeCursorWith(cursor), categorie);
    }

    @DgsQuery(field = "findArticleBySourcePays")
    public Connection<Article> findArticleBySourcePays(DataFetchingEnvironment env, @InputArgument("pays") String pays, @InputArgument("first")  int first, @InputArgument("after")  @Nullable String after){
        log.info("Pays  "+ pays);
        List<Article> articles = service.findArticleBySourcePays(pays);
        return new SimpleListConnection<>(articles).get(env);
    }
    @DgsQuery(field = "findArticleSourceName")
    public Connection<Article> findArticleSourceName(DataFetchingEnvironment env, @InputArgument("nom") String nom, @InputArgument("first")  int first, @InputArgument("after")  @Nullable String after){
        log.info("findArticleSourceName "+ nom);
        List<Article> articles = service.findArticleBySourceName(nom);
        return new SimpleListConnection<>(articles).get(env);
    }

    @DgsQuery(field = "getArticlePreferences")
    public Connection<Article> getArticlePreferences(DataFetchingEnvironment env, @InputArgument("preferences") List<String> preferences, @InputArgument("first")  int first, @InputArgument("after")  @Nullable String after){
        List<Article> articles = service.getArticlePreferences(preferences);
        return new SimpleListConnection<>(articles).get(env);
    }
    @DgsQuery(field = "search")
    public Connection<Article> search(DataFetchingEnvironment env, @InputArgument("recherche") String recherche, @InputArgument("first")  int first, @InputArgument("after")  @Nullable String after){
        List<Article> articles = service.search(recherche);
        return new SimpleListConnection<>(articles).get(env);
    }
    @DgsQuery(field = "getlistSources")
    public List<Source> getlistSources(){
        log.info("getlistSources");
        return sourceService.getlistSources();
    }
    @DgsMutation(field = "sendMessage")
    public Contact sendMessage(@InputArgument("nom") String nom,@InputArgument("email") String email,@InputArgument("message") String message){
        Contact contact = new Contact();
        contact.setNom(nom);
        contact.setEmail(email);
        contact.setMessage(message);
        log.info("contact" + contact);
        this.contactService.save(contact);
        return contact;
    }
}
