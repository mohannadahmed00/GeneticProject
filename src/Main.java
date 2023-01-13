//mohannad ahmed 160164 CS4 task1
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        int counter = 0;
        int bestNext = 0;
        int iteration = 5;
        int[] x = {2, 8, 13, 40, 19, 22};


        /*int bestGene = largestInArr(x);
        while (bestNext <= bestGene) {
            if (largestInArr(x)==0){
                break;
            }
            x = geneticAlgorithm(x);
            bestNext = largestInArr(x);
            counter++;
        }
*/
        for (int i = 0; i < iteration; i++) {
            x = geneticAlgorithm(x);
            bestNext = largestInArr(x);
            counter++;
        }
        System.out.println(counter + " Iteration");
        System.out.println("The best gene :" + bestNext);
    }


    private static int[] geneticAlgorithm(int[] x) {
        double pc = 0.7, pm = 0.05;
        int largestBinary = 0;

        int[] newX = new int[x.length];
        String[] binary = new String[x.length];
        double[] rate = new double[x.length];


        int fit = 0;
        String[] mixed1 = new String[2];
        String[] mixed2 = new String[2];
        String[] mixed3 = new String[2];
        String[] crossOver;
        int[] crossedIndex = new int[x.length];
        double[] probabilityMutation = new double[x.length];

        System.out.println("PC=" + pc + "      PM=" + pm);
        printColumn("x", x);
        for (int i = 0; i < x.length; i++) {
            binary[i] = Integer.toBinaryString(x[i]);
            if (binary[i].length() > largestBinary) {
                largestBinary = binary[i].length();
            }
            x[i] = x[i] * x[i];
            fit = fit + x[i];
        }
        printColumn("x Square", x);


        System.out.println("Largest Binary:" + largestBinary);
        System.out.println("Fitness:" + fit);


        for (int i = 0; i < binary.length; i++) {
            if (binary[i].length() != largestBinary) {
                int zeros = largestBinary - binary[i].length();
                for (int j = 0; j < zeros; j++) {
                    binary[i] = "0" + binary[i];
                }
            }
        }
        printColumn("Binary", binary);


        for (int i = 0; i < x.length; i++) {
            rate[i] = (double) x[i] / fit;
        }
        printColumn("Rate", rate);


        Cross cross1 = new Cross(rate, getRandomDouble(1));
        Cross cross2 = new Cross(rate, getRandomDouble(1));
        Cross cross3 = new Cross(rate, getRandomDouble(1));
        cross3.printWheel();

        System.out.println("cross1: " + cross1.getA() + " " + cross1.getB() + " " + new DecimalFormat("##.###").format(cross1.getPc()));
        System.out.println("cross2: " + cross2.getA() + " " + cross2.getB() + " " + new DecimalFormat("##.###").format(cross2.getPc()));
        System.out.println("cross3: " + cross3.getA() + " " + cross3.getB() + " " + new DecimalFormat("##.###").format(cross3.getPc()));


        if (cross1.getPc() < pc) {
            mixed1 = mix(binary[cross1.getA() - 1], binary[cross1.getB() - 1]);
            crossedIndex[0] = cross1.getA();
            crossedIndex[1] = cross1.getB();
        }
        if (cross2.getPc() < pc) {
            mixed2 = mix(binary[cross2.getA() - 1], binary[cross2.getB() - 1]);
            crossedIndex[2] = cross2.getA();
            crossedIndex[3] = cross2.getB();
        }
        if (cross3.getPc() < pc) {
            mixed3 = mix(binary[cross3.getA() - 1], binary[cross3.getB() - 1]);
            crossedIndex[4] = cross3.getA();
            crossedIndex[5] = cross3.getB();
        }
        crossOver = combine(mixed1, mixed2, mixed3);
        for (int i = 0; i < crossOver.length; i = i + 2) {
            if (crossOver[i] == null) {
                if (i + 1 == 1 || i + 1 == 2) {
                    crossOver[i] = binary[cross1.getA() - 1];
                    crossOver[i + 1] = binary[cross1.getB() - 1];
                } else if (i + 1 == 3 || i + 1 == 4) {
                    crossOver[i] = binary[cross2.getA() - 1];
                    crossOver[i + 1] = binary[cross2.getB() - 1];
                } else if (i + 1 == 5 || i + 1 == 6) {
                    crossOver[i] = binary[cross3.getA() - 1];
                    crossOver[i + 1] = binary[cross3.getB() - 1];
                }
            }
        }

        printColumn("cross Over", crossOver);

        for (int i = 0; i < probabilityMutation.length; i++) {
            probabilityMutation[i] = getRandomDouble(0.1);
            if (probabilityMutation[i] < pm) {
                crossOver[i] = changeRandom(crossOver[i]);
            }
        }

        printColumn("Probability Mutation", probabilityMutation);
        printColumn("Mutation", crossOver);


        for (int i = 0; i < newX.length; i++) {
            newX[i] = binaryToDec(crossOver[i]);
        }
        printColumn("New x", crossOver);


        System.out.println("------------------------------------------------------------------------------------fine------------------------------------------------------------------------------------");
        return newX;
    }

    static int getRandomInt(int x) {
        return (int) ((Math.random() * x) + 1);
    }

    private static double getRandomDouble(double x) {
        return Math.random() * x;
    }

    private static String[] mix(String a, String b) {
        String[] mixed = new String[2];
        int random = getRandomInt(a.length() - 1);
        String a1, a2, b1, b2;
        a1 = a.substring(0, a.length() - random);
        a2 = a.substring(a.length() - random);
        b1 = b.substring(0, b.length() - random);
        b2 = b.substring(b.length() - random);
        mixed[0] = a1 + b2;
        mixed[1] = b1 + a2;
        return mixed;
    }

    private static int binaryToDec(String x) {
        return Integer.parseInt(x, 2);
    }

    private static String changeRandom(String x) {
        int r = getRandomInt(x.length()) - 1;
        String[] c = x.split("");
        if (c[r].equals("1")) {
            c[r] = "0";
        } else {
            c[r] = "1";
        }
        return arrayToString(c);
    }

    private static String arrayToString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : array) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    private static String[] combine(String[] a, String[] b, String[] c) {
        int length = a.length + b.length + c.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        System.arraycopy(c, 0, result, a.length + b.length, c.length);
        return result;
    }

    private static int largestInArr(int[] x) {
        int largest = 0;
        for (int i : x) {
            if (i > largest) {
                largest = i;
            }
        }
        return largest;
    }

    private static void printColumn(String s, int[] x) {
        System.out.print(s + " :");
        for (int i = 0; i < x.length; i++) {
            if (i == 0) {
                System.out.print("(" + x[i]);
            } else if (i == x.length - 1) {
                System.out.println(", " + x[i] + ")");
            } else {
                System.out.print(", " + x[i]);
            }
        }
    }

    private static void printColumn(String s, String[] x) {
        System.out.print(s + " :");
        for (int i = 0; i < x.length; i++) {
            if (i == 0) {
                System.out.print("(" + x[i]);
            } else if (i == x.length - 1) {
                System.out.println(", " + x[i] + ")");
            } else {
                System.out.print(", " + x[i]);
            }
        }
    }

    private static void printColumn(String s, double[] x) {
        System.out.print(s + " :");
        for (int i = 0; i < x.length; i++) {
            if (i == 0) {
                System.out.print("(" + new DecimalFormat("##.###").format(x[i]));
            } else if (i == x.length - 1) {
                System.out.println(", " + new DecimalFormat("##.###").format(x[i]) + ")");
            } else {
                System.out.print(", " + new DecimalFormat("##.###").format(x[i]));
            }
        }
    }


}

