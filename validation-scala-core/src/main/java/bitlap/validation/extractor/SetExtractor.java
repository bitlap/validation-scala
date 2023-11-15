package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import scala.collection.Iterator;
import scala.collection.Set;

public class SetExtractor implements ValueExtractor<Set<@ExtractedValue ?>> {

    @Override
    public void extractValues(Set<?> originalValue, ValueReceiver receiver) {
        int i = 0;
        Iterator<?> iterator = originalValue.iterator();
        while (iterator.hasNext()) {
            Object a = iterator.next();
            receiver.indexedValue(NodeImpl.LIST_ELEMENT_NODE_NAME, i, a);
            i++;
        }
    }
}
