package dev.sagar.wordsmith;

import org.springframework.boot.SpringApplication;

public class TestWordsmithApplication {

    public static void main(String[] args) {
        SpringApplication.from(WordsmithApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
