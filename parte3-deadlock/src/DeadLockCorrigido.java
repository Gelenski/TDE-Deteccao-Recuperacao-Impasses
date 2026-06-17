public class DeadLockCorrigido {

    static class Recurso {
    }

    public static void main(String[] args) {
        Recurso recursoA = new Recurso();
        Recurso recursoB = new Recurso();

        Thread t1 = new Thread(() -> {
            synchronized (recursoA) {
                System.out.println("Thread 1 pegou o Recurso A.");
                try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println(e.getMessage()); }

                System.out.println("Thread 1 pegou o Recurso B.");
                synchronized (recursoB) {
                    System.out.println("Thread 1 concluiu.");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (recursoA) {
                System.out.println("Thread 2 pegou o Recurso A.");
                try { Thread.sleep(50); } catch (InterruptedException e) { System.out.println(e.getMessage()); }

                System.out.println("Thread 2 pegou o Recurso B.");
                synchronized (recursoB) {
                    System.out.println("Thread 2 concluiu.");
                }
            }
        });

        System.out.println("Iniciando versão corrigida por hierarquia de recursos...");
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Execução finalizada sem deadlock.");
    }
}