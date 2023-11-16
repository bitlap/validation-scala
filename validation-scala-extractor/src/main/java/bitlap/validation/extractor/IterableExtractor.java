package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import scala.collection.Iterable;
import scala.collection.Iterator;

public class IterableExtractor implements ValueExtractor<Iterable<@ExtractedValue ?>> {

    @Override
    public void extractValues(Iterable<@ExtractedValue ?> originalValue, ValueReceiver receiver) {
        Iterator<?> iterator = originalValue.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            receiver.iterableValue("<iterable element>", o);
        }
    }
}
