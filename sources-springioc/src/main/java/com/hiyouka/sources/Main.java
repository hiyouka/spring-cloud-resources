package com.hiyouka.sources;

import com.hiyouka.sources.config.MainConfig;
import com.hiyouka.sources.core.annocation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class   //禁用springboot自动配置单数据源
})

@ComponentScan({"com.hiyouka.sources.config","com.hiyouka.sources.util"})
public class Main {

	@Bean
	public MainConfig mainConfig(){
		return new MainConfig();
	}

	public static void main(String[] args)
	{
		SpringApplication.run(Main.class, args
		);
	}

}
