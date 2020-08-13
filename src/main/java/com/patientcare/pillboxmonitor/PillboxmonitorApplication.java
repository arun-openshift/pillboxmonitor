package com.patientcare.pillboxmonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patientcare.pillboxmonitor.service.MonitorService;

@SpringBootApplication
@RestController
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
	
	@RequestMapping(value = "/areyouthere")
	public ResponseEntity<String> getGreeting() {
		return new ResponseEntity<String>("iMHERE", HttpStatus.OK);
	}

}
