package com.example.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class ServiceApplicationTests {

	@Test
	void contextLoads() {

		var modules = ApplicationModules.of( ServiceApplication.class) ;
		for (var m : modules)
			System.out.println(m.getName() + ":"+
					 m.getBasePackage());

		modules.verify();

	}

}
