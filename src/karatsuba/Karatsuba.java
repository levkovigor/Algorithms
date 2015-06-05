/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatsuba;

/**
 *
 * @author igor
 */
import java.math.BigInteger;

public class Karatsuba {

    public static int counter = 0;
    public static final String NUMBER = "12";
    public static final String NUMBER1 = "1685287499328328297814655639278583667919355849391453456921116729";
    public static final String NUMBER2 = "7114192848577754587969744626558571536728983167954552999895348492";

    public static BigInteger karatsuba(BigInteger x, BigInteger y) {

        String x1 = x.toString();
        String y1 = y.toString();

        int N = x1.length();
        int M = y1.length();

        if ((N == 1) && (M == 1)) {
            return x.multiply(y);
        }

        if ((N % 2) != 0) {
            x1 = "0" + x1;
            N++;
        }

        if ((M % 2) != 0) {
            y1 = "0" + y1;
            M++;
        }

        if (N > M) {
            for (int i = 1; i <= (N - M); i++) {
                y1 = "0" + y1;
            }
            M = y1.length();
        }

        if (N < M) {
            for (int i = 1; i <= (M - N); i++) {
                x1 = "0" + x1;
            }
            N = x1.length();
        }

        BigInteger a = new BigInteger(x1.substring(0, N / 2));
        BigInteger b = new BigInteger(x1.substring(N / 2, N));
        BigInteger c = new BigInteger(y1.substring(0, M / 2));
        BigInteger d = new BigInteger(y1.substring(M / 2, M));

        BigInteger ac = karatsuba(a, c);
        BigInteger bd = karatsuba(b, d);
        BigInteger abcd = karatsuba(a.add(b), c.add(d));
        BigInteger adbc = abcd.subtract(ac).subtract(bd);

        if (adbc.toString().equals(NUMBER)) {
            counter++;
        }

        BigInteger C1 = new BigInteger("10");

        for (int i = 1; i <= N; i++) {
            ac = ac.multiply(C1);
        }

        for (int i = 1; i <= (N / 2); i++) {
            adbc = adbc.multiply(C1);
        }

        return ac.add(adbc).add(bd);
    }

    public static void main(String[] args) {
        BigInteger a = new BigInteger(NUMBER1);
        BigInteger b = new BigInteger(NUMBER2);
        BigInteger c = karatsuba(a, b);
        System.out.println(c);
        System.out.println(counter);
    }
}
