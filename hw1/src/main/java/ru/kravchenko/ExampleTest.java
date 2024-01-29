package ru.kravchenko;


import ru.kravchenko.annotation.*;

public class ExampleTest {
    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("BeforeSuite method");
    }

    @Test(priority = 7)
    public void testMethod1() {
        System.out.println("Test method with priority 7");
    }

    @Test(priority = 3)
    public void testMethod2() {
        System.out.println("Test method with priority 3");
    }

    @Test
    public void testMethod3() {
        System.out.println("Test method with default priority 5");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("AfterSuite method");
    }
}
