import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    public ModularHash() {}
    @Override
    public HashFunctor<Integer> pickHash(int k) {
        if (k>30 || k<0){
            throw new IllegalArgumentException("only for 0<=k< 31");
        }
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;
        public Functor(int k){
            a = genA();
            b = genB();
            p = genP();
            m = genM(k);
        }
        @Override
        public int hash(Integer key) {
            return HashingUtils.mod((int)HashingUtils.mod((a*key+b),p),m);
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
        private int genA(){
            var hashFunctions = new HashingUtils();
            Integer[] randomInt = null;
            boolean gen = false;
            while (!gen){
                randomInt = hashFunctions.genUniqueIntegers(1);
                gen = true;
                if (randomInt[0] < 1  || randomInt[0] == Integer.MAX_VALUE){
                    gen = false;
                }
            }
            return randomInt[0];
        }
        private int genB(){
            var hashFunctions = new HashingUtils();
            Integer[] randomInt = null;
            boolean gen = false;
            while (!gen){
                randomInt = hashFunctions.genUniqueIntegers(1);
                gen = true;
                if (randomInt[0] < 0  || randomInt[0] == Integer.MAX_VALUE){
                    gen = false;
                }
            }
            return randomInt[0];
        }
        private int genM(int k){
            return (int) Math.pow(2, k);
        }
        private long genP(){
            var hashFunctions = new HashingUtils();
            long p = 0;
            boolean gen = false;
            while (!gen){
                while (!gen){
                    p = hashFunctions.genLong(((long)Integer.MAX_VALUE)+1, Long.MAX_VALUE);
                    if(p%2 == 1) gen = true;
                }
                gen = hashFunctions.runMillerRabinTest(p,50);
            }
            return p;
        }
    }
}
