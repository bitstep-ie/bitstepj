package ie.bitstep.logging.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ie.bitstep.logging.annotations.Log;

@Builder
@Getter
public class Options {
    @Log
    private final Boolean alloyWheels;

    @Log
    private final Boolean leatherSeats;

    @Log
    private final Boolean heatedSeats;

    @Log
    private final Boolean spotlights;

    @Log
    private final Boolean dualZoneClimateControl;

    @Log
    @Setter
    private Options options;
}
