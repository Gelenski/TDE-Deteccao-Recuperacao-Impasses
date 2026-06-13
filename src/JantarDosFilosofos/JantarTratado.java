package JantarDosFilosofos;

import java.util.concurrent.Semaphore;

public class JantarTratado {

    static class Garfo {
    }

    static class Filosofo extends Thread {

        private final String nome;
        private final Garfo esquerdo;
        private final Garfo direito;

        private final Semaphore garcom;

        public Filosofo(String nome, Garfo esquerdo, Garfo direito, Semaphore garcom) {
            this.nome = nome;
            this.esquerdo = esquerdo;
            this.direito = direito;
            this.garcom = garcom;
        }

        // Ações dos Filósofos

        private void pensar() throws InterruptedException {
            System.out.println(this.nome + "  está pensando.");
            Thread.sleep(800);
        }

        private void comer() throws InterruptedException {
            System.out.println(this.nome + "  está comendo.");
            Thread.sleep(800);
        }

        // Execução da Thread

        @Override
        public void run() {
            try {
                while (true) {
                    pensar();
                    System.out.println(this.nome + " está faminto.");

                    garcom.acquire();
                    try {
                        /* Aqui o filosofo pega o garfo esquerdo */
                        synchronized (esquerdo) {
                            System.out.println(this.nome + " pegou o garfo do seu lado esquerdo.");

                            /* Aqui o filosofo pega o garfo direito */
                            synchronized (direito) {
                                System.out.println(this.nome + " pegou o garfo do seu lado direito.");
                                comer();
                            }
                        }
                        System.out.println(this.nome + " largou os garfos.");
                    } finally {
                        garcom.release();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Garfo[] garfos = new Garfo[5];

        for (int i = 0; i < garfos.length; i++) {
            garfos[i] = new Garfo();
        }

        /* Semaphore com 4 permissões para 5 filósofos
         *  true ativa a ordem FIFO
         */
        Semaphore garcom = new Semaphore(4, true);

        Filosofo a = new Filosofo("Kant", garfos[0], garfos[1], garcom);
        Filosofo b = new Filosofo("Aristóteles", garfos[1], garfos[2], garcom);
        Filosofo c = new Filosofo("Platão", garfos[2], garfos[3], garcom);
        Filosofo d = new Filosofo("Bollas", garfos[3], garfos[4], garcom);
        Filosofo e = new Filosofo("Boécio", garfos[4], garfos[0], garcom);

        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
    }
}
