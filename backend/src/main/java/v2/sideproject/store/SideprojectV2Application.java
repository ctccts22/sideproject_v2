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
                title = "NEUROCHEMv3 REST API Documentation",
                description = "NEUROCHEMv3 REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "GYUNO LEE",
                        email = "GyunoLee@neurolines.net",
                        url = "https://www.neurochem.io"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.neurochem.io"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "NEUROCHEMv3 REST API Documentation",
                url = "https://www.NEUROCHEMv3.com/swagger-ui.html"
        )
)
public class SideprojectV2Application {

    public static void main(String[] args) {
        SpringApplication.run(SideprojectV2Application.class, args);
    }

}
