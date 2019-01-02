package pl.ostrowski.loan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build()
                    .pathMapping("/")
                    .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        Contact contact = new Contact("Kacper Ostrowski",
                "https://github.com/kacper-j-ostrowski/loan/blob/develop/README.md",
                "kacper.j.ostrowski@gmail.com");
        return new ApiInfoBuilder()
                .contact(contact)
                .title("Loan application")
                .description("Interview app")
                .version("0.1")
                .license("n/a")
                .build();

    }

}
