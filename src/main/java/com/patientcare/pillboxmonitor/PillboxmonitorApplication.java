package com.patientcare.pillboxmonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.patientcare.pillboxmonitor.service.MonitorService;

@SpringBootApplication
public class PillboxmonitorApplication implements CommandLineRunner {
	
	@Autowired
	MonitorService monitorService;

	@Autowired
	private ConfigurableApplicationContext context;

	
	public static void main(String[] args) {
		SpringApplication.run(PillboxmonitorApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		final String topic = "testTopic";

		monitorService.subscribe(topic);

		Thread.sleep(2000);
//		monitorService.publish(topic, "Welcome openshift1", 0, true);

//		context.close();
	}

}
