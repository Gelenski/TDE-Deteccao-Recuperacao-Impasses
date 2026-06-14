public class JantarDeadLock {

    static class Garfo {
    }

    static class Filosofo extends Thread {
        private final String nome;
        private final Garfo esquerdo;
        private final Garfo direito;

        public Filosofo(String nome, Garfo esquerdo, Garfo direito) {
            this.nome = nome;
            this.esquerdo = esquerdo;
            this.direito = direito;
        }

        private void pensar() throws InterruptedException {
            System.out.println(this.nome + "  está pensando.");
            Thread.sleep(800);
        }

        private void comer() throws InterruptedException {
            System.out.println(this.nome + "  está comendo.");
            Thread.sleep(800);
        }

        @Override
        public void run() {
            try {
                while (true) {
                    pensar();
                    System.out.println(this.nome + " está faminto.");

                    /* Aqui o filosofo tenta pegar o garfo a sua esquerda
                     *  O synchronized faz com que apenas uma Thread use o garfo por vez
                     */
                    synchronized (esquerdo) {
                        System.out.println(this.nome + " pegou o garfo do seu lado esquerdo.");
                        Thread.sleep(800);

                        /* Aqui o filosofo tenta pegar o garfo direito */
                        synchronized (direito) {
                            System.out.println(this.nome + " pegou o garfo do seu lado direito.");
                            comer();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static void main(String[] args) {
        Garfo[] garfos = new Garfo[5];

        for (int i = 0; i < garfos.length; i++) {
            garfos[i] = new Garfo();
        }

        Filosofo a = new Filosofo("Kant", garfos[0], garfos[1]);
        Filosofo b = new Filosofo("Aristóteles", garfos[1], garfos[2]);
        Filosofo c = new Filosofo("Platão", garfos[2], garfos[3]);
        Filosofo d = new Filosofo("Bollas", garfos[3], garfos[4]);
        Filosofo e = new Filosofo("Boécio", garfos[4], garfos[0]);

        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
    }
}
