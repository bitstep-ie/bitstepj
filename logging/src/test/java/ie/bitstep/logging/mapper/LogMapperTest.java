package ie.bitstep.logging.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ie.bitstep.logging.data.Car;
import ie.bitstep.logging.data.Options;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LogMapperTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCar() throws JsonProcessingException {
        Car c = Car.builder()
                .make("Mercedes")
                .model("E220d")
                .card("5111000011119876")
                .options(
                        Options.builder()
                                .alloyWheels(true)
                                .spotlights(false)
                                .leatherSeats(true)
                                .dualZoneClimateControl(true)
                                .heatedSeats(true)
                                .build())
                .build();

        c.getOptions().setOptions(c.getOptions());
        LogMapper logMapper = new LogMapper();
        Map<String, Object> m = logMapper.map(c);

        assertThat(m).containsEntry("card", "511100******9876");
        System.out.println(objectMapper.writeValueAsString(m));
    }
}