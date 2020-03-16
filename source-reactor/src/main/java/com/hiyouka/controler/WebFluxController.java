package com.hiyouka.controler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/web")
public class WebFluxController {

    @GetMapping("/flux/get")
    public Flux<String> getFlux(){
        return Flux.just("1","2","3");
    }

}