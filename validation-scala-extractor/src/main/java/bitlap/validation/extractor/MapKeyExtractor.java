package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import scala.collection.Iterator;
import scala.collection.Map;

/**
 * Cannot verify both key and value due to hibernate-validator limitations.
 * If you want to verify the key, you should get ConstraintValidatorFactory and call it's releaseInstance method to 
 * release the [[bitlap.validation.extractor.MapValueExtractor]]
 */
public class MapKeyExtractor implements ValueExtractor<Map<@ExtractedValue ?, ?>> {

    @Override
    public void extractValues(Map<?, ?> originalValue, ValueReceiver receiver) {
        Iterator<?> iterator = originalValue.keysIterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            receiver.keyedValue("<map key>", key, key);
        }
    }
}
