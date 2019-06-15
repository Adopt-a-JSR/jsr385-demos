package nl.utrechtjug.adoptjsr385.vaadin;

import lombok.Data;

@Data
public class ConversionRequest {
    String fromUnit;
    String fromValue;
    String toUnit;
    String toValue;
}
