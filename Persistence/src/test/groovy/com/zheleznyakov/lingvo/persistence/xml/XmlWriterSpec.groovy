package com.zheleznyakov.lingvo.persistence.xml

import com.zheleznyakov.lingvo.basic.persistence.Persistable
import spock.lang.Specification
import spock.lang.Unroll

class XmlWriterSpec extends Specification {
    private StringWriter output = []
    private XmlWriter writer = [output]

    @Unroll
    def "Test persisting entity with primitive fields only: #entity.class.simpleName"() {
        when: "the entity is persisted"
        writer.write(entity)

        then: "only annotated field is persisted"
        output.toString() == expectedOutput

        where: "parameters are"
        entity              || expectedOutput
        new IntegerEntity() || "<IntegerEntity>\n  <intValue>42</intValue>\n</IntegerEntity>"
        new DoubleEntity()  || "<DoubleEntity>\n  <doubleValue>42.0</doubleValue>\n</DoubleEntity>"
    }

    private static class IntegerEntity {
        @Persistable private int intValue = 42
        private int intValue2 = (Math.random() * 100) as int
    }

    private static class DoubleEntity {
        @Persistable private double doubleValue = 42.0
        private double doubleValue2 = Math.random() * 100
    }

}
