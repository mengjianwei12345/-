



class diffiehellman {
    

    public static void main(String[] args) {

        double P, G, a, b;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Number P: ");
        P = scanner.nextDouble();
        System.out.println("Enter Number G: ");
        G = scanner.nextDouble();
        System.out.println("Enter Number a: ");
        a = scanner.nextDouble();
        System.out.println("Enter Number b: ");
        b = scanner.nextDouble();
        scanner.close();

        double x, y;

        x = power(G, a, P);
        y = power(G, b, P);

        System.out.println(x);
        System.out.println(y);

        
    }


    private static double power(double a, double b, double p) {

        if (b == 1) 
            return a; 
        else
            return ((Math.pow(a, b)) % p); 

    }


}
