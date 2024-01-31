import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StreamTest {
    /**
     * Реализуйте удаление из листа всех дубликатов
     */
    @Test()
    public void test01() {
        final var originalList = Arrays.asList(1, 2, 3, 4, 2, 3, 5, 6, 4, 7, 8, 9, 1);
        final var expectedList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final var uniqueList = originalList.stream()
                .distinct().collect(Collectors.toList());

        assertEquals(expectedList, uniqueList);
    }

    /**
     * Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
     */
    @Test
    public void test02() {
        final var numbers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
        final var expectedThirdLargest = 10;
        final int thirdLargest = numbers.stream()
                .sorted((a, b) -> Integer.compare(b, a))
                .skip(2)
                .findFirst()
                .orElseThrow();

        assertEquals(expectedThirdLargest, thirdLargest);
    }

    /**
     * Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9,
     * в отличие от прошлой задачи здесь разные 10 считает за одно число)
     */
    @Test
    public void test03() {
        final var numbers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);
        final var expectedThirdUniqueLargest = 9;
        final int thirdUniqueLargest = numbers.stream()
                .distinct()
                .sorted((a, b) -> Integer.compare(b, a))
                .skip(2)
                .findFirst()
                .orElseThrow();

        assertEquals(expectedThirdUniqueLargest, thirdUniqueLargest);
    }

    /**
     * Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3
     * самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
     */
    @Test
    public void test04() {
        final var employees = Arrays.asList(
                new Employee("Alice", 35, "Engineer"),
                new Employee("Bob", 40, "Engineer"),
                new Employee("Charlie", 45, "Engineer"),
                new Employee("David", 30, "Manager"),
                new Employee("Emma", 38, "Engineer"),
                new Employee("Frank", 42, "Engineer"),
                new Employee("Grace", 37, "Engineer"));

        final var expectedNames = Arrays.asList("Charlie", "Frank", "Bob");
        final var topThreeEngineerNames = employees.stream()
                .filter(e -> "Engineer".equals(e.getPosition()))
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .map(Employee::getName)
                .distinct()
                .limit(3)
                .collect(Collectors.toList());

        assertEquals(expectedNames, topThreeEngineerNames);
    }

    /**
     * Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников
     * с должностью «Инженер»
     */
    @Test
    public void test05() {
        final var employees = Arrays.asList(
                new Employee("Alice", 35, "Engineer"),
                new Employee("Bob", 40, "Engineer"),
                new Employee("Charlie", 45, "Engineer"),
                new Employee("Frank", 74, "Engineer"),
                new Employee("Grace", 63, "Engineer"));
        final var averageAgeOfEngineers = employees.stream()
                .filter(employee -> "Engineer".equals(employee.getPosition()))
                .mapToDouble(Employee::getAge)
                .average()
                .orElse(0);

        final var expectedAverageAge = (35D + 40D + 45D + 74D + 63) / 5.0;
        assertEquals(expectedAverageAge, averageAgeOfEngineers, 0.000001);
    }

    /**
     * Найдите в списке слов самое длинное
     */
    @Test
    public void test06() {
        final var words = Arrays.asList("apple", "banana", "grapefruit", "orange", "kiwi");
        final var expectedLongestWord = "grapefruit";
        final var findLongestWord = words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");

        assertEquals(expectedLongestWord, findLongestWord);
    }

    /**
     * Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут
     * хранится пары: слово - сколько раз оно встречается во входной строке
     */
    @Test
    public void test07() {
        final var input = "apple banana apple orange banana apple";
        final var countWordFrequency = Arrays.stream(input.split(" "))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final var expectedFrequencyMap = Map.of(
                "apple", 3L,
                "banana", 2L,
                "orange", 1L);

        assertEquals(expectedFrequencyMap, countWordFrequency);
    }

    /**
     * Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длины,
     * то должен быть сохранен алфавитный порядок
     */
    @Test
    public void test08() {
        final var strings = Arrays.asList("banana", "apple", "kiwi", "orange", "grape", "pear");
        strings.stream()
                .sorted(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()))
                .forEach(System.out::println);
    }

    /**
     * Имеется массив строк, в каждой из которых лежит набор из 5 строк, разделенных пробелом, найдите среди всех слов
     * самое длинное, если таких слов несколько, получите любое из них
     */
    @Test
    public void test09() {
        String[][] arrayOfStrings = {
                {"apple banana", "kiwi grape", "orange pear", "pineapple watermelon", "strawberry blueberry"},
                {"cat", "dog", "elephant", "lion", "tiger"},
                {"green", "red", "yellow", "blue", "purple"}
        };
        final String findLongestWord = Arrays.stream(arrayOfStrings)
                .flatMap(Arrays::stream)
                .flatMap(str -> Arrays.stream(str.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        final var expectedLongestWord = "watermelon";

        assertEquals(expectedLongestWord, findLongestWord);
    }
}
