package org.winbandu.populator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * org.winbandu.populator.Populator is responsible for poplulating all the bean values in your bean.
 */
public class Populator {
    /**
     * core method that rigs up a bean for you the bean doesn't need to have setters or getters.
     * all declared fields would be targetted.
     * @param target the bean that has to be populated
     * @param <T>
     * @return
     */
    public static<T> T rigUp(T target) {
        for(Field local : target.getClass().getDeclaredFields()) {
            process(target, local);
        }
        Class temp = target.getClass();
        while(!temp.getName().equals("java.lang.Object")) {
            for (Field local : temp.getSuperclass().getDeclaredFields()) {
                process(target, local);
            }
            temp = temp.getSuperclass();
        }
        return target;
    }

    private static <T> void process(T target, Field local) {
        if (!Modifier.isStatic(local.getModifiers())) {
            try {
                if (local.getType().getName().equals("long")) {
                    local.setAccessible(true);
                    local.setLong(target, getRandomNumber());
                    local.setAccessible(false);
                } else if (local.getType().getName().equals("int")) {
                    local.setAccessible(true);
                    local.setInt(target, getRandomNumber());
                    local.setAccessible(false);
                }  else if (local.getType().getName().equals("java.lang.String")) {
                    local.setAccessible(true);
                    local.set(target, getRandomString());
                    local.setAccessible(false);
                } else if (local.getType().getName().equals("java.math.BigInteger")) {
                    local.setAccessible(true);
                    local.set(target, new BigInteger(getRandomNumber() + ""));
                    local.setAccessible(false);
                } else if (local.getType().getName().equals("java.util.List")) {
                    local.setAccessible(true);
                    local.set(target, new ArrayList<>());
                    local.setAccessible(false);
                } else if (local.getType().getName().equals("java.lang.Integer")) {
                    local.setAccessible(true);
                    local.set(target, new Integer(getRandomNumber() + ""));
                    local.setAccessible(false);
                } else if (local.getType().getName().equals("java.lang.Boolean")) {
                    local.setAccessible(true);
                    local.set(target, true);
                    local.setAccessible(false);
                } else if (!local.getType().isEnum()) {
                    local.setAccessible(true);
                    local.set(target, local.getType().newInstance());
                    rigUp(target.getClass().getMethod("get" +
                            Character.toUpperCase(local.getName().charAt(0)) +
                            local.getName().substring(1)
                    ).invoke(target));
                    local.setAccessible(false);
                } else if(local.getType().isEnum()) {
                    local.setAccessible(true);
                    local.set(target, local.getType().getEnumConstants()[0]);
                    local.setAccessible(false);
                }
            } catch (Exception e) {}
        }
    }

    private static short getRandomNumber() {
        return (short) Math.round(Math.ceil((9999) * Math.random()));
    }

    private static String getRandomString() {
        return "String-" + getRandomNumber();


    }
}
