package classes;

import annotations.Column;
import annotations.Table;

import java.io.File;
import java.lang.reflect.Field;
import java.util.UUID;

public class QueryBuilder {

    public String buildInsertQuery(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        StringBuilder query = new StringBuilder("INSERT INTO ");

        if (clazz.isAnnotationPresent(Table.class)){
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query
                    .append(tableAnnotation.name())
                    .append(" ( ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                if (field.isAnnotationPresent(Column.class)){
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    query
                            .append(columnAnnotation.name())
                            .append(", ");
                }
            }

            deleteComma(query);

            query.append(") VALUES ( ");
            for (Field field : fields){
                if (field.isAnnotationPresent(Column.class)){
                    field.setAccessible(true);
                    query
                            .append("'")
                            .append(field.get(object))
                            .append("', ");
                }
            }

            deleteComma(query);

            query.append(" );");
            return query.toString();
        }
        else return null;
    }

    public String buildSelectQuery(Class<?> clazz, UUID primaryKey){
        StringBuilder query = new StringBuilder("SELECT * FROM ");

        if (clazz.isAnnotationPresent(Table.class)){
            Table tableAnnotation = clazz.getAnnotation((Table.class));
            query
                    .append(tableAnnotation.name())
                    .append(" WHERE ");
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(Column.class)){
                Column columnSAnnotation = field.getAnnotation(Column.class);
                if (columnSAnnotation.primaryKey()){
                    query
                            .append(field.getName())
                            .append(" = ")
                            .append(primaryKey)
                            .append(";");
                    return query.toString();
                }
            }
        }
        return null;
    }

    public String buildUpdateQuery(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        StringBuilder query = new StringBuilder("UPDATE ");

        if (clazz.isAnnotationPresent(Table.class)){
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query
                    .append(tableAnnotation.name())
                    .append(" SET ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                if (field.isAnnotationPresent(Column.class)){
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (!columnAnnotation.primaryKey()) {
                        field.setAccessible(true);
                        query
                                .append(columnAnnotation.name())
                                .append(" = '")
                                .append(field.get(object))
                                .append("', ");

                    }
                }
            }

            deleteComma(query);

            query.append(" WHERE ");
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.primaryKey()){
                    field.setAccessible(true);
                        query
                                .append(field.getName())
                                .append(" = ")
                                .append(field.get(object))
                                .append(";");
                        return query.toString();
                    }
                }
            }

            query.append(" );");
            return query.toString();
        }
        else return null;
    }

    public String buildDeleteQuery(Class<?> clazz, UUID primaryKey){
        StringBuilder query = new StringBuilder("DELETE FROM ");
        if (clazz.isAnnotationPresent(Table.class)){
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            query
                    .append(tableAnnotation.name())
                    .append(" WHERE ");
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(Column.class)){
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()){
                    query
                            .append(field.getName())
                            .append(" = ")
                            .append(primaryKey)
                            .append(";");
                    return query.toString();
                }
            }
        }
        return null;
    }

    private StringBuilder deleteComma(StringBuilder sb){
        if (sb.charAt(sb.length()-2) == ','){
            sb.delete(sb.length()-2, sb.length()-1);
        }
        return sb;
    }
}
