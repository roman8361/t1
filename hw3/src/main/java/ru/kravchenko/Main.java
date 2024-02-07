package ru.kravchenko;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Попробуйте реализовать собственный пул потоков. В качестве аргументов конструктора пулу передается его
 * емкость (количество рабочих потоков). Как только пул создан, он сразу инициализирует и запускает потоки.
 * Внутри пула очередь задач на исполнение организуется через LinkedList<Runnable>. При выполнении у пула
 * потоков метода execute(Runnable r), указанная задача должна попасть в очередь исполнения, и как только
 * появится свободный поток – должна быть выполнена. Также необходимо реализовать метод shutdown(), после
 * выполнения которого новые задачи больше не принимаются пулом (при попытке добавить задачу можно бросать
 * IllegalStateException), и все потоки для которых больше нет задач завершают свою работу. Дополнительно
 * можно добавить метод awaitTermination() без таймаута, работающий аналогично стандартным пулам потоков
 */

public class Main {
    public static void main(String[] args) {
        // Создаем пул потоков с емкостью 3
        CustomThreadPool threadPool = new CustomThreadPool(3);

        // Добавляем задачи в пул потоков
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            Runnable task = () -> {
                System.out.println("Task " + taskId + " is running on thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // Эмулируем выполнение задачи
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            threadPool.execute(task);
        }

        // Ждем некоторое время, чтобы убедиться, что задачи выполняются
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Завершаем пул потоков после выполнения всех задач
        threadPool.shutdown();
    }
}