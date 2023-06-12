import java.math.BigInteger;
import java.util.Random;

public class StringHash implements HashFactory<String> {

    public StringHash() {}

    @Override
    public HashFunctor<String> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;
        public Functor(int k){
            carterWegmanHash = new ModularHash().pickHash(k);
            c = genC();
            q = genQ();
        }

        @Override
        public int hash(String key) {
            int k = key.length();
            long sum = 0;
            for (int i = 0; i < k; i++) {
                char xi = key.charAt(i);
                long exponent = HashingUtils.modPow(c, k-i, q);
                long mappedValue = HashingUtils.multiplyMod(xi, exponent, q);
                sum += mappedValue;
            }
            int hashValue = (int)HashingUtils.mod(sum, q);
            return carterWegmanHash.hash(hashValue);
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
        private int genQ(){
            var hashFunctions = new HashingUtils();
            int q = 0;
            boolean gen = false;
            while (!gen){
                while (!gen){
                    q = hashFunctions.genUniqueIntegers(1)[0];
                    if (q%2 == 1 && q > Integer.MAX_VALUE/2) gen = true;
                }
                gen = hashFunctions.runMillerRabinTest(q,50);
            }
            return q;
        }
        private int genC(){
            var hashFunctions = new HashingUtils();
            int c = 0;
            boolean gen = false;
            while (!gen){
                while (!gen){
                    c = hashFunctions.genUniqueIntegers(1)[0];
                    if (c>1 && c<q) gen = true;
                }
            }
            return c;
        }
    }
}
