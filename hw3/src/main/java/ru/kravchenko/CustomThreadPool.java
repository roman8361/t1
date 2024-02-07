package ru.kravchenko;

import java.util.LinkedList;

public class CustomThreadPool {
    private final int capacity;
    private final LinkedList<Runnable> taskQueue;
    private final PoolWorker[] workers;

    private boolean isShutdown = false;

    public CustomThreadPool(int capacity) {
        this.capacity = capacity;
        taskQueue = new LinkedList<>();
        workers = new PoolWorker[capacity];

        for (int i = 0; i < capacity; i++) {
            workers[i] = new PoolWorker();
            workers[i].start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is already shut down");
        }

        taskQueue.addLast(task);
        notify(); // Уведомляем один из ожидающих потоков, что задача доступна.
    }

    public synchronized void shutdown() {
        isShutdown = true;
        for (PoolWorker worker : workers) {
            worker.interrupt(); // Прерываем все рабочие потоки, чтобы завершить их работу.
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (CustomThreadPool.this) {
                    // Ждем, пока не станет доступна новая задача или пока не будет прерван поток.
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            CustomThreadPool.this.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }

                    // Если поток был прерван или пул завершен и задач больше нет, завершаем выполнение.
                    if (isShutdown && taskQueue.isEmpty()) {
                        return;
                    }

                    // Получаем следующую задачу из очереди.
                    task = taskQueue.removeFirst();
                }

                // Выполняем задачу вне синхронизированного блока, чтобы другие потоки могли добавлять задачи.
                try {
                    task.run();
                } catch (RuntimeException e) {
                    // Обработка исключений, например, логирование исключения.
                }
            }
        }
    }
}
