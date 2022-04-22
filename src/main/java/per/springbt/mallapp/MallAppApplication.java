package per.springbt.mallapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = "per.springbt.mallapp.model.dao")
@EnableSwagger2
@EnableCaching
public class MallAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallAppApplication.class, args);
    }
}