import org.junit.jupiter.api.Test;
import ru.kravchenko.CustomThreadPool;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomThreadPoolTest {

    @Test
    public void testThreadPoolExecution(){
        CustomThreadPool threadPool = new CustomThreadPool(3);
        // Добавляем задачи в пул потоков
        for (int i = 0; i < 5; i++) {
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

        // Проверяем, что все задачи были выполнены
        try {
            Thread.sleep(6000); // Ожидаем 6 секунд для выполнения задач
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Завершаем пул потоков после выполнения всех задач
        threadPool.shutdown();
    }

    @Test
    public void testThreadPoolShutdown() {
        CustomThreadPool threadPool = new CustomThreadPool(2);

        // Добавляем одну задачу в пул потоков
        Runnable task = () -> {
            System.out.println("Task is running on thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // Эмулируем выполнение задачи
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        threadPool.execute(task);

        // Завершаем пул потоков и добавляем новую задачу
        threadPool.shutdown();

        assertThrows(IllegalStateException.class, () -> threadPool.execute(task));
    }
}
