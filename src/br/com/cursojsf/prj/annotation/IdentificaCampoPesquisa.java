package br.com.cursojsf.prj.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.FIELD)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public abstract @interface IdentificaCampoPesquisa {
	String descricaoCampo();       // descrição do campo na tela
	String campoConsulta();        // campo no banco de dados
	int principal() default 1000;  // posição que ira aparecer no LOV
}
