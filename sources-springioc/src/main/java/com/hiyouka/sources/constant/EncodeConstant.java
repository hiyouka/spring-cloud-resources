package com.hiyouka.sources.constant;

import com.hiyouka.sources.exception.FileNotFoundException;
import org.springframework.context.annotation.Bean;

/**
 * @author hiyouka
 * Date: 2019/2/12
 * @since JDK 1.8
 */
public interface EncodeConstant {

    String UTF_8 = "utf-8";

    @Bean
    default FileNotFoundException fileNotFoundException(){
        return new FileNotFoundException();
    }

}