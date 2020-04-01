package cz.jalasoft.springlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author lastovicka
 */
@SpringBootApplication
public class Main implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("@myBean.code('hotovo'.length())");

        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setBeanResolver(new BeanFactoryResolver(context));

        System.out.println(expression.getValue(ctx, String.class));
        System.out.println(expression.getValue(ctx, String.class));
        System.out.println(expression.getValue(ctx, String.class));
        System.out.println(expression.getValue(ctx, String.class));

    }
}
