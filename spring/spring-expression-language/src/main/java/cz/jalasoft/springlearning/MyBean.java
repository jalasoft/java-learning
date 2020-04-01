package cz.jalasoft.springlearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("myBean")
public class MyBean {

	private final String name;
	private final AtomicInteger counter;

	@Autowired
	public MyBean(
			@Value("#{'Honzales'}")
			String name) {
		this.name = name;
		this.counter = new AtomicInteger(0);
	}

	public String code(String suffix) {
		return name + counter.getAndIncrement() + suffix;
	}
}
