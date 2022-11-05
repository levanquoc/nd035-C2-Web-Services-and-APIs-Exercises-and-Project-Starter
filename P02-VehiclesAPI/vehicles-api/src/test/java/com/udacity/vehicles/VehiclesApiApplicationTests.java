package com.udacity.vehicles;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehiclesApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createCar() throws Exception {
        Car car = new Car();
        car.setCondition(Condition.USED);

        Details detail = new Details();
        detail.setBody("sedan");
        detail.setModel("Impala");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCode(302);
        manufacturer.setName("Chevrolet");

        detail.setManufacturer(manufacturer);
        detail.setNumberOfDoors(10);
        detail.setFuelType("Gasoline");
        detail.setEngine("3.6L V6");
        detail.setMileage(47328);
        detail.setModelYear(2022);
        detail.setProductionYear(2022);
        detail.setExternalColor("Blue");
        car.setDetails(detail);

        Location location = new Location();
        location.setLat(35.34);
        location.setLon(-34.24);
        car.setLocation(location);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(car);

        mockMvc.perform(post("/cars")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/cars/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(put("/cars/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/cars/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}