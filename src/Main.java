import classes.Employee;
import classes.QueryBuilder;

import java.lang.reflect.Method;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {

        // region standart task

        // Используя Reflection API, напишите программу, которая
        // выводит на экран все методы класса String

        Class<?> sm = String.class;
        Method[] stringMethods = sm.getMethods();
        for (Method method : stringMethods) {
            System.out.println(method.getName() +
                    " " +
                    method.getReturnType().getName());
        }

        // endregion

        // region seminar task
        /*
        Задача 3: Реализовать простой фреймворк для создания SQL-запросов
        на основе Java объектов

        Фреймворк должен позволять аннотировать классы и поля для связывания их
        с таблицами и столбцами в базе данных.

        1. Аннотации для маппинга:
            Создайте аннотации, такие как @Entity, @Table, @Column для маппинга
            классов, таблиц и полей в базе данных.
        2. Механизм генерации SQL-запросов:
            Реализуйте класс QueryBuilder, который может принимать объект и
            генерировать SQL-запросы для выполнения операций CRUD (Create, Read,
            Update, Delete) на основе аннотаций.
            Используйте Reflection для получения метаданных класса, аннотированного
            объекта, чтобы построить соответствующий SQL-запрос.
        3. Пример использования:
            Создайте простой класс, аннотированный для маппинга с базой данных.
            Используйте ваш фреймворк для генерации SQL-запросов для различных
            операций, таких как вставка, выборка, обновление и удаление.
        */

        Employee user = new Employee("Gena", "gena@gena.com");
        UUID pk = UUID.randomUUID();
        user.setId(pk);

        QueryBuilder queryBuilder = new QueryBuilder();

        String insertQuery = queryBuilder.buildInsertQuery(user);
        System.out.printf("Insert Query: %s\n", insertQuery);

        String selectQuery = queryBuilder.buildSelectQuery(user.getClass(), pk);
        System.out.printf("Select Query: %s\n", selectQuery);

        String updateQuery = queryBuilder.buildUpdateQuery(user);
        System.out.printf("Update Query: %s\n", updateQuery);

        String deleteQuery = queryBuilder.buildDeleteQuery(user.getClass(), pk);
        System.out.printf("Delete Query: %s\n", deleteQuery);

        // endregion
    }
}