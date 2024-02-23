package epicode.capstoneepicode;

import com.github.javafaker.Faker;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.user.NewUserDTO;
import epicode.capstoneepicode.service.AuthService;
import epicode.capstoneepicode.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RunnerPopulateData implements CommandLineRunner {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

//        postService.deleteMedia(UUID.fromString("0fcffee8-a1ad-4caf-8497-abc3676ea77e"));

        // creates users
//        for(int i = 0; i < 10; i++) {
//            NewUserDTO u = new NewUserDTO(
//                    faker.name().firstName(),
//                    faker.name().lastName(),
//                    faker.name().username(),
//                    faker.internet().emailAddress(),
//                    "1999-09-10",
//                    "1234",
//                    "1234"
//            );
//            User saved = authService.save(u);

//
//            // creates random posts for random users
////            Random rnd = new Random();
////            if(rnd.nextBoolean()) {
////                int posts = rnd.nextInt(0, 5);
////                for (int j = 0; j < posts; j++) {
////                    NewPostDTO p = new NewPostDTO(
////                            "sample text",
////                            null,
////                            ("d9958f17-a9cb-46ca-b09d-e50302a12aea")
////                    );
////                    postService.save(p);
////                }
////            }
//
//
//        }
    }
}
