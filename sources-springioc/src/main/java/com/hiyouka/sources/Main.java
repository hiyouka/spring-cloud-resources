package com.hiyouka.sources;

import com.hiyouka.sources.constant.LogoConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class   //禁用springboot自动配置单数据源
})

//@ComponentScan({"com.hiyouka.sources.config","com.hiyouka.sources.util"})
public class Main implements LogoConstant{


//	@Bean
//	public MainConfig mainConfig(){
//		return new MainConfig();
//	}

	public static void main(String[] args) throws IOException, URISyntaxException {
//        FileUtils.getFileContent("C:\\Users\\20625\\Desktop\\666.xlsx");
//		SpringApplication.run(Main.class, args);
//		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main.class);
//		ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
//		URL resource = defaultClassLoader.getResource("com/hiyouka/sources");
//		System.out.println(resource);

//		File file = ResourceUtils.getFile("file:limit.lua");
//			InputStream is = RedisType.class.getClass().getResourceAsStream("/limit.lua");
//		int i;
//		while ((i = is.read()) != -1){
//			System.out.print((char)i);
////		}
//		System.out.println(System.currentTimeMillis()/1000);
//		InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream("limit.lua");
//		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("limit.lua");
//		URL url = ResourceUtils.getURL("/limit.lua");
//		String path = url.getPath();
//		File file = new File(path);
//		File file = FileUtils.getFile("limit.lua");
//		FileUtils.getFile()
		URL url = ResourceUtils.getURL("classpath:limit.lua");
		String path = url.getPath();
		String protocol = url.getProtocol();
		String file = url.getFile();
		System.out.println(url.getPath());
		URL url1 = extractArchiveURL(url);
		System.out.println(url1.getPath());
		URL url2 = ResourceUtils.extractArchiveURL(url);
		System.out.println(url2.getFile());
		File jar_url = ResourceUtils.getFile(url2, "Jar URL");
		JarFile jarFile = new JarFile(jar_url);
		InputStream inputStream = jarFile.getInputStream(jarFile.getEntry("limit.lua"));
//		URL resource = Thread.currentThread().getContextClassLoader().getResource("/limit.lua");
//		File file = new File(resource.toURI());
//		InputStream inputStream = new FileInputStream(file);
		int i;
		while ((i = inputStream.read()) != -1){
			System.out.print((char)i);
		}
		System.out.println("=============");

//		ResourceUtils.
//		InputStream in = new FileInputStream(file);


	}


	public static final String JAR_URL_SEPARATOR = "!/";

	public static final String FILE_URL_PREFIX = "file:";
	public static final String WAR_URL_SEPARATOR = "*/";
	public static final String URL_PROTOCOL_WAR = "war";
	public static final String WAR_URL_PREFIX = "war:";

	private static URL extractArchiveURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile();

		int endIndex = urlFile.indexOf(WAR_URL_SEPARATOR);
		if (endIndex != -1) {
			// Tomcat's "war:file:...mywar.war*/WEB-INF/lib/myjar.jar!/myentry.txt"
			String warFile = urlFile.substring(0, endIndex);
			if (URL_PROTOCOL_WAR.equals(jarUrl.getProtocol())) {
				return new URL(warFile);
			}
			int startIndex = warFile.indexOf(WAR_URL_PREFIX);
			if (startIndex != -1) {
				return new URL(warFile.substring(startIndex + WAR_URL_PREFIX.length()));
			}
		}
		// Regular "jar:file:...myjar.jar!/myentry.txt"
		return extractJarFileURL(jarUrl);
	}

	private static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile();
		int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
		if (separatorIndex != -1) {
			String jarFile = urlFile.substring(0, separatorIndex);
			try {
				return new URL(jarFile);
			}
			catch (MalformedURLException ex) {
				// Probably no protocol in original jar URL, like "jar:C:/mypath/myjar.jar".
				// This usually indicates that the jar file resides in the file system.
				if (!jarFile.startsWith("/")) {
					jarFile = "/" + jarFile;
				}
				return new URL(FILE_URL_PREFIX + jarFile);
			}
		}
		else {
			return jarUrl;
		}
	}

}
