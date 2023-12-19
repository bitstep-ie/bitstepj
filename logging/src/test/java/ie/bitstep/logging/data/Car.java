package ie.bitstep.logging.data;

import lombok.Builder;
import lombok.Getter;
import ie.bitstep.logging.annotations.Log;
import ie.bitstep.logging.annotations.Mask;
import ie.bitstep.logging.annotations.MaskCardNumber;

@Builder
@Getter
public class Car {
    @Log
    private final String make;

    @Log
    @Mask
    private final String model;

    @Log
    @MaskCardNumber
    private final String card;

    @Log
    private final Options options;
}
