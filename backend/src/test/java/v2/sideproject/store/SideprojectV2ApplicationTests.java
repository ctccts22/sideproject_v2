package v2.sideproject.store;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class SideprojectV2ApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("a" + UUID.randomUUID().toString());
        System.out.println("b" + UUID.randomUUID().toString());
        System.out.println("c" + UUID.randomUUID().toString());
    }

}
