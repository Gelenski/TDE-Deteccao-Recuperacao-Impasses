public class SemSemaforo {
    private static int contador = 0;

    public static void main(String[] args) throws InterruptedException {
        int numeroThreads = 8;
        int incrementos = 200000;

        int valorEsperado = numeroThreads * incrementos;

        Thread[] threads = new Thread[numeroThreads];

        long tempoInicial = System.nanoTime();

        for (int i = 0; i < numeroThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementos; j++) {
                    contador++;
                }
            });
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long tempoFinal = System.nanoTime();

        long tempoTotalNanos = tempoFinal - tempoInicial;
        double tempoTotalMilissegundos = tempoTotalNanos / 1_000_000.0;

        System.out.println("Valor esperado: " + valorEsperado);
        System.out.println("Valor obtido: " + contador);
        System.out.printf("Tempo de execução: %.3f ms%n", tempoTotalMilissegundos);
    }
}