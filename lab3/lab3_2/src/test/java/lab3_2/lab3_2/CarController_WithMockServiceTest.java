package lab3_2.lab3_2;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.mockito.Mockito;
import java.util.Arrays;

@WebMvcTest(CarController.class)
public class CarController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManegerService service;

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car car = new Car("Honda", "Civic");

        when( service.save(Mockito.any()) ).thenReturn( car);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Honda")))
                .andExpect(jsonPath("$.model", is("Civic")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car("Honda", "Civic");
        Car car2 = new Car("Toyota", "Corolla");

        when(service.getAllCars()).thenReturn(Arrays.asList(car1, car2));

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker", is("Honda")))
                .andExpect(jsonPath("$[1].maker", is("Toyota")));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void whenGetCarID_thenReturnCar() throws Exception {

        Car ferrari = new Car("Ferrari", "F40");

        //when(CarManagerService.getCarDetails(anyLong())).thenReturn(java.util.Optional.of(ferrari));
        when(service.getCarDetails(anyLong())).thenReturn(java.util.Optional.of(ferrari));
        mvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(ferrari.getMaker())))
                .andExpect(jsonPath("$.model", is(ferrari.getModel())));
    }
}
