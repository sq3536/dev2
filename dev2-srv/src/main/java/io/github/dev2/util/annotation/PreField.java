package io.github.dev2.util.annotation;


import io.github.dev2.util.enums.FillMode;
import io.github.dev2.util.enums.PredefinedType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD})
public @interface PreField
{
	FillMode fill() default FillMode.INSERT_UPDATE;//填充模式 insert or update
	PredefinedType preType() default PredefinedType.DEFAULT;//预置属性类型
}

