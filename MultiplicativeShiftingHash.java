import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    public MultiplicativeShiftingHash() {}

    public HashFunctor<Long> pickHash(int k) {
       return new Functor(k);
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;
        public Functor(int k){
            a = genA();
            this.k = (long)k;
        }

        @Override
        public int hash(Long key) {
            return (int) ((a*key)>>>(WORD_SIZE-k));
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
        private int genA(){
            var hashFunctions = new HashingUtils();
            Integer[] randomInt = null;
            boolean gen = false;
            while (!gen){
                randomInt = hashFunctions.genUniqueIntegers(1);
                gen = true;
                if (randomInt[0] < 2){
                    gen = false;
                }
            }
            return randomInt[0];
        }
    }
}
