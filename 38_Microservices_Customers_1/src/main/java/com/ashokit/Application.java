package com.ashokit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
//@EnableFeignClients
//@EnableDiscoveryClient
public class Application implements CommandLineRunner{
	
	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		String welcomeMessage = environment.getProperty("welcome.message");
		System.out.println(welcomeMessage);
	}

}

/*
 {
    "id": 2,
    "name": "Ashok",
    "location": "Hyderabad",
    "gender": "male",
    "emailId": "ashokit@gmail.com",
    "contactNo": "11223344",
    "createdDate": "2025-01-24T07:19:40.244514",
    "addressResponse": {
        "id": 54,
        "doorNo": "123-345-456",
        "cityName": "Delhi",
        "pincode": "55555",
        "created_dt": "2025-01-25T06:42:51.809715",
        "customerId": 2
    }
}
*/

/*
{
    "customerDetails": {
        "id": 2,
        "name": "Ashok",
        "location": "Hyderabad",
        "gender": "male",
        "emailId": "ashokit@gmail.com",
        "contactNo": "11223344",
        "createdDate": "2025-01-24T07:19:40.244514"
    },
    "addressDetails": {
        "id": 54,
        "doorNo": "123-345-456",
        "cityName": "Delhi",
        "pincode": "55555",
        "created_dt": "2025-01-25T06:42:51.809715",
        "customerId": 2
    }
}
*/