package com.dreesis.afronews_graphql.utils;

import com.dreesis.afronews_graphql.entities.Article;
import com.dreesis.afronews_graphql.entities.Categorie;
import com.dreesis.afronews_graphql.entities.Source;
import com.sun.jdi.event.ExceptionEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExtractionSource2 {
    public static List<Article> getSeneweb() {
        String url = "https://www.seneweb.com";
        String url1 = "seneweb.com";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.fifa_block_wrapper");
            Elements main = docs.select("div.posts_block");
            Elements block = main.select("div.posts_block_center");
            Elements module = block.select("ul.module_main_posts_list");


                    Elements post = module.select("li.module_main_post_item");
                    for (Element posts : post) {
                        Article article = new Article();
                        Source source = new Source();
                        source.setNom("Seneweb");
                        source.setPays("Sénégal");
                        source.setTitre(title);
                        source.setUrlContact("redaction@seneweb.com");
                        source.setUrlContactFull("redaction@seneweb.com");
                        source.setUrlLogo("https://www.seneweb.com/v5/img/logo-2oans-welcome-page2.png");
                        source.setUrlSource(url1);
                        article.setSource(source);
                        Elements image = posts.select("div.module_main_post_image");
                        Elements lienimg = image.select("a");
                        for (Element imglien : lienimg) {
                            String urlLien = imglien.attr("href");
                            article.setUrlArticle(url+urlLien);
                            String imgs = imglien.select("img.lazy_started").attr("src");
                            //System.out.println(url+urlLien);
                            article.setUrlImage(imgs);
                            //System.out.println(imgs);
                        }
                        Elements cat = posts.select("span.module_main_post_categorey");
                        for(Element categorie : cat){
                            String catString = categorie.text();
                            Categorie categorie1 = new Categorie();
                            categorie1.setNom(catString);
                            article.setCategorie(categorie1);
                            //System.out.println(catString);
                        }
                        Elements content = posts.select("div.module_main_post_content");
                        for(Element cont : content){
                            String titre = cont.select("a.module_main_post_title").text();
                            article.setTitre(titre);
                            article.setCreated(LocalDateTime.now());

                            //System.out.println(titre);
                            Elements desc = cont.select("div.post_meta");
                            for(Element descript : desc){
                                String description = descript.select("span.meta_item").first().text();
                                //System.out.println(description);
                                article.setDescription(description);
                                liste.add(article);

                            }

                        }
                    }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getGalsen221(){
        String url = "https://galsen221.com/";
        String url1 = "galsen221.com";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.zox-widget-bg");
            Elements main = docs.select("div.zox-widget-home");
            Elements block = main.select("div.zox-widget-flex1-adr");
            Elements module = block.select("div.zox-art-grid");
            for(Element mod: module){
                Elements zox = mod.select("div.zox-art-img");
                Article article = new Article();
                Source source = new Source();
                source.setNom("Galsen221");
                source.setPays("Sénégal");
                source.setTitre(title);
                source.setUrlSource(url1);
                source.setUrlLogo("https://galsen221.com/wp-content/uploads/2021/05/g221-logo-2-e1620381032159.png");
                article.setSource(source);
                for(Element img : zox){
                    String urlArt = img.select("a").attr("href");
                    //System.out.println(urlArt);
                    article.setUrlArticle(urlArt);
                    Elements imgs = img.select("a");
                    for(Element lazy: imgs){
                        String urlImg = lazy.select("img.wp-post-image").attr("src");
                        article.setUrlImage(urlImg);
                        //System.out.println(urlImg);
                    }
                }
                Elements meta = mod.select("div.zox-art-title");
                for(Element tits : meta){
                    String titre = tits.select("h2.zox-s-title2").text();
                    //System.out.println(titre);
                    article.setTitre(titre);
                }
                String desc = mod.select("p.zox-s-graph").text();
                //System.out.println(desc);
                article.setDescription(desc);
                Elements dtims = mod.select("div.zox-byline-wrap");
                for(Element wrap : dtims){
                    String author = wrap.select("span.zox-byline-name").text();
                    String datimes = wrap.select("span.zox-byline-date").text();
                    article.setDatePublication(datimes);
                    article.setAuteur(author);
                    article.setCreated(LocalDateTime.now());

                    //System.out.println(author);
                    //System.out.println(datimes);
                }
                liste.add(article);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    //RDC
    public static List<Article> getActualiteCD(){
        String url = "https://actualite.cd";
        String url1 = "actualite.cd";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("section.col-sm-12");
            Elements main = docs.select("div.region");
            Elements block = main.select("div.dynamic-layouts-container");
            Elements module = block.select("div.dynamic-layout-row");
            //Groupe 1
            Elements layout = module.select("div.layout-builder--layout__region");
            Elements views = layout.select("section.views-element-container");
            for(Element view : views){
                Article article = new Article();
                Source source = new Source();
                source.setNom("Actualite.CD");
                source.setPays("RDC");
                source.setTitre(title);
                source.setContact("+243999136373");
                source.setUrlLogo("https://actualite.cd/sites/default/files/Logo-A-1.png");
                source.setUrlSource(url1);
                article.setSource(source);
                Elements content = view.select("div.view-content");
                for(Element cont : content){
                    Elements col = cont.select("div.views-field-field-image-media");
                    for(Element fields : col){
                        String urlArticle = fields.select("a").attr("href");
                        //System.out.println(url+urlArticle);
                        article.setUrlArticle(url+urlArticle);
                        Elements img = fields.select("a");
                        for(Element imgurl : img){
                            String imgUrl = imgurl.select("img").attr("src");
                            //System.out.println(url+imgUrl);
                            article.setUrlImage(url+imgUrl);
                        }

                    }
                    Elements titles = cont.select("div.views-field-title");
                    for(Element tits : titles){
                        String titre = tits.select("span.field-content").text();
                        article.setTitre(titre);
                        //System.out.println(titre);
                    }
                    Elements categs = cont.select("div.views-field-field-categorie");
                    for(Element cats : categs){
                        String categorie = cats.select("div.field-content").text();
                        Categorie categorieM = new Categorie();
                        categorieM.setNom(categorie);
                        article.setCategorie(categorieM);
                        article.setCreated(LocalDateTime.now());

                        //System.out.println(categorie);
                    }
                    liste.add(article);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> get7sur7CD(){
        String url = "https://7sur7.cd";
        String url1 = "7sur7.cd";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.main-container");
            Elements main = docs.select("section.col-sm-12");
            Elements block = main.select("div.region-sous-la-une");
            Elements module = block.select("section.views-element-container");
            Elements content = module.select("div.view-content");
            Elements cont = content.select("div.col-xs-12");
            for(Element col : cont){
                Article article = new Article();
                Source source = new Source();
                source.setNom("7sur7");
                source.setPays("RDC");
                source.setTitre(title);
                source.setUrlContact("7sur7.cd/contact");
                source.setUrlContactFull("https://7sur7.cd/contact");
                source.setUrlSource(url1);
                source.setUrlLogo("https://7sur7.cd/sites/default/files/cropped-logo-7sur7-fond-blanc-75px.jpg");
                article.setSource(source);
                Elements categs = col.select("div.views-field-field-categorie");
                for(Element cats : categs){
                    String categorie = cats.select("a").text();
                    Categorie categorie1 = new Categorie();
                    categorie1.setNom(categorie);
                    article.setCategorie(categorie1);
                    //System.out.println(categorie);
                }
                Elements image = col.select("div.views-field-field-image");
                for(Element img : image){
                    String urlArt = img.select("a").attr("href");
                    article.setUrlArticle(url+urlArt);
                    Elements urlImg = img.select("a");
                    for(Element imges : urlImg){
                        String imgesUrl = imges.select("img").attr("src");
                        article.setUrlImage(url+imgesUrl);
                    }
                    //System.out.println(url+urlArt);

                }
                Elements titres = col.select("div.views-field-title");
                for(Element tits : titres){
                    Elements titre = tits.select("span.field-content").select("div");
                    String titreUrl = titre.get(0).text();
                    article.setTitre(titreUrl);
                    String dateP = titre.get(1).text();
                    article.setDatePublication(dateP);
                    article.setCreated(LocalDateTime.now());

                }
                liste.add(article);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getRadioOkapi(){
        String url = "https://www.radiookapi.net/actualite";
        String url1 = "radiookapi.net";
        String url2 = "https://www.radiookapi.net";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("section.col-sm-12");
            Elements main = docs.select("div.panels-flexible-region");
            Elements block = main.select("div.pane-content");
            Elements content = block.select("div.view");
            Elements module = content.select("div.views-row");
            for(Element cont : module){
                Article article = new Article();
                Source source = new Source();
                source.setNom("Radio Okapi");
                source.setPays("RDC");
                source.setTitre(title);
                source.setUrlSource(url1);
                source.setUrlLogo("https://www.radiookapi.net/sites/default/files/logo-radiookapi-254-92.png");
                source.setUrlContactFull("https://www.radiookapi.net/contact");
                source.setUrlContact("radiookapi.net/contact");
                article.setSource(source);
                Elements img = cont.select("div.views-field-field-image-une");
                Elements imges = img.select("div.floatleft");
                for(Element imgs : imges){
                    String images = imgs.select("img").attr("src");
                    //System.out.println(images);
                    article.setUrlImage(images);
                }
                Elements titres = cont.select("div.views-field-title");
                for(Element tits : titres){
                    Elements titreh2 = tits.select("h2.field-content");
                    String urlArt = titreh2.select("a").attr("href");
                    article.setUrlArticle(url2+urlArt);
                    String titreUrl = titreh2.text();
                    article.setTitre(titreUrl);
                    //System.out.println(urlArt);
                    //System.out.println(titreUrl);
                }
                Elements datime = cont.select("div.views-field-created");
                for(Element dtime : datime){
                    String dtimes = dtime.select("span").text();
                    //System.out.println(dtimes);
                    article.setDatePublication(dtimes);
                    article.setCreated(LocalDateTime.now());

                }
                Elements categs = cont.select("span.views-field-field-cat-gorie");
                for(Element cats : categs){
                    String categorie = cats.select("span.field-content").select("a").get(0).text();
                    Categorie categorie1 = new Categorie();
                    categorie1.setNom(categorie);
                    article.setCategorie(categorie1);
                    //System.out.println(categorie);
                    liste.add(article);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getPolitico(){
        String url = "https://www.politico.cd/";
        String url1 = "politico.cd";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.vc_column");
            Elements main = docs.select("div.wpb_wrapper");
            Elements block = main.select("div.tdi_99");
            Elements content = block.select("div.tdi_101");
            Elements module = content.select("div.wpb_wrapper");
            Elements wrap = module.select("div.td_block_template_9");
            Elements wraps = wrap.select("div.td_block_inner");
            Elements modules = wraps.select("div.td_module_flex");
            for(Element mod : modules){
                Article article = new Article();
                Source source = new Source();
                source.setNom("Politico");
                source.setPays("RDC");
                source.setTitre(title);
                source.setUrlSource(url1);
                source.setUrlLogo("https://www.politico.cd/wp-content/uploads/2019/12/logo-politico-1.png");
                source.setUrlContactFull("https://www.politico.cd/nous-contacter/");
                source.setUrlContact("politico.cd/nous-contacter/");
                article.setSource(source);
                Elements img = mod.select("div.td-image-container");
                for(Element imges : img){
                    String urlArt = imges.select("a").attr("href");
                    article.setUrlArticle(urlArt);
                    //System.out.println(urlArt);
                    Elements imgs = imges.select("a");
                    for(Element td_img: imgs){
                        String imgUrl = td_img.select("span").attr("data-img-url");
                        //System.out.println(imgUrl);
                        article.setUrlImage(imgUrl);
                    }
                }
                Elements meta = mod.select("div.td-module-meta-info");
                for(Element mets : meta){
                    String titre = mets.select("h3.entry-title").text();
                    //System.out.println(titre);
                    article.setTitre(titre);
                    Elements dtime = mets.select("span.td-author-date");
                    for(Element dtims : dtime){
                        String datetimes = dtims.select("time.entry-date").attr("datetime");
                        String datimes = dtims.select("time.entry-date").text();
                        //System.out.println(datetimes);
                        article.setDatePublicationFormat(datetimes);
                        article.setDatePublication(datimes);
                        article.setCreated(LocalDateTime.now());
                        //System.out.println(datimes);

                    }
                    liste.add(article);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    //Fini mais pas pris en charge
    public static List<Article> getMosaicGuinee(){
        String url = "https://mosaiqueguinee.com/";
        String url1 = "mosaiqueguinee.com";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.wpb_column");
            Elements main = docs.select("div.jeg_wrapper");
            Elements block = main.select("div.jeg_postblock_8");
            Elements bloc = block.select("div.jeg_posts_wrap");
            Elements wrap = bloc.select("article.jeg_post");
            for(Element arts : wrap){
                Article article = new Article();
                Source source = new Source();
                source.setNom("mosaiqueguinee");
                source.setPays("GuineeConakry");
                source.setTitre(title);
                source.setUrlSource("mosaiqueguinee.com");
                source.setUrlLogo("https://mosaiqueguinee.com/wp-content/uploads/2019/02/logo_Mosaique_guinee01.png");
                source.setUrlContact("Tel. +224 621 65 60 21");
                article.setSource(source);
                Elements news = arts.select("div.jeg_thumb");
                for(Element img : news){
                    String newsUrl = img.select("a").attr("href");
                    article.setUrlArticle(newsUrl);
                    Elements imgUrl = img.select("a").select("div.thumbnail-container");
                    for(Element image : imgUrl){
                        String imgsUrls = image.select("img.wp-post-image").attr("data-src");
                        //System.out.println(imgsUrls);
                        article.setUrlImage(imgsUrls);
                    }
                }
                for(Element cats : news){
                    String categs = cats.select("div.jeg_post_category").select("span").text();
                    //System.out.println(categs);
                    Categorie categorie = new Categorie();
                    categorie.setNom(categs);
                    article.setCategorie(categorie);
                }
                Elements content = arts.select("div.jeg_postblock_content");
                for(Element conts : content){
                    String titres = conts.select("h3.jeg_post_title").text();
                    //System.out.println(titres);
                    article.setTitre(titres);
                    Elements meta = conts.select("div.jeg_post_meta");
                    for(Element jeg : meta){
                        String auteur = jeg.select("div.jeg_meta_author").select("a").text();
                        //System.out.println(auteur);
                        article.setAuteur(auteur);
                    }
                    for(Element jeg_meta : meta){
                        String dtime = jeg_meta.select("div.jeg_meta_date").select("a").text();
                        //System.out.println(dtime);
                        article.setDatePublication(dtime);
                        article.setCreated(LocalDateTime.now());
                        liste.add(article);
                    }
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    //Pas fini
    public static List<Article> getGuineeNews(){
        String url = "https://guineenews.org/";
        String url1 = "guineenews.org";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.bs-vc-wrapper");
            Elements main = docs.select("div.wpb_wrapper");
            Elements block = main.select("div.listing");
            Elements listing = block.select("article.type-post");
            for(Element arts : listing){
                Elements featured = arts.select("div.featured");
                for(Element img : featured){
                    String image = img.select("a.img-holder").attr("src");
                    ///System.out.println(image);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

    public static List<Article> getAfrikPresse(){
        String url = "https://afrikipresse.fr/";
        String url1 = "afrikipresse.fr";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements docs = doc.select("div.jeg_main");
            Elements main = docs.select("div.jeg_container");
            Elements block = main.select("div.bialty-container");
            Elements vc = block.select("div.vc_row");
            Elements jeg_wrap = vc.select("div.wpb_column");
            for(Element jeg : jeg_wrap){
                Elements post = jeg.select("div.jeg_postblock_1");
                for(Element cat : post){
                    String mod = cat.select("div.jeg_block_heading").select("h3.jeg_block_title").text();
                    //System.out.println(mod);
                }
                for(Element news : post){

                    Elements arts = news.select("div.jeg_block_container").select("div.jeg_posts");
                    for(Element posts: arts){
                        Elements thumb = posts.select("article.jeg_post");
                        for(Element img : thumb){
                            Article article = new Article();
                            Source source = new Source();
                            source.setNom("AfrikiPresse");
                            source.setPays("Afrique");
                            source.setTitre(title);
                            source.setUrlSource(url1);
                            source.setUrlLogo("https://afrikipresse.fr/wp-content/uploads/2019/07/logo-afrikipresse.png");
                            source.setContact("+33 601264678");
                            article.setSource(source);
                            String urlArt = img.select("a").attr("href");
                            article.setUrlArticle(urlArt);
                            //System.out.println(urlArt);
                            Elements imgArt = img.select("a").select("div.thumbnail-container");
                            for(Element image :imgArt){
                                String imge = image.select("div.bialty-container").select("img.wp-post-image").attr("src");
                                //System.out.println(imge);
                                article.setUrlImage(imge);
                            }
                            String categorie = img.select("div.jeg_post_category").select("span").text();
                            //System.out.println(categorie);
                            Categorie categorie1 = new Categorie();
                            categorie1.setNom(categorie);
                            article.setCategorie(categorie1);
                            Elements content = img.select("div.jeg_postblock_content");
                            for(Element cont :content){
                                String titres = cont.select("h3.jeg_post_title").text();
                                //System.out.println(titres);
                                article.setTitre(titres);
                                String desc = cont.select("div.jeg_post_excerpt").select("p").text();
                                //System.out.println(desc);
                                article.setDescription(desc);
                                article.setCreated(LocalDateTime.now());

                            }
                            liste.add(article);
                        }

                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    //Impossible de run
    public static List<Article> getLeQuotidien(){
        String url = "https://lequotidien.sn/";
        String url1 = "lequotidien.sn";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements main = doc.select("main#main");
            Elements docs = main.select("section#latest-posts");
            Elements block = docs.select("div.row");
            for(Element vc : block)
            {
                Elements news = vc.select("article.post");
                for(Element post : news){
                    String urlArt = post.select("figure.entry-image").select("a").attr("href");
                    //System.out.println(urlArt);
                    Elements img = post.select("figure.entry-image").select("a");
                    for(Element image : img){
                        String imageurl = image.select("img.wp-post-image").attr("src");
                        System.out.println(imageurl);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }
    //Pas fini
    public static List<Article> getSenePlus(){
        String url = "https://www.seneplus.com/";
        String url1 = "seneplus.com";
        List<Article> liste = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            //System.out.println(title);
            Elements main = doc.select("div#content");
            Elements docs = main.select("div.section");
            //Elements block = docs.select("div.col-sm-8");
            Elements col = docs.select("div.home-column-center");
            for(Element home : col){
                Elements wrap = home.select("div.story-wrapper");
                for(Element story : wrap){
                    Elements tits = story.select("div.center-story-wrapper");
                    String titre = tits.select("h2").text();
                    //Element bloc = story.select("h2");
                    System.out.println(titre);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return liste;
    }

}
