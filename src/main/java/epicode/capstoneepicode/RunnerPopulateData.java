package epicode.capstoneepicode;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerPopulateData implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

    }
}
