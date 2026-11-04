package Model;

import java.util.Random;

public class GeneradorRandom implements Generador {
    private Random random = new Random();

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }
}