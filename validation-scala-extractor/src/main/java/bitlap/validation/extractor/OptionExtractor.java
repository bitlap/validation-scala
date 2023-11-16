package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import scala.Option;

public class OptionExtractor implements ValueExtractor<Option<@ExtractedValue ?>> {

    @Override
    public void extractValues(Option<@ExtractedValue ?> originalValue, ValueReceiver receiver) {
        receiver.value(null, originalValue.isDefined() ? originalValue.get() : null);
    }
}
