package br.com.hospital.utils;

import br.com.hospital.exceptions.AttributeInValidException;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class Validation {
    @SneakyThrows
    public static <T> void validateRequiredAttributes(T object) {
        Class<?> classe = object.getClass();
        for (Field attribute : classe.getDeclaredFields()) {
            attribute.setAccessible(true);
            if (attribute.get(object) == null || attribute.get(object) == "") {
                throw new AttributeInValidException(String.format("O %s não pode estar nulo ou vazio", attribute.getName()));
            }
        }
    }
}
