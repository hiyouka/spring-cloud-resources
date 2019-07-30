package com.hiyouka.source.controller;

import com.hiyouka.source.model.ConfigDataEntry;
import com.hiyouka.source.service.ConfigEntryService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@RestController
@RequestMapping("config")
public class ConfigDataEntryController {

    @Autowired
    private ConfigEntryService entryService;

    @GetMapping("/get")
    public String getConfig(String id){
        return entryService.getConfig(id);
    }

    @GetMapping("/list")
    public String list(String id){
        ConfigDataEntry configDataEntry = new ConfigDataEntry();
        configDataEntry.setNodeId(id);
        return entryService.getConfig(configDataEntry);
    }

    @PostMapping("/json")
    public String po(@RequestBody ConfigDataEntry entry){
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
//        copyOfContextMap.forEach((k,v) -> {
//            System.out.println(k+"==="+v);
//        });
        return entry.toString();
    }


}