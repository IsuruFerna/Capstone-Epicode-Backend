package epicode.capstoneepicode;

import com.github.javafaker.Faker;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.user.NewUserDTO;
import epicode.capstoneepicode.service.AuthService;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RunnerPopulateData implements CommandLineRunner {

    @Autowired
    private AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for(int i = 0; i < 10; i++) {
            NewUserDTO u = new NewUserDTO(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.name().username(),
                    faker.internet().emailAddress(),
                    "1999-09-10",
                    "1234",
                    "1234"
                    );
            User saved = authService.save(u);

            Random rnd = new Random();
            System.out.println("this is random state: " + rnd.nextBoolean());

        }
    }
}
