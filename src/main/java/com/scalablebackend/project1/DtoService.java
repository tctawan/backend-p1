package com.scalablebackend.project1;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DtoService {


    private static final Logger logger = LogManager.getLogger(Controller.class);
    @Cacheable(cacheNames="wordcount", condition = "! #force",key="#url", sync = true)
    public Dto getWordCount(String url, Boolean force) throws IOException{
            int totalCount = 0;
            logger.info("execute " + url);
            Document doc = Jsoup.connect(url).get();

            Elements elements = doc.body().getAllElements();
            List<String> list = Arrays.asList("style", "script", "head", "title", "meta", "[document]");
            Pattern pattern = Pattern.compile("\\b[a-zA-Z]{1,20}\\b");
            HashMap<String,Integer> countMap = new HashMap<>();
            for(Element e : elements ){
                if(!list.contains(e.parent().tagName())){
                    List<String> words = new ArrayList<>();
                    Matcher m = pattern.matcher(e.ownText());
                    while(m.find()){
                        words.add(m.group());
                    }
                    for (String word : words){
                        word = word.toLowerCase();
                        int count = countMap.getOrDefault(word, 0);
                        count++;
                        countMap.put(word,count);
                        totalCount++;
                    }
                }

            }

            List<String> top10 = getTop10(countMap);
            return new Dto(top10,totalCount);
    }

    private List<String> getTop10(HashMap<String, Integer> map) {
        Set<String> set = map.keySet()                                                                                                                                      ;
        List<String> keys = new ArrayList<>(set);

        keys.sort((s1, s2) -> Integer.compare(map.get(s2), map.get(s1)));

        return keys.subList(0,10);
    }


}
