package Lab3;

import java.util.Scanner;

public class Main {
    private static final double A = 0, B = Math.PI/3;
    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        IntegralCalculator integralCalculator = new IntegralCalculator();

        System.out.println("Enter the number of split intervals and the number of threads with a space\ne - exit");
        while (true){
            String s = in.nextLine();
            if (s.equals("e")) break;
            else {
                int n = Integer.parseInt(s.split(" ")[0]);
                int threadsAmount = Integer.parseInt(s.split(" ")[1]);
                System.out.println(integralCalculator.countIntegral(A, B, n, threadsAmount));
                System.out.println("The value of the integral in a single-threaded program: " + countIntegral(A, B, n));
            }
        }
    }

    private static double countIntegral(double a, double b, int n) {
        double h = (b - a) / n;
        double integral = 0;

        for (int i = 1; i < n; i++) {
            double xi = a + i * h;
            double fxi = Function.func(xi);
            integral += fxi;
        }
        integral = h * ((Function.func(A) + Function.func(B)) / 2 + integral);

        return integral;
    }
}
