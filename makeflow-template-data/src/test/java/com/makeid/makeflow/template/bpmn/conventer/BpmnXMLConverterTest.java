package com.makeid.makeflow.template.bpmn.conventer;

import com.makeid.makeflow.template.bpmn.model.BpmnModel;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class BpmnXMLConverterTest {

    @org.junit.jupiter.api.Test
    void convertToBpmnModel() throws IOException, XMLStreamException {
        String modelPath = "test.xml";
        try (InputStream xmlStream = this.getClass().getClassLoader().getResourceAsStream(
                modelPath)) {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            InputStreamReader in = new InputStreamReader(xmlStream, StandardCharsets.UTF_8);
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
            System.out.println();
        }

    }
}