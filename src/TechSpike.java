import java.util.Scanner;

public class TechSpike {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = input.nextLine();

        System.out.println("You entered: " + text);

        input.close();
    }
}