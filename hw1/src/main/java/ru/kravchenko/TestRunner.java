package ru.kravchenko;


import ru.kravchenko.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static void runTests(Class<?> c) {
        try {
            Object instance = c.getDeclaredConstructor().newInstance();

            // Получаем все методы класса
            Method[] methods = c.getDeclaredMethods();

            // Фильтруем методы по аннотациям
            List<Method> beforeSuiteMethods = filterMethods(methods, BeforeSuite.class);
            List<Method> afterSuiteMethods = filterMethods(methods, AfterSuite.class);
            List<Method> testMethods = new ArrayList<>();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Test.class)) {
                    int priority = method.getAnnotation(Test.class).priority();
                    if (priority < 1 || priority > 10) {
                        throw new IllegalArgumentException("Test priority must be between 1 and 10.");
                    }
                    testMethods.add(method);
                }
            }
            // Проверяем, что метод с аннотацией BeforeSuite только один
            if (beforeSuiteMethods.size() > 1) {
                throw new RuntimeException("More than one method annotated with @BeforeSuite");
            }

            // Проверяем, что метод с аннотацией AfterSuite только один
            if (afterSuiteMethods.size() > 1) {
                throw new RuntimeException("More than one method annotated with @AfterSuite");
            }

            // Выполняем метод с аннотацией BeforeSuite, если такой есть
            if (!beforeSuiteMethods.isEmpty()) {
                beforeSuiteMethods.get(0).invoke(instance);
            }

            // Сортируем методы с аннотацией Test по их приоритету
            testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));

            // Выполняем методы с аннотацией Test
            for (Method testMethod : testMethods) {
                testMethod.invoke(instance);
            }

            // Выполняем метод с аннотацией AfterSuite, если такой есть
            if (!afterSuiteMethods.isEmpty()) {
                afterSuiteMethods.get(0).invoke(instance);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static List<Method> filterMethods(Method[] methods, Class<? extends Annotation> annotation) {
        List<Method> filteredMethods = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                filteredMethods.add(method);
            }
        }
        return filteredMethods;
    }
}
