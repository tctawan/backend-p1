package com.scalablebackend.project1;

import com.scalablebackend.project1.Dto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class Controller {

    @Autowired
    DtoService dtoService;


    @GetMapping("/wc")
    public ResponseEntity wordCount(@RequestParam String url , @RequestParam(required = false, defaultValue = "false") Boolean force){
        try {
            Dto dto = dtoService.getWordCount(url, force);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
        }

    }




}
