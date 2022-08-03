package com.dreesis.afronews_graphql.utils;

import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.entities.Source;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExtractionSource {
    public static List<Article> getVox(){
        //Vox.cg 12/01/2022
        String url = "https://www.vox.cg/";
        String url1 = "vox.cg";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div#main");
            Elements main = docs.select("div#fullwidth");
            for(Element full : main){
                Elements content = full.select("div.home-widget");
                Elements post = content.select("ul.img-featured");

                for(Element posts : post){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Vox");
                    source.setTitre(title);
                    source.setUrlLogo("https://www.vox.cg/fichiers/2017/02/cropped-voxnewlogo.png");
                    source.setPays("Congo");
                    source.setContact("+242226132172");
                    source.setUrlSource(url1);
                    article.setSource(source);
                    Elements img = posts.select("div.img-featured-posts-image");
                    for(Element urlArt : img){
                        String urlA = urlArt.select("a").attr("href");
                        //System.out.println(urlA);
                        article.setUrlArticle(urlA);
                        Elements urlImg = urlArt.select("a");
                        String UrlImage = urlImg.select("img").attr("src");
                        //System.out.println(UrlImage);
                        article.setUrlImage(UrlImage);
                    }
                    Elements detail = img.select("div.img-featured-title");
                    for(Element details : detail){
                        String titre = details.select("h2").text();
                        //System.out.println(titre);
                        article.setTitre(titre);
                        String dtime = details.select("div.author-date").select("div.date").text();
                        //System.out.println(dtime);
                        article.setCreated(LocalDateTime.now());

                        article.setDatePublication(dtime);
                        liste.add(article);
                    }

                }
            }
            System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getJournalDeBrazza() {
        //JournalDeBrazza 12/01/2022
        String url = "https://www.journaldebrazza.com/";
        String url1 = "journaldebrazza.com";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.main");
            Elements main = docs.select("div.column-left");
            Elements content = main.select("ul.list-post");
            for(Element post: content){
                Elements medium = post.select("li");
                for(Element news : medium)
                {
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("JournalDeBrazza");
                    source.setTitre(title);
                    source.setPays("Congo");
                    source.setContact("infos@journaldebrazza.com");
                    source.setUrlLogo("https://www.journaldebrazza.com/wp-content/themes/jdbrazza/assets/img/logo-journal.png");
                    source.setUrlSource(url1);
                    article.setSource(source);
                    Elements posts = news.select("article.medium-post");
                    for(Element block :posts){
                        String urlA = block.select("a").attr("href");
                        //System.out.println(urlA);
                        article.setUrlArticle(urlA);
                        Elements liens = block.select("a").select("figure.post-img");
                        for(Element lien : liens){
                            Categorie categorie = new Categorie();
                            String cat = lien.select("span.post-category").text();
                            //System.out.println(cat);
                            categorie.setNom(cat);
                            article.setCategorie(categorie);
                            String img = lien.select("img").attr("src");
                            //System.out.println(img);
                            article.setUrlImage(img);
                        }
                        String titre = block.select("a").select("h2.post-title").text();
                        //System.out.println(titre);
                        article.setTitre(titre);
                    }
                    Elements dtime = posts.select("span.post-date");
                    for(Element times : dtime){
                        String datime = times.text();
                        //System.out.println(datime);
                        article.setDatePublication(datime);
                    }
                    Elements desc = posts.select("p");
                    for(Element descs : desc){
                        String description = descs.text();
                        //System.out.println(description);
                        article.setDescription(description);
                        article.setCreated(LocalDateTime.now());

                        liste.add(article);
                    }
                }


            }
            //liste.forEach(System.out::println);
        }catch(Exception e){
            log.error("Erreur dans Journal De brazza", e);
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getGabonReview(){
        //Gabon Review Extraction categorieName categorieUrl titre url_article url_img description time
        String url = "https://www.gabonreview.com/";
        String url1 = "gabonreview.com";
        List<Article> allArticle = new ArrayList<Article>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Elements body = doc.select("div#main");

            //System.out.println(body);
            for(Element content : body){
                Elements bloc = body.select("div.column-narrow-1");
                //System.out.println(bloc);
                for(Element categorie : bloc){
                    Elements categ= categorie.select("div.column-wrapper");
                    for(Element category : categ){
                        Elements lienCategory = category.select("h2.heading a");
                        Categorie categorie1 = new Categorie();

                        for(Element lienC : lienCategory ){
                            //Categorie name
                            String[] catelien = lienC.text().split("»");
                            //System.err.println(catelien[0]);
                            categorie1.setNom(catelien[0]);


                            //Categorie lien
                            //System.out.println(lienC.attr("href"));
                        }
                        Elements posts = category.select("div.post");
                        for(Element post : posts){
                            Article article = new Article();
                            Source source = new Source();
                            article.setCategorie(categorie1);
                            source.setNom("Gabon Review");
                            source.setTitre(title);
                            source.setUrlLogo("https://www.gabonreview.com/wp-content/uploads/2020/02/logogabonreview212-1-1.png");
                            source.setPays("Gabon");
                            source.setUrlContact("gabonreview.com/contact");
                            source.setUrlContactFull("https://www.gabonreview.com/contact/");
                            source.setUrlSource(url1);
                            article.setSource(source);
                            Elements titres = post.select("h3 a");
                            for(Element titre: titres){
                                String nameTitre = titre.text();
                                String lienArticle = titre.attr("href");
                                //System.out.println(nameTitre + lienArticle);
                                article.setTitre(nameTitre);
                                article.setUrlArticle(lienArticle);
                                allArticle.add(article);
                            }
                            Elements cover = post.select("div.cover a img");
                            for(Element img : cover){
                                String imageUrl = img.attr("src");
                                //System.out.println(imageUrl);
                                article.setUrlImage(imageUrl);

                            }
                            //Recuperation de la description
                            Element description = post.select("p").get(1);
                            //System.out.println(description.text());
                            Element time = post.select("p").get(3);
                            //System.out.println(time.text());
                            String[] dates = time.before("a").text().split("/");
                            article.setDescription(description.text());
                            article.setCreated(LocalDateTime.now());

                            article.setDatePublication(dates[0]);

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allArticle;
    }

    public static List<Article> getLinfodrome2() throws Exception{
        String url ="https://www.linfodrome.com";
        String url1 ="linfodrome.com";
        List<Article> allArticle = new ArrayList<Article>();

        try {
            Document dc = Jsoup.connect(url).get();//.get();
            String titreweb = dc.title();
            Elements bod = dc.select("div.mod-67");
            Elements body = bod.select("div.bottom");
            Elements bottom = body.select("div.uk-child-width-1-1");

            for(Element bot : bottom){
                Elements contnt = bot.select("div.content");
                for(Element content : contnt){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Linfodrome");
                    source.setTitre(titreweb);
                    source.setUrlSource(url1);
                    source.setUrlContact("kiosque.linfodrome.com/contact");
                    source.setUrlContactFull("https://kiosque.linfodrome.com/contact");
                    source.setPays("Côte d'ivoire");
                    article.setSource(source);
                    Elements categorie = content.select("h4.category");
                    for(Element cat : categorie){
                        String cateName = categorie.select("span").text();
                        Categorie categorie1 = new Categorie();
                        categorie1.setNom(cateName);
                        article.setCategorie(categorie1);
                        //System.out.println(cateName);
                    }
                    Elements liens = content.select("a");
                    String lienUrl = liens.attr("href");
                    article.setUrlArticle(url+lienUrl);
                    //System.out.println(lienUrl);
                    for(Element lien : liens){
                        Elements urllien = lien.select("img.classe");
                        for(Element lUrl : urllien){
                            String imgUrl = lUrl.attr("src");
                            article.setUrlImage(imgUrl);
                        }
                        //System.out.println(urllien);

                    }
                    Elements corps = content.select("div.uk-position-relative");
                    for(Element corp : corps){
                        String titre = corp.select("h2.title").text();
                        //System.out.println(titre);
                        article.setTitre(titre);
                        Elements meta = corp.select("div.meta");
                        for(Element dtime : meta){
                            Elements times = dtime.select("div.left");
                            String time = times.select("span.date").text();
                            //System.out.println(time);
                            article.setDatePublication(time);
                            article.setCreated(LocalDateTime.now());

                            allArticle.add(article);
                        }
                    }
                }
            }
//            allArticle.forEach(System.out::println);

        } catch (Exception e) {
            log.error("Erreur dans infodrome", e);
            e.printStackTrace();
        }
        return allArticle;
    }
    public static List<Article> getTelquel(){
        //lequel.ma 22/01/2022
        String url = "https://telquel.ma/";
        String url4 = "telquel.ma";
        List<Article> articleList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("section.section");
            Elements main = docs.select("div.container");
            Elements gutter = main.select("div.col-gutter");
            Elements content = gutter.select("ul.articles-list");
            for(Element item : content){
                Elements items = item.select("li.article-normal");
                for(Element thumb : items){
                    Elements post = thumb.select("div.row");
                    for(Element posts : post){
                        Article article = new Article();
                        Source source = new Source();
                        source.setNom("Telquel");
                        source.setTitre(title);
                        source.setPays("Maroc");
                        source.setUrlContact("telquel.ma/contact");
                        source.setUrlContactFull("https://telquel.ma/contact");
                        source.setUrlSource(url4);
                        article.setSource(source);
                        Elements header = posts.select("div.article-header");
                        for(Element heads : header){
                            String urlA = heads.select("a.article-image").attr("href");
                            //System.out.println(urlA);
                            article.setUrlArticle(urlA);
                            Elements img = heads.select("a.article-image");
                            for(Element imgurl : img){
                                String imageurl = imgurl.select("img.wp-post-image").attr("src");
                                //System.out.println(imageurl);
                                article.setUrlImage(imageurl);
                            }

                        }
                        Elements body = posts.select("div.article-content");
                        for(Element bod : body){
                            Elements cat = bod.select("div.article-meta");
                            for(Element meta : cat){
                                String cates = meta.select("a.article-category").text();
                                Categorie categorie = new Categorie();
                                categorie.setNom(cates);
                                article.setCategorie(categorie);
                                //System.out.println(cates);

                            }
                            Elements tit = bod.select("h3.article-heading");
                            for(Element tits : tit){
                                String titres = tits.select("a").text();
                                //System.out.println(titres);
                                article.setTitre(titres);
                                article.setCreated(LocalDateTime.now());

                            }
                            String dtime = bod.select("time.article-publish").text();
                            //System.out.println(dtime);
                            article.setDatePublication(dtime);
                            articleList.add(article);
                        }
                    }
                }
            }
            //articleList.forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }
        return articleList;
    }
    public static List<Article> getScidev(){
        String url = "https://www.scidev.net/afrique-sub-saharienne/";
        String url1 = "scidev.net";
        List<Article> articleList = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.fl-module");
            Elements main = docs.select("div.fl-node-content");
            Elements content = main.select("div.fl-html");
            for(Element full : content){
                Elements list = full.select("div.article-listing");
                for(Element listing : list){
                    Elements post = listing.select("div.article-list");
                    for(Element posts : post){
                        Article article = new Article();
                        Source source = new Source();
                        source.setNom("Scidev");
                        source.setTitre(title);
                        source.setUrlLogo("https://www.scidev.net/wp-content/uploads/2019/02/logo.png");
                        source.setPays("Afrique");
                        source.setUrlContact("scidev.net/global/content/contact-us.html");
                        source.setUrlContactFull("https://www.scidev.net/global/content/contact-us.html");
                        source.setUrlSource(url1);
                        article.setSource(source);
                        Categorie categorie = new Categorie();
                        categorie.setNom("SANTÉ");
                        article.setCategorie(categorie);
                        String lienA = posts.select("a.block-img").attr("href");
                        //System.out.println(lienA);
                        article.setUrlArticle(lienA);
                        Elements img = posts.select("a.block-img");
                        for(Element imgurl : img){
                            String imageUrl = imgurl.select("img").attr("data-src");
                            //System.out.println(imageUrl);
                            article.setUrlImage(imageUrl);
                        }
                        Elements contents = posts.select("div.list-contents");
                        for(Element meta : contents){
                            String titres = meta.select("h3").text();
                            //System.out.println(titres);
                            article.setTitre(titres);
                            String dtime = meta.select("p.caption").get(1).text();
                            //System.out.println(dtime);
                            article.setDatePublication(dtime);
                            article.setCreated(LocalDateTime.now());

                            articleList.add(article);
                        }
                    }
                }
            }
            //articleList.forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }
        return articleList;
    }
    public static List<Article> getMedia24(){
        //https://medias24.com/ 21/01/2022
        String url = "https://medias24.com/";
        String url2 = "medias24.com";
        List<Article> articleList = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).timeout(100 * 1000).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("section.bloc-rubrique");
            Elements main = docs.select("div.hero-section");
            Elements content = main.select("div#classement-list");
            Elements item = content.select("div.items-row");
            for(Element items : item){
                Elements bods = items.select("div#ar");
                for(Element bod: bods){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Medias24");
                    source.setPays("Maroc");
                    source.setTitre(title);
                    source.setUrlContact("medias24.com/nous-contacter");
                    source.setUrlContactFull("https://medias24.com/nous-contacter/");
                    source.setUrlLogo("https://medias24.com/content/themes/medias24/dist/logo/logo.min.new.png?x50244&v=0.0.3");
                    source.setUrlSource(url2);
                    article.setSource(source);
                    Elements bodys = bod.select("article.article-small");
                    for(Element liens : bodys){
                        Elements lienA = liens.select("a").select("figure");
                        for(Element img : lienA){
                            String imageurl = img.select("img.attachment-medium").attr("src");
                            //System.out.println(imageurl);
                            article.setUrlImage(imageurl);
                        }
                    }
                    String urlArticle = bodys.select("a.titleLink").attr("href");
                    article.setUrlArticle(urlArticle);
                    Elements urlA = bodys.select("a.titleLink");
                    for(Element tits : urlA){
                        String titres = tits.select("h2.titre").text();
                        //System.out.println(titres);
                        article.setTitre(titres);
                        article.setCreated(LocalDateTime.now());

                    }
                    Elements desc = bodys.select("a.extraitLink");
                    for(Element descs : desc){
                        String description = descs.select("p.chapo-rub").text();
                        //System.out.println(description);
                        article.setDescription(description);
                        articleList.add(article);
                    }
                }
            }
            //articleList.forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }
        return articleList;
    }
    public static List<Article> getAfrikfoot(){
        //Afrik-foot 21/01/2022
        String url = "https://www.afrik-foot.com/";
        String url2 = "afrik-foot.com";
        List<Article> liste = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.td-main-content-wrap");
            Elements main = docs.select("div.td-container");
            Elements content = main.select("div.td-ss-main-content");
            for(Element tdmain : content){
                //System.out.println(tdmain);
                Elements arts = tdmain.select("div.td_module_10");

                for(Element art : arts){
                    Article article1 = new Article();
                    Source source = new Source();
                    source.setNom("Afrik-Foot");
                    source.setTitre(title);
                    source.setUrlLogo("https://www.afrik-foot.com/wp-content/uploads/2019/02/logo-2.png");
                    source.setPays("Afrique");
                    source.setUrlContact("afrik-foot.com/mentions-legales");
                    source.setUrlContactFull("https://www.afrik-foot.com/mentions-legales");
                    source.setUrlSource(url2);
                    article1.setSource(source);
                    Categorie categorie = new Categorie();
                    categorie.setNom("SPORT");
                    article1.setCategorie(categorie);
                    Elements thumb = art.select("div.td-module-thumb");
                    for(Element lien : thumb){

                        String liens = lien.select("a").attr("href");
                        article1.setUrlArticle(liens);
                        Elements img = lien.select("a");
                        for(Element imglien : img){
                            String urlA = imglien.select("img").attr("data-img-url");
                            //System.out.println(urlA);
                            article1.setUrlImage(urlA);

                        }

                    }
                    Elements details = art.select("div.item-details");
                    for(Element detail : details){
                        String titre = detail.select("h3.entry-title").text();
                        article1.setTitre(titre);
                        article1.setCreated(LocalDateTime.now());

                        //System.out.println(titre);
                    }
                    Elements dtime = details.select("div.td-module-meta-info").select("span.td-post-date");
                    for(Element datetime :dtime){
                        String temp = datetime.select("time").text();
                        //System.out.println(temp);
                        article1.setDatePublication(temp);
                    }
                    Elements desc = details.select("div.td-excerpt");
                    for(Element descpt : desc){
                        String description = descpt.text();
                        //System.out.println(description);
                        article1.setDescription(description);

                    }
                    liste.add(article1);
                }

            }
            //System.out.println(liste);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAfriquelatribuneEconomie(){
        String url = "https://afrique.latribune.fr/economie";
        String url1 = "afrique.latribune.fr";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements content = doc.select("div.row-custom");
            Elements docs = content.select("div.col-centrale");
            Elements main = docs.select("div.river");
            for(Element centre : main){
                Elements river = centre.select("article.article-wrapper");
                for(Element wrapper : river){
                    Elements wrap = wrapper.select("section.chapo");
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Latribune");
                    source.setTitre("Toute l'économie du Continent");
                    source.setPays("Afrique");
                    source.setUrlContact("afrique.latribune.fr/contacts");
                    source.setUrlContactFull("https://afrique.latribune.fr/contacts");
                    source.setUrlSource(url1);
                    article.setSource(source);
                    Categorie categorie = new Categorie();
                    categorie.setNom("ÉCONOMIE");
                    article.setCategorie(categorie);
                    for(Element chapo : wrap){
                        Elements titres = chapo.select("h2");
                        for(Element titre : titres){
                            String urlA = titre.select("a").attr("href");
                            //System.out.println(urlA);
                            article.setUrlArticle(urlA);
                            String tit = titre.select("a").text();
                            //System.out.println(tit);
                            article.setTitre(tit);
                            article.setCreated(LocalDateTime.now());

                        }
                        Elements desc = chapo.select("p");
                        for(Element descs : desc){
                            String description = descs.text();
                            //System.out.println(description);
                            article.setDescription(description);
                        }
                    }
                    Elements wraps = wrapper.select("div.image-wrapper").select("a");
                    for(Element img : wraps){
                        String imageUrl = img.select("img").attr("src");
                        //System.out.println(imageUrl);
                        article.setUrlImage(imageUrl);
                        liste.add(article);
                    }
                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAfriquelatribunePolitique(){
        String url = "https://afrique.latribune.fr/politique";
        String url1 = "afrique.latribune.fr";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements content = doc.select("div.row-custom");
            Elements docs = content.select("div.col-centrale");
            Elements main = docs.select("div.river");
            for(Element centre : main){
                Elements river = centre.select("article.article-wrapper");
                for(Element wrapper : river){
                    Elements wrap = wrapper.select("section.chapo");
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Latribune");
                    source.setTitre("Toute l'économie du Continent");
                    source.setPays("Afrique");
                    source.setUrlSource(url1);
                    source.setUrlContact("afrique.latribune.fr/contacts");
                    source.setUrlContactFull("https://afrique.latribune.fr/contacts");
                    article.setSource(source);
                    Categorie categorie = new Categorie();
                    categorie.setNom("POLITIQUE");
                    article.setCategorie(categorie);
                    for(Element chapo : wrap){
                        Elements titres = chapo.select("h2");
                        for(Element titre : titres){
                            String urlA = titre.select("a").attr("href");
                            //System.out.println(urlA);
                            article.setUrlArticle(urlA);
                            String tit = titre.select("a").text();
                            //System.out.println(tit);
                            article.setTitre(tit);
                        }
                        Elements desc = chapo.select("p");
                        for(Element descs : desc){
                            String description = descs.text();
                            //System.out.println(description);
                            article.setDescription(description);
                            article.setCreated(LocalDateTime.now());

                        }
                    }
                    Elements wraps = wrapper.select("div.image-wrapper").select("a");
                    for(Element img : wraps){
                        String imageUrl = img.select("img").attr("src");
                        //System.out.println(imageUrl);
                        article.setUrlImage(imageUrl);
                        liste.add(article);
                    }
                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAfriquelatribuneTech(){
        String url = "https://afrique.latribune.fr/africa-tech";
        String urlspe = "afrique.latribune.fr";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements content = doc.select("div.row-custom");
            Elements docs = content.select("div.col-centrale");
            Elements main = docs.select("div.river");
            for(Element centre : main){
                Elements river = centre.select("article.article-wrapper");
                for(Element wrapper : river){
                    Elements wrap = wrapper.select("section.chapo");
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Latribune");
                    source.setTitre("Toute l'économie du Continent");
                    source.setPays("Afrique");
                    source.setUrlContact("afrique.latribune.fr/contacts");
                    source.setUrlContactFull("https://afrique.latribune.fr/contacts");
                    source.setUrlSource(urlspe);
                    article.setSource(source);
                    Categorie categorie = new Categorie();
                    categorie.setNom("TECHNOLOGIE");
                    article.setCategorie(categorie);
                    for(Element chapo : wrap){
                        Elements titres = chapo.select("h2");
                        for(Element titre : titres){
                            String urlA = titre.select("a").attr("href");
                            //System.out.println(urlA);
                            article.setUrlArticle(urlA);
                            String tit = titre.select("a").text();
                            //System.out.println(tit);
                            article.setTitre(tit);
                        }
                        Elements desc = chapo.select("p");
                        for(Element descs : desc){
                            String description = descs.text();
                            //System.out.println(description);
                            article.setDescription(description);
                        }
                    }
                    Elements wraps = wrapper.select("div.image-wrapper").select("a");
                    for(Element img : wraps){
                        String imageUrl = img.select("img").attr("src");
                        //System.out.println(imageUrl);
                        article.setUrlImage(imageUrl);
                        article.setCreated(LocalDateTime.now());

                        liste.add(article);
                    }
                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAfriquelatribuneTechTelecoms(){
        String url2 = "https://afrique.latribune.fr/africa-tech/telecoms";
        String urlspe = "afrique.latribune.fr";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url2).get();
            String title = doc.title();
            //System.out.println(title);
            Elements content = doc.select("div.row-custom");
            Elements docs = content.select("div.col-centrale");
            Elements main = docs.select("div.river");
            for(Element centre : main){
                Elements river = centre.select("article.article-wrapper");
                for(Element wrapper : river){
                    Elements wrap = wrapper.select("section.chapo");
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Latribune");
                    source.setTitre("Toute l'économie du Continent");
                    source.setPays("Afrique");
                    source.setUrlContact("afrique.latribune.fr/contacts");
                    source.setUrlContactFull("https://afrique.latribune.fr/contacts");
                    source.setUrlSource(urlspe);
                    article.setSource(source);
                    Categorie categorie = new Categorie();
                    categorie.setNom("TECHNOLOGIE");
                    article.setCategorie(categorie);
                    for(Element chapo : wrap){
                        Elements titres = chapo.select("h2");
                        for(Element titre : titres){
                            String urlA = titre.select("a").attr("href");
                            //System.out.println(urlA);
                            article.setUrlArticle(urlA);
                            String tit = titre.select("a").text();
                            //System.out.println(tit);
                            article.setTitre(tit);
                        }
                        Elements desc = chapo.select("p");
                        for(Element descs : desc){
                            String description = descs.text();
                            //System.out.println(description);
                            article.setDescription(description);
                        }
                    }
                    Elements wraps = wrapper.select("div.image-wrapper").select("a");
                    for(Element img : wraps){
                        String imageUrl = img.select("img").attr("src");
                        //System.out.println(imageUrl);
                        article.setUrlImage(imageUrl);
                        article.setCreated(LocalDateTime.now());

                        liste.add(article);
                    }
                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAfriqueitnews(){
        String url = "https://afriqueitnews.com/";
        String url1 = "afriqueitnews.com";

        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.elementor-widget-jnews_block_5_elementor");
            Elements main = docs.select("div.elementor-widget-container");
            Elements jeg = main.select("div.jeg_posts");
            for(Element posts : jeg){
                Elements post = posts.select("article.jeg_post");

                for(Element thumba : post){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("AfriqueITNews");
                    source.setPays("Afrique");
                    source.setUrlSource(url1);
                    source.setTitre(title);
                    source.setUrlContact("afriqueitnews.com/contact");
                    source.setUrlContactFull("https://afriqueitnews.com/contact/");
                    source.setUrlLogo("https://afriqueitnews.com/wp-content/uploads/2020/08/aitn-logo-aitn-white.png");
                    article.setSource(source);
                    Categorie categorie = new Categorie();
                    categorie.setNom("TECHNOLOGIE");
                    article.setCategorie(categorie);
                    Elements thumbs = thumba.select("div.jeg_thumb");
                    for(Element thumb : thumbs){
                        String urlA = thumb.select("a").attr("href");
                        //System.out.println(urlA);
                        article.setUrlArticle(urlA);
                        Elements img = thumb.select("a").select("div.thumbnail-container");
                        for(Element imgurl : img){
                            String imageUrl = imgurl.select("img").attr("data-src");
                            //System.out.println(imageUrl);
                            article.setUrlImage(imageUrl);
                        }
                    }
                    Elements content = thumba.select("div.jeg_postblock_content");
                    for(Element contents : content){
                        Elements titres = contents.select("h3.jeg_post_title");
                        for(Element tit : titres){
                            String tits = tit.select("a").text();
                            //System.out.println(tits);
                            article.setTitre(tits);
                        }
                        Elements meta = contents.select("div.jeg_meta_date").select("a");
                        for(Element dtime : meta){
                            String dtimes = dtime.text();
                            //System.out.println(dtimes);
                            article.setDatePublication(dtimes);
                        }
                        Elements desc = contents.select("div.jeg_post_excerpt").select("p");
                        for(Element descs : desc){
                            String description = descs.text();
                            //System.out.println(description);
                            article.setDescription(description);
                            article.setCreated(LocalDateTime.now());

                            liste.add(article);
                        }
                    }
                }
            }
            //liste.forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAfrimag(){
        //Afrimag 11/01/2022
        String url = "https://afrimag.net/rubrique/economie-et-entreprise/";
        String url1 = "afrimag.net";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.main");
            Elements main = docs.select("div.row");
            Elements centre = main.select("div.main-content");
            for (Element head : centre) {
                Elements post = head.select("div.listing");
                for (Element list : post) {
                    Elements col = list.select("div.column");
                    Elements premier = col.select("article.highlights");
                    for (Element postes : premier) {
                        Article article = new Article();
                        Source source = new Source();
                        source.setNom("Afrimag");
                        source.setPays("Afrique");
                        source.setUrlSource(url1);
                        source.setTitre(title);
                        source.setUrlContact("afrimag.net/informations-legales");
                        source.setUrlContactFull("https://afrimag.net/informations-legales/");
                        source.setUrlLogo("https://afrimag.net/wp-content/uploads/2020/05/Logo-Afrimag-HD.png");
                        article.setSource(source);
                        Categorie categorie = new Categorie();
                        categorie.setNom("Économie");
                        article.setCategorie(categorie);
                        String urlA = postes.select("a.image-link").attr("href");
                        //System.out.println(urlA);
                        article.setUrlArticle(urlA);
                        Elements urlImg = postes.select("a.image-link");
                        for (Element urleImg : urlImg) {
                            String urlImage = urleImg.select("img.image").attr("data-src");
                            //System.out.println(urlImage);
                            article.setUrlImage(urlImage);
                        }
                        Elements dt = postes.select("div.cf");
                        for (Element dtime : dt) {
                            String dtimes = dtime.select("time").text();
                            //System.out.println(dtimes);
                            article.setDatePublication(dtimes);
                            article.setCreated(LocalDateTime.now());

                        }
                        Elements titre = postes.select("h2.post-title");
                        for (Element tit : titre) {
                            String titr = tit.select("a").text();
                            //System.out.println(titr);
                            article.setTitre(titr);
                        }
                        Elements desc = postes.select("div.excerpt");
                        for (Element descs : desc) {
                            String description = descs.select("p").text();
                            //System.out.println(description);
                            article.setDescription(description);
                            liste.add(article);
                        }
                    }

                }
            }
            //System.out.println(liste);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getDakarActu(){
        //DakarActu 11/01/2022
        String url = "https://www.dakaractu.com";
        String url1 = "dakaractu.com";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Elements docs = doc.select("tbody");
            Elements main = docs.select("td.celcombo1");
            Elements centre = main.select("div.eau");
            for(Element div : centre){
                Elements cel = div.select("div.cel1");
                for(Element tit : cel){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("DakarActu");
                    source.setPays("Sénégal");
                    source.setTitre(title);
                    source.setContact("dakaractu@gmail.com");
                    source.setUrlLogo("https://www.dakaractu.com/photo/titre_4609675.png?v=1508293565");
                    source.setUrlSource(url1);
                    article.setSource(source);
                    Elements titr = tit.select("h3.titre");
                    for(Element titres : titr){
                        String urlA = titres.select("a").attr("href");
                        article.setUrlArticle(url+urlA);
                        //System.out.println(url+urlA);
                        String montitre = titres.select("a").text();
                        //System.out.println(montitre);
                        article.setTitre(montitre);
                    }
                    Elements detail = tit.select("div.shadow");
                    for(Element imgU : detail){
                        Elements urlImg = imgU.select("a");
                        for(Element urlImge : urlImg){
                            String urlImage = urlImge.select("img").attr("src");
                            //System.out.println(urlImage);
                            article.setUrlImage(urlImage);
                            article.setCreated(LocalDateTime.now());

                        }
                    }
                    Elements desc = tit.select("div.texte");
                    for(Element descs : desc){
                        String description = descs.select("a").text();
                        //System.out.println(description);
                        article.setDescription(description);
                        liste.add(article);
                    }
                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAps() {
        //APS 11/01/2022
        String url = "http://aps.sn/";
        String url1 = "aps.sn";

        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).timeout(100 * 1000).get();//.get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.page-content");
            Elements main = docs.select("div.content-actu");
            Elements centre = main.select("div.grid_8");
            Elements clearfix = centre.select("div.clearfix");
            Elements blog = clearfix.select("ul.from-blog");
            for(Element grid : blog){

                Elements lists = grid.select("li.grid_4");
                for(Element listes : lists){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Aps");
                    source.setPays("Sénégal");
                    source.setTitre(title);
                    source.setUrlContact("aps.sn/spip.php?page=contact");
                    source.setUrlContactFull("http://aps.sn/spip.php?page=contact");
                    source.setUrlLogo("http://aps.sn/squelettes/images/theme/logo.png");
                    source.setUrlSource(url1);
                    article.setSource(source);
                    String urlA = listes.select("a").attr("href");
                    //System.out.println(url+urlA);
                    article.setUrlArticle(url+urlA);
                    Elements imgUrl = listes.select("a");
                    String imageUrl = imgUrl.select("img").attr("src");
                    //System.out.println(url+imageUrl);
                    article.setUrlImage(url+imageUrl);
                    Elements detail = listes.select("div.detailes");
                    for(Element details :detail){
                        Elements titre = details.select("h5");
                        for(Element tit : titre){
                            String titres = tit.select("a").text();
                            //System.out.println(titres);
                            article.setTitre(titres);
                        }
                        String desc = details.select("p").text();
                        //System.out.println(desc);
                        article.setDescription(desc);
                        article.setCreated(LocalDateTime.now());

                        liste.add(article);
                    }
                    ///Elements time = listes.select("div.from-meta").not(".icon-eye-open").not(".icon-heart");
                    ///System.out.println(time);
                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    public static List<Article> getAdiacCongo(){
        //Adiac-Congo 12/01/2022

        String url = "https://www.adiac-congo.com";
        String url2 = "adiac-congo.com";
        List<Article> liste = new ArrayList<>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div#main-content");
            Elements main = docs.select("div.content-style");
            Elements content = main.select("div#block-system-main");
            Elements view = content.select("div.view-content");

            for(Element bloc : view){
                Elements row = bloc.select("div.views-row");
                for(Element field : row){
                    Article article = new Article();
                    Source source = new Source();
                    source.setNom("Adiac-Congo");
                    source.setTitre(title);
                    source.setPays("Congo");
                    source.setUrlSource(url2);
                    source.setUrlContact("adiac-congo.com/content/contact");
                    source.setUrlContactFull("https://www.adiac-congo.com/content/contact");
                    source.setUrlLogo("https://www.adiac-congo.com/sites/default/files/images/logo.png");
                    article.setSource(source);
                    Elements titres = field.select("div.views-field-title");
                    for(Element titre : titres){
                        String tit = titre.select("span.field-content").text();
                        //System.out.println(tit);
                        article.setTitre(tit);
                        String urlArt = titre.select("span.field-content").select("a").attr("href");
                        //System.out.println(url+urlArt);
                        article.setUrlArticle(url+urlArt);
                    }
                    Elements rubriques = field.select("div.views-field-field-rubrique");
                    for(Element rubrique : rubriques)
                    {
                        Categorie categorie = new Categorie();
                        Elements cat = rubrique.select("div.field-content").select("span");
                        String cate = cat.text();
                        categorie.setNom(cate);
                        article.setCategorie(categorie);
                        //System.out.println(cate);
                    }

                    Elements nothings = field.select("div.views-field-nothing-1");
                    for(Element nothing : nothings){
                        String desc = nothing.select("span.field-content").select("p").select("strong").text();
                        //System.out.println(desc);
                        article.setDescription(desc);
                        article.setCreated(LocalDateTime.now());

                        String imageUrl = nothing.select("span.field-content").select("p").select("img").attr("src");
                        //System.out.println(url+imageUrl);
                        article.setUrlImage(url+imageUrl);
                        liste.add(article);
                    }

                }
            }
            //System.out.println(liste);
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getKoaciExtract() throws RuntimeException{
        //Koaci Afrique
        String url ="https://www.koaci.com/pays/afrique";
        String url1 ="koaci.com";
        List<Article> allArticle = new ArrayList<Article>();

        try {
            Document dc = Jsoup.connect(url).get();
            String titresweb = dc.title();
            Elements body = dc.select("div.KoaciPageWhite");
            //Premier bloc
            for(Element step : body){
                String etat = step.select("div.KTagFilterBar div.KTagFilterBarSt2").text();
                //System.out.println(pays);
                Elements bloc = step.select("div.KoaciPageSplitA1");

                //Deuxieme bloc
                for(Element st : bloc){
                    Elements lien = st.select("a[href$=.html]");
                    //System.out.println(lien);
                    //la liste des liens
                    for(Element l : lien){
                        //Recupere la liste des liens
                        String rel = l.attr("href");
                        //System.out.println(rel);
                        Article article = new Article();
                        Source source = new Source();
                        source.setNom("Koaci");
                        source.setTitre(titresweb);
                        source.setPays("Côte d'ivoire");
                        source.setUrlSource(url1);
                        source.setUrlContact("koaci.com/contact.html");
                        source.setUrlContactFull("https://www.koaci.com/contact.html");
                        source.setUrlLogo("https://www.koaci.com/img/koaci_logo.png");
                        article.setSource(source);
                        article.setUrlArticle(rel);

                        //Bloc pour recuperer la liste des urls images
                        Elements tb = l.select("div#KoaciBlocNews6_ ");
                        for(Element blc : tb){
                            Elements url_image = blc.select("div.KoaciBlocNews6St1 img");
                            for(Element image : url_image){
                                String url_img = image.attr("src");
                                article.setUrlImage(url_img);
                                String titre = image.attr("alt");
                                article.setTitre(titre);
                                //System.out.println(url_img);
                            }
                            Elements titre = blc.select("div.KoaciBlocNews6St2");
                            for(Element title : titre){
                                Elements descript = titre.select("div.KoaciBlocNews6Text1");
                                for(Element description : descript){
                                    //System.out.println(description.text());
                                    article.setDescription(description.text());
                                    article.setCreated(LocalDateTime.now());
                                    allArticle.add(article);
                                }
                                Elements categ = titre.select("div.KoaciBlocNews2Text2 ");
                                for(Element categorie : categ){
                                    String[] dates = categorie.before("span").text().split("il");
                                    Categorie categorie1 = new Categorie();
                                    categorie1.setNom(dates[0]);
                                    article.setCategorie(categorie1);
                                    //System.out.println(dates[0]);
                                }
                            }
                        }
                    }
                }
            }
            //allArticle.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return allArticle;
    }

    public static List<Article> getLesEchosExtraction() throws RuntimeException{
        //EXtraction du site lesechos-congobrazza
        String url = "https://lesechos-congobrazza.com";
        String url1 = "lesechos-congobrazza.com";
        List<Article> articlesList = new ArrayList<Article>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Elements main = doc.select("div.blog");
            Elements bloc = main.select("div#item-container");
            for(Element ite : bloc){
                Elements items = ite.select("div.item");
                for(Element item : items){
                    Elements row = item.select("article");
                    Article article = new Article();
                    Source source = new Source();
                    source.setUrlSource(url1);
                    source.setNom("LesEchos");
                    source.setTitre(title);
                    source.setUrlLogo("https://lesechos-congobrazza.com/templates/ja_magz_ii/images/logo.png");
                    source.setUrlContact("lesechos-congobrazza.com/contact-redaction");
                    source.setUrlContactFull("https://lesechos-congobrazza.com/contact-redaction");
                    source.setPays("Congo");
                    article.setSource(source);
                    for(Element post : row){
                        Elements posts = post.select("div.pull-none");
                        for(Element src : posts){
                            Elements art_url = src.select("a");
                            String a_url = art_url.attr("href");
                            //System.out.println(url+a_url);
                            article.setUrlArticle(url+a_url);
                            for(Element srcI : art_url){
                                String article_img = srcI.select("img").attr("src");
                                //System.out.println(url+article_img);
                                article.setUrlImage(url+article_img);
                            }
                        }
                    }
                    for(Element detail: row){
                        Elements details = detail.select("div.item-content");
                        for(Element aside: details){
                            Elements side = aside.select("aside.article-aside");
                            for(Element genre : side){
                                Categorie categorie = new Categorie();
                                String url_categorie = genre.select("a").attr("href");
                                String catName = genre.select("span").text();
                                categorie.setNom(catName);
                                article.setCategorie(categorie);
                                //System.out.println(url+url_categorie);
                                Elements times = side.select("dd");
                                for(Element dtime : times){
                                    String date = dtime.select("time").text();
                                    article.setDatePublication(date);
                                    article.setCreated(LocalDateTime.now());

                                    //System.out.println(date);
                                }
                            }
                            Elements title_article = aside.select("h2.article-title");
                            for(Element art_title : title_article){
                                String tis = art_title.text();
                                article.setTitre(tis);
                                //System.out.println(tis);
                            }
                            Elements desc_article = aside.select("section.article-intro");
                            for(Element desc : desc_article){
                                String descpt = desc.text();
                                article.setDescription(descpt);

                                //System.out.println(descpt);
                            }
                        }

                    }
                    articlesList.add(article);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return articlesList;
    }

    public static List<Article> getJeuneAfrique(){
        String url = "https://www.jeuneafrique.com/";
        String url1 = "jeuneafrique.com";
        List<Article> jList = new ArrayList<Article>();
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Elements docs = doc.select("div.page__content");
            Elements main = docs.select("div.panel");
            for(Element bloc : main){
                Elements grid = bloc.select("div.grid__cell");
                for(Element art : grid){
                    Elements artis = art.select("article.thumbnail a");
                    Article article = new Article();
                    Source source = new Source();
                    source.setPays("Afrique");
                    source.setTitre(title);
                    source.setNom("Jeune Afrique");
                    source.setUrlContact("jeuneafrique.com/contacts");
                    source.setUrlContactFull("https://www.jeuneafrique.com/contacts/");
                    source.setUrlSource(url1);
                    article.setSource(source);
                    for(Element articl : artis){
                        String thumb = articl.attr("href");
                        //Url Article
                        //System.out.println(thumb);
                        article.setUrlArticle(thumb);
                        Elements img = articl.select("div.thumbnail__header img.thumbnail__img");
                        for(Element imgA : img){
                            String image = imgA.attr("data-src");
                            //UrlImage Article
                            //System.out.println(image);
                            article.setCreated(LocalDateTime.now());
                            article.setUrlImage(image);
                        }
                        Elements infos = articl.select("div.thumbnail__body");
                        for(Element info : infos){
                            String tag = info.select("span.subtitle").text();
                            //Categorie Name
                            //System.out.println(tag);
                            String titleArt = info.select("h2").text();
                            article.setTitre(titleArt);
                            //Titre Article
                            //System.out.println(titleArt);
                            //Description Article
                            String descArt = info.select("p").text();
                            article.setDescription(descArt);
                            //System.out.println(descArt);
                            jList.add(article);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jList;
    }


    //Boucle
    public static List<Article> getSenego(){
        //Extraction senego On a pu l'url_image_article le titre la description l'url_article l'auteur la date_article recuperer
        String url = "https://senego.com/";
        String url1 = "senego.com";
        List<Article> list = new ArrayList<Article>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Elements main = doc.select("div#main-content");
            Elements head = main.select("div.post-list-home");
            //System.out.println(main);
            for(Element bodys : head){
                Elements body = bodys.select("main#site-content");


                    for(Element article : body) {
                        Article article1 = new Article();
                        Source source = new Source();
                        source.setPays("Sénégal");
                        source.setNom("Senego");
                        source.setTitre(title);
                        source.setUrlContact("senego.com/nous-contacter");
                        source.setUrlContactFull("https://senego.com/nous-contacter");
                        source.setUrlLogo("https://senego.com/wp-content/themes/senegoV11/images/logo-1.png");
                        source.setUrlSource(url1);
                        article1.setSource(source);
                        Elements artikl = article.select("article.posts-list");
                        for(Element artikle: artikl){
                            Elements imgartikl = artikle.select("div.posts-list-images");
                            for(Element img : imgartikl){
                                String lienArtikl = img.select("a").attr("href");
                                article1.setUrlArticle(lienArtikl);
                                //System.out.println(lienArtikl);
                                String imglien = img.select("img").attr("src");
                                article1.setUrlImage(url+imglien);
                                //System.out.println(url+imglien);
                            }
                            Elements detail = artikle.select("div.posts-list-detail");
                            for(Element dets : detail){
                                Elements infos = dets.select("div.posts-list-infos");
                                for(Element info : infos){
                                    String author = info.select("div.posts-list-author").text();
                                    article1.setAuteur(author);
                                    //System.out.println(author);
                                    String tim = info.select("div.posts-list-time").text();
                                    //System.out.println(tim);
                                    article1.setDatePublication(tim);
                                }
                                String titre = dets.select("h2.entry-title").text();
                                //System.out.println(titre);
                                article1.setTitre(titre);
                                article1.setCreated(LocalDateTime.now());

                                Elements desc = dets.select("p.posts-list-excerpt");
                                for(Element descript : desc){
                                    String description = descript.text();
                                    article1.setDescription(description);

                                }
                                //System.out.println(desc);

                                list.add(article1);
                            }

                        }

                    }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
