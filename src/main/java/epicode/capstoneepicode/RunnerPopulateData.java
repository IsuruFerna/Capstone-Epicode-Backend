package epicode.capstoneepicode;

import com.github.javafaker.Faker;
import epicode.capstoneepicode.payload.NewUserDTO;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerPopulateData implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

//        for(int i = 0; i < 10; i++) {
//
//        }

//        NewUserDTO u = new NewUserDTO(
//                faker.name().firstName(),
//                faker.name().lastName(),
//                faker.name().username(),
//                faker.internet().emailAddress(),
//                "1999-09-10",
//                "1234",
//                "1234"
//                );
//        userService.save(u);

//        NewUserDTO u = new NewUserDTO(
//                faker.name().firstName(),
//                faker.name().lastName(),
//                faker.name().username(),
//                faker.internet().emailAddress(),
//                "2003-05-24",
//                "1234",
//                "1234"
//                );
//        userService.save(u);
    }
}
