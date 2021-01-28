



public class Sqrt {

    public static double sqrt(double x, int precision) {
        double low = 0;
        double high = x;
        if (0 < x && x < 1) {
            low = x;
            high = 1;
        }
        return sqrt(x, low, high, precision);
    }

    public static double sqrt(double x, double low, double high, int precision) {

        if (x < 0) {
            return Double.NaN;
        }
        if (low > high) {
            return -1;
        }

        BigDecimal lowB = new BigDecimal("" + low);
        BigDecimal highB = new BigDecimal("" + high);

        BigDecimal midB = lowB.add(highB.subtract(lowB).divide(new BigDecimal(2)));
        String midStr = midB.toString();
        int len = midStr.substring(midStr.indexOf(".") + 1).length();
        if (midB.multiply(midB).doubleValue() == x || len == precision) {
            return midB.doubleValue();
        } else if (midB.multiply(midB).doubleValue() > x) {
            return sqrt(x, low, midB.doubleValue(), precision);
        } else {
            return sqrt(x, midB.doubleValue(), high, precision);
        }
    }

    public static void main(String[] args) {
        System.out.println(sqrt(9, 12));
    }


}
