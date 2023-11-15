package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.Map;

public class MapKeyExtractor implements ValueExtractor<Map<@ExtractedValue ?, ?>> {


    @Override
    public void extractValues(Map<?, ?> originalValue, ValueReceiver receiver) {
        Iterator<? extends Tuple2<?, ?>> iterator = originalValue.iterator();
        while (iterator.hasNext()) {
            Tuple2<?, ?> tuple2 = iterator.next();
            receiver.keyedValue(NodeImpl.MAP_KEY_NODE_NAME, tuple2._1(), tuple2._2());
        }
    }
}
