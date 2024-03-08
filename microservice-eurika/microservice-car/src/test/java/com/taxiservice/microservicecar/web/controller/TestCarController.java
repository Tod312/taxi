package com.taxiservice.microservicecar.web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxiservice.microservicecar.model.Car;
import com.taxiservice.microservicecar.model.Status;
import com.taxiservice.microservicecar.repository.CarRepository;
import com.taxiservice.microservicecar.service.CarService;

@WebMvcTest(controllers = CarController.class)
public class TestCarController {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CarService carService;
	
	@MockBean
	private CarRepository carRepository;
	
	private static List<Car> cars;
	
	@BeforeAll
	public static void initArray() {
		cars = new ArrayList<>();
		cars.add(new Car("A240PY77","Chery","Tiggo", Status.FREE));
		cars.add(new Car("B240PY76","Chery","Tiggo", Status.BROKEN));
		cars.add(new Car("C240PY75","Chery","Tiggo", Status.REPAIR));
		cars.add(new Car("D240PY74","Chery","Tiggo", Status.TAKEN));
		cars.add(new Car("J240PY73","Chery","Tiggo", Status.FREE));
		cars.add(new Car("E240PY72","Chery","Tiggo", Status.TAKEN));
		cars.add(new Car("R240PY71","Chery","Tiggo", Status.BROKEN));
	}
	
	@Test
	public void test_getCar_with_good_id() throws Exception{
		
		Car car = new Car();
		car.setNumber("A240PY77");
		car.setCarMake("Chery");
		car.setModel("Tiggo");

		when(carService.getById(anyLong())).thenReturn(car);
		
		mockMvc.perform(get("/car")
				.queryParam("id", "2"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.number").value("A240PY77"));
				
	}
	
	@Test
	public void test_getCar_with_symbol_id() throws Exception{
		String x = "asdea";
		mockMvc.perform(get("/car")
				.queryParam("id", x))
				.andExpect(status().isInternalServerError());
	}
	

	@Test
	public void test_getCar_with_null_id() throws Exception{
		String x = null;
		mockMvc.perform(get("/car")
				.queryParam("id", x))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_getCarByNumber_with_good_Number() throws Exception{
		
		String number = "A240PY77";
		
		Car car = new Car();
		car.setNumber("A240PY77");
		car.setCarMake("Chery");
		car.setModel("Tiggo");

		when(carService.getByNumber(any(String.class))).thenReturn(car);
		
		mockMvc.perform(get("/car/byNumber")
				.queryParam("number", number))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.number").value("A240PY77"));
				
	}
	
	@Test
	public void test_getCarByNumber_with_badValid_Number() throws Exception{ //null,empty,more or less than 8 symbols
		
		String number = "A240PY7711111";
		
		MvcResult mvcResult = mockMvc.perform(get("/car/byNumber")
				.queryParam("number", number))
				.andExpect(status().isBadRequest())
				.andReturn();
		
		String content = mvcResult.getResponse().getContentAsString();
		
		assertEquals(content, "Bad valid");
	}
	
	@Test
	public void test_getAllCar() throws Exception{
		
		when(carService.getAll()).thenReturn(cars);
		
		mockMvc.perform(get("/car/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(cars.size())));
	}
	
	@Test
	public void test_getAllCar_with_gooo_status() throws Exception{
		
		List<Car> filtredCars = cars.stream().filter(c -> c.getStatus() == Status.FREE).collect(Collectors.toList());
		
		when(carService.getAllByStatus(Status.FREE)).thenReturn(filtredCars);
		
		mockMvc.perform(get("/car/all")
				.queryParam("status", "FREE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(filtredCars.size())));
	}
	
	@Test
	public void test_getAllCar_with_bad_status() throws Exception{

		mockMvc.perform(get("/car/all")
				.queryParam("status", "HIHI"))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void test_create_with_good_car() throws Exception{
		
		Car car = cars.get(0);
		
		when(carService.create(car)).thenReturn(car);
		
		mockMvc.perform(post("/car/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(car)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void test_create_with_taken_carNumber() throws Exception{
		
		Car car = cars.get(0);
		
		when(carRepository.findByNumber(any(String.class))).thenReturn(Optional.of(new Car()));
		
		mockMvc.perform(post("/car/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(car)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_update_with_good_car() throws Exception{
		
		Car car = cars.get(1);
		
		when(carService.update(car)).thenReturn(car);
		
		MvcResult result =  mockMvc.perform(put("/car/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(car)))
				.andExpect(status().isAccepted())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertEquals(content, "Resource updated successfully");
	}
	
	@Test
	public void test_update_with_bad_car() throws Exception{
		
		Car car = new Car("A240PY7777","","", Status.FREE);
		
		when(carRepository.findByNumber(car.getNumber())).thenReturn(Optional.of(new Car()));
		
		when(carService.update(car)).thenReturn(car);
		
		mockMvc.perform(put("/car/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(car)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void test_delete_with_good_id() throws Exception{
		
		doNothing().when(carService).deleteById(anyLong());
		
		mockMvc.perform(delete("/car/delete")
				.queryParam("id", "2"))
				.andExpect(status().isAccepted());
	}
	
	@Test
	public void test_delete_with_null_id() throws Exception{
		
		doNothing().when(carService).deleteById(anyLong());
		
		mockMvc.perform(delete("/car/delete")
				.queryParam("id", ""))
				.andExpect(status().isBadRequest());
	}
}
