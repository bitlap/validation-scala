package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import scala.collection.Iterator;
import scala.collection.Set;

public class SetExtractor implements ValueExtractor<Set<@ExtractedValue ?>> {

    @Override
    public void extractValues(Set<?> originalValue, ValueReceiver receiver) {
        Iterator<?> iterator = originalValue.iterator();
        while (iterator.hasNext()) {
            Object a = iterator.next();
            receiver.iterableValue("<set element>", a);
        }
    }
}
