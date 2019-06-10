package nl.utrechtjug.adoptjsr385.springboot;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.UnitConverter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.units.indriya.unit.Units;

@RestController
public class ConversionController {

	/** 
	 * Demo conversion, kilometers into millimeters
	 * @param kilometers
	 * @return
	 */
	@GetMapping("/demo/{kilometers}")
	public ConversionOutput doDemo(@PathVariable("kilometers") String kilometers)	{
		return doTransformFromPrefix(ConversionInput.createForDemo(new Long(kilometers)));
	}

	
	/**
	 * provide prefixes and units for the conversion. 
	 * @param conversionInput
	 * @return conversionOutput
	 * @throws Exception
	 */
	@PostMapping("/conversion")
	public ConversionOutput doTransformFromPrefix(@RequestBody ConversionInput conversionInput)	{

		Unit<?> sourceUnit = createUnit(conversionInput.getUnit(), conversionInput.getPrefix());
		Unit<?> targetUnit = createUnit(conversionInput.getTargetUnit(), conversionInput.getTargetPrefix());

		ConversionOutput conversionOutput = new ConversionOutput();
		conversionOutput.setInput(conversionInput);
		conversionOutput.setTargetValue(conversionOfUnits(sourceUnit, targetUnit, conversionInput.getValue()));

		return conversionOutput;
	}

	private Unit<?> createUnit(String unit, String prefix)	{

		try {
			Unit<?> targetUnit = Units.getInstance().getUnit(unit);
			if (prefix != null) {
				targetUnit = targetUnit.prefix(MetricPrefix.valueOf(prefix));
			}
			return targetUnit;
		} catch (Exception e) {
			throw new UserInputException(e.getMessage());
		}
	}

	private Number conversionOfUnits(Unit<?> sourceUnit, Unit<?> targetUnit, Number value) {
		try {
			UnitConverter converter = sourceUnit.getConverterToAny(targetUnit);
			Number result = converter.convert(value);
			return result;
		} catch (Exception e) {
			throw new UserInputException(e.getMessage());
		}
	}
}
