package org.example.annotations;


import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JabaColumn {

    String columnName() default "";

}
