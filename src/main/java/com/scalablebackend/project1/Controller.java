package com.scalablebackend.project1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;


@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    DtoService dtoService;
    @Autowired
    TemplateEngine htmlTemplateEngine;

    private static final Logger logger = LogManager.getLogger(Controller.class);

    @GetMapping("/wc")
    public ResponseEntity wordCount(@RequestHeader("accept") String format, @RequestParam String target , @RequestParam(required = false, defaultValue = "false") Boolean force){
        try {
            Response response  = dtoService.getResponse(target);
            String etag = response.header("etag");
            if(etag == null){
                etag = "";
            }
            Dto dto = dtoService.getWordCount(etag, force,response.parse());
            HttpStatus responseStatus = dto.getStatus();
            dtoService.updateStatus(dto, HttpStatus.NOT_MODIFIED);

            HttpHeaders headers = new HttpHeaders();
            if(format.equals(MediaType.TEXT_PLAIN_VALUE)){
                headers.setContentType(MediaType.TEXT_PLAIN);
                return new ResponseEntity<>(dto.toString(), headers , responseStatus);
            }else if (format.equals(MediaType.APPLICATION_JSON_VALUE)){
                headers.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(dto, headers , responseStatus);
            }else{
                headers.setContentType(MediaType.TEXT_HTML);
                Context ctx = new org.thymeleaf.context.Context();
                ctx.setVariable("top10", dto.getTop10());
                ctx.setVariable("total", dto.getTotal());
                final String htmlContent = this.htmlTemplateEngine.process("wc.html", ctx);
                return new ResponseEntity<>(htmlContent,headers,responseStatus);
            }

        }catch (IOException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }




}
