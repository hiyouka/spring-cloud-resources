package com.hiyouka.sources;

import com.hiyouka.sources.config.test.ClassUtils;
import com.hiyouka.sources.constant.LogoConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;
import java.net.URL;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class   //禁用springboot自动配置单数据源
})

//@ComponentScan({"com.hiyouka.sources.config","com.hiyouka.sources.util"})
public class Main implements LogoConstant{


//	@Bean
//	public MainConfig mainConfig(){
//		return new MainConfig();
//	}

	public static void main(String[] args) throws IOException {
//        FileUtils.getFileContent("C:\\Users\\20625\\Desktop\\666.xlsx");
//		SpringApplication.run(Main.class, args);
		ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
		URL resource = defaultClassLoader.getResource("com/hiyouka/sources");
		System.out.println(resource);
	}

}
