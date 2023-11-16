package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.Map;

/**
 * Cannot verify both key and value due to hibernate-validator limitations. Register key extractor by default.
 */
public class MapValueExtractor implements ValueExtractor<Map<?, @ExtractedValue ?>> {

    @Override
    public void extractValues(Map<?, @ExtractedValue ?> originalValue, ValueReceiver receiver) {
        Iterator<? extends Tuple2<?, ?>> iterator = originalValue.iterator();
        while (iterator.hasNext()) {
            Tuple2<?, ?> tuple2 = iterator.next();
            receiver.keyedValue("<map value>", tuple2._1(), tuple2._2());
        }
    }
}
