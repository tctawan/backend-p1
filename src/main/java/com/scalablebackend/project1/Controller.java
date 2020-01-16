package com.scalablebackend.project1;

import com.scalablebackend.project1.Dto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    DtoService dtoService;
    @Autowired
    TemplateEngine htmlTemplateEngine;

    private static final Logger logger = LogManager.getLogger(Controller.class);

    @GetMapping("/wc")
    public ResponseEntity wordCount(@RequestHeader("accept") String format, @RequestParam String url , @RequestParam(required = false, defaultValue = "false") Boolean force){
        logger.info(url);
        try {
            Dto dto = dtoService.getWordCount(url, force);
            HttpHeaders headers = new HttpHeaders();
            if(format.equals(MediaType.TEXT_PLAIN_VALUE)){
                headers.setContentType(MediaType.TEXT_PLAIN);
                return new ResponseEntity<>(dto.toString(), headers , HttpStatus.OK);

            }else if (format.equals(MediaType.APPLICATION_JSON_VALUE)){
                headers.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(dto, headers , HttpStatus.OK);
            }else{
                headers.setContentType(MediaType.TEXT_HTML);
                Context ctx = new org.thymeleaf.context.Context();
                ctx.setVariable("top10", dto.getTop10());
                ctx.setVariable("total", dto.getTotal());
                final String htmlContent = this.htmlTemplateEngine.process("wc.html", ctx);
                return new ResponseEntity<>(htmlContent,headers,HttpStatus.OK);
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }




}
