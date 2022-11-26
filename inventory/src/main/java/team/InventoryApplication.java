package team;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class InventoryApplication {
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(InventoryApplication.class, args);
    }

    // @Bean
    // public Serializer buildSerializer() {
    //     return XStreamSerializer.builder()
    //                             .                        
    //                             .build();
    // }
}