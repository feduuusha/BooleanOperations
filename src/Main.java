import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        StringConverter gen = new StringConverter("(!x1 || x2) && x3");

        gen.output();
    }

}