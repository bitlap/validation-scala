package bitlap.validation.extractor;

import jakarta.validation.valueextraction.ExtractedValue;
import jakarta.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import scala.collection.Seq;

public class SeqExtractor implements ValueExtractor<Seq<@ExtractedValue ?>> {

    @Override
    public void extractValues(Seq<?> originalValue, ValueReceiver receiver) {
        for (int i = 0; i < originalValue.size(); i++) {
            receiver.indexedValue(NodeImpl.LIST_ELEMENT_NODE_NAME, i, originalValue.apply(i));
        }
    }
}
