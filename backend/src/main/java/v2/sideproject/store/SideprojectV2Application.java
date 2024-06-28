package v2.sideproject.store;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "SideprojectV2 REST API Documentation",
                description = "SideprojectV2 REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "GYUNO LEE",
                        email = "ctccts22@gmail.com",
                        url = "https://www.glbras.store"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.glbras.store"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "SideprojectV2 REST API Documentation",
                url = "https://www.glbras.store/swagger-ui.html"
        )
)
public class SideprojectV2Application {

    public static void main(String[] args) {
        SpringApplication.run(SideprojectV2Application.class, args);
    }

}
