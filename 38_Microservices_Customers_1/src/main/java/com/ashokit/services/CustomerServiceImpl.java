package com.ashokit.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ashokit.dao.CustomerDao;
import com.ashokit.entity.Customer;
import com.ashokit.feign.clients.AddressClient;
import com.ashokit.request.CustomerRequest;
import com.ashokit.response.AddressResponse;
import com.ashokit.response.ApiResponse;
import com.ashokit.response.CustomerResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	//@LoadBalanced
	private RestTemplate restTemplate; 
	
	@Value("${address.service.name.url}")
	public String addressServiceUrl;
	
	//@Autowired
	//public WebClient webClient;
	
	//Autowiring the Feignclients
	//@Autowired
	//public AddressClient addressClient;
	
	//@Autowired
	//private DiscoveryClient discoveryClient;
	
	//@Autowired
	//private LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private Environment environment;
	
	@Override
	public CustomerResponse createCustomer(CustomerRequest customerRequest) {
		//converting from CustomerRequest To Entity Class Object
		Customer customer = convertingFromRequestToEntity(customerRequest);
		//Saving Customer Information
		Customer savedCustomer = this.customerDao.save(customer);
		//Converting From Customer Entity to CustomerResponse Object
		return this.convertingEntityFromResponse(savedCustomer);
	}

	@Override
	public CustomerResponse getCustomerById(Integer customerId) {
		Optional<Customer> customerDetails = this.customerDao.findById(customerId);
		if (customerDetails.isPresent()) {
			Customer custDetails = customerDetails.get();
			
			CustomerResponse customerResponse = this.convertingEntityFromResponse(custDetails);
			
			//calling the AddressService
			AddressResponse addressResponse = callingAddressServiceWithRestTemplate(customerId);

			//Appending AddressResponse to CustomerResponse (First technique)
			customerResponse.setAddressResponse(addressResponse);
			
			return customerResponse;
		}else {
			//throw the exception
			return null;
		}
	}
	
	
	@Override
	public ApiResponse getCustomerAndAddressById(Integer customerId) {
		Optional<Customer> customerDetails = this.customerDao.findById(customerId);
		if (customerDetails.isPresent()) {
			Customer custDetails = customerDetails.get();
			
			//holding customer information in customerResponse
			CustomerResponse customerResponse = this.convertingEntityFromResponse(custDetails);
			
			//calling the AddressService
			AddressResponse addressResponse = callingAddressServiceWithRestTemplate(customerId);
			//AddressResponse addressResponse = callingAddressServiceWithWebClient(customerId);
			//AddressResponse addressResponse = addressClient.fetchAddressByCustomerId(customerId).getBody();
			//AddressResponse addressResponse = callAddressServiceByUsingDiscoveryClient(customerId);
			//AddressResponse addressResponse = callAddressServiceByUsingLoadBalancer(customerId);

			//Appending CustomerResponse & AddressResponse to ApiResponse(2nd Technique)
			ApiResponse apiResponse = new ApiResponse();
			//setting values
			apiResponse.setCustomerDetails(customerResponse);
			apiResponse.setAddressDetails(addressResponse);
			
			return apiResponse;
		}else {
			//throw the exception
			return null;
		}
	}

	@Override
	public List<CustomerResponse> getAllCustomers() {
		//Getting All Customers Details
		List<Customer> allCustomers = this.customerDao.findAll();
		//converting from List<Customer> into List<CustomerResponse>
		List<CustomerResponse> allCustomerResponse = 
				allCustomers.stream()
				            .map(eachCustomer -> {return this.modelMapper.map(eachCustomer, CustomerResponse.class);})
							.collect(Collectors.toList());
		log.info("Inside the CustomerServices Record Count {}::::", allCustomers.size());
		//Writing logic for Address Microservices to get All Address and map to each customer
		//List<AddressResponse> allAddressResponse = callingAddressServiceForAllAddressWithRestTemplate();
		//List<AddressResponse> allAddressResponse = callingAddressServiceForAllAddressWithWebClient();
		//List<AddressResponse> allAddressResponse = addressClient.fetchAllAddresses().getBody();
		
		//Mapping the Address with Customer
		/*List<CustomerResponse> allCustomerResponse1 = allCustomerResponse.stream()
			.map(eachCustomer -> {
				 Optional<AddressResponse> add = allAddressResponse.stream()
				.filter(eachAddress ->{
						return eachAddress.getCustomerId() == eachCustomer.getId();
					  })
				.findAny();
				//setting AddressResponse to CustomerResponse
				 if(add.isPresent()) {
					 eachCustomer.setAddressResponse(add.get());
				 }else {
					 eachCustomer.setAddressResponse(null); 
				 }
				return eachCustomer;
			 })
		.collect(Collectors.toList());
		log.info("After Mapping Customer Into Address::::{}", allCustomerResponse1.size());
		returning CustomerResponse*/
		return allCustomerResponse;
	}
	
	//utility method for converting customerRequest to Entity Object
	private Customer convertingFromRequestToEntity(CustomerRequest customerRequest) {
		Customer customer = modelMapper.map(customerRequest,Customer.class);
		System.out.println("Customer::::" + customer);
		return this.modelMapper.map(customerRequest,Customer.class);
	}
	
	//utility method for converting customerRequest to Entity Object
	private CustomerResponse convertingEntityFromResponse(Customer customer) {
		CustomerResponse custResponse = this.modelMapper.map(customer,CustomerResponse.class);
		System.out.println("CustomerResponse::::" + custResponse.getCreatedDate());
		return custResponse;
	}
	
	/*private AddressResponse callAddressServiceByUsingDiscoveryClient(int customerId) {
		//Fetching all Instances related to Address-Service
		List<ServiceInstance> allInstances = discoveryClient.getInstances("ADDRESS-SERVICE");
		
		//Fetching the first instance all available instances
		ServiceInstance currentInstance = allInstances.get(0);
		
		//
		String apiUrl = currentInstance.getUri()+"/api/address/customer/{customerId}";
		System.out.println("API URL ::::" + apiUrl);
		
		ResponseEntity<AddressResponse> addressEntity = 
				   restTemplate.exchange(apiUrl, 
						         HttpMethod.GET,null,
						         AddressResponse.class,
						         customerId);   
		   //checking the API Status is 200 or not
		  if (addressEntity.getStatusCode() == HttpStatus.OK) {
			   //API having Response body or not
			 	if (addressEntity.hasBody()) {
			 		return addressEntity.getBody();
				}
		  }
		 return null;
	}*/
	
	private AddressResponse callAddressServiceByUsingLoadBalancer(int customerId) {
		
	ResponseEntity<AddressResponse> addressEntity = 
				   restTemplate.exchange(addressServiceUrl+"customer/"+customerId, 
						         HttpMethod.GET,null,
						         AddressResponse.class,
						         customerId);   
		   //checking the API Status is 200 or not
		  if (addressEntity.getStatusCode() == HttpStatus.OK) {
			   //API having Response body or not
			 	if (addressEntity.hasBody()) {
			 		return addressEntity.getBody();
				}
		  }
		 return null;
	}
	
	//calling the Address microservice to Fetch Address of particular Customer
	private AddressResponse callingAddressServiceWithRestTemplate(int customerId) {
		//http://localhost:9966/api/address/
		System.out.println("Address Service URL:::" + addressServiceUrl);
		ResponseEntity<AddressResponse> addressResponseEntity = 
				restTemplate.getForEntity(addressServiceUrl+"customer/{customerId}",
					   			          AddressResponse.class,
								          customerId
								         );
		
		//By Using exchange() of RestTemplate
		/*ResponseEntity<AddressResponse> addressResponseEntity = 
				restTemplate.exchange(addressServiceUrl+"customer/{customerId}", 
							  HttpMethod.GET,
							  null,
							  AddressResponse.class,
							  customerId);*/
		
		//checking the API Status
		if(addressResponseEntity.getStatusCode() == HttpStatus.OK) {
			//ResponseBody of API
			if(addressResponseEntity.hasBody()) {
				return addressResponseEntity.getBody();
			}
		}
		return null;
	}
	
	//calling the Address micro service to Fetch Address of particular Customer
	/*private AddressResponse callingAddressServiceWithWebClient(int customerId) {
		AddressResponse addressResponse = webClient.get()
											        .uri(addressServiceUrl+"customer/"+customerId)
											        .retrieve()
											        .bodyToMono(AddressResponse.class)
											        .block();
		return addressResponse;
	}*/		
	
	
	//calling the Address micro service to Fetch Address of particular Customer
	private List<AddressResponse> callingAddressServiceForAllAddressWithRestTemplate() {
		
		ResponseEntity<List<AddressResponse>> addressResponseEntity = 
				    restTemplate.exchange(addressServiceUrl,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<AddressResponse>>(){});
		
		//checking the API Status
		if(addressResponseEntity.getStatusCode() == HttpStatus.OK) {
			//ResponseBody of API
			if(addressResponseEntity.hasBody()) {
				return addressResponseEntity.getBody();
			}
		}
		return null;
	}
	
	//calling the Address micro service to Fetch Address of particular Customer
	/*private List<AddressResponse> callingAddressServiceForAllAddressWithWebClient() {
		List<AddressResponse> allAddress = webClient.get()
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<AddressResponse>>(){})
				.block();
				
	    return allAddress;
	}*/

}
