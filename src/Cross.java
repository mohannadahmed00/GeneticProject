import java.util.Arrays;

class Cross {
    private int a, b;
    private double pc;
    private int[] wheel;

    Cross(double[] rate, double pc) {
        this.wheel = wheel(rate);
        this.a = wheel[Main.getRandomInt(wheel.length) - 1];
        this.b = B(a);
        this.pc = pc;
    }

    int getA() {
        return a;
    }

    int getB() {
        return b;
    }

    double getPc() {
        return pc;
    }

    private int B(int a) {
        int b = wheel[Main.getRandomInt(wheel.length) - 1];
        while (b == a) {
            b = wheel[Main.getRandomInt(wheel.length) - 1];
        }
        return b;
    }

    void printWheel() {
        System.out.print("Wheel :");
        for (int i = 0; i < wheel.length; i++) {
            if (i == 0) {
                System.out.print("(" + wheel[i]);
            } else if (i == wheel.length - 1) {
                System.out.println(", " + wheel[i] + ")");
            } else {
                System.out.print(", " + wheel[i]);
            }
        }
    }

    private static int index(double[] x, double s) {
        int y = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] == s) {
                y = i + 1;
                break;
            }
        }
        return y;
    }

    private static int[] sortRate(double[] x) {
        double[] sorted = x.clone();
        Arrays.sort(sorted);
        int[] z = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            z[i] = index(x, sorted[i]);
        }
        return z;
    }


    private static int factorial(int x) {
        int result = 0;
        for (int i = 0; i < x; i++) {
            result = result + (i + 1);
        }
        return result;
    }

    private static int[] wheel(double[] x) {

        int[] rate = sortRate(x);
        int[] w = new int[factorial(x.length)];


        for (int i = 0; i < rate.length; i++) {
            for (int j = 0; j < factorial(i + 1); j++) {
                if (w[j] == 0) {
                    w[j] = rate[i];
                }
            }
        }
        for (int i = 0; i < w.length - 1; i++) {
            int random = Main.getRandomInt(w.length) - 1;
            int temp = w[i];
            w[i] = w[random];
            w[random] = temp;
        }
        return w;
    }
}
