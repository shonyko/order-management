package org.alexk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clasa principala
 */
@SpringBootApplication
public class Main {

    /**
     * Metoda care marcheaza punctul de start al aplicatiei
     * @param args argumentele aplicatiei
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
