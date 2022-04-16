package org.edin.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.edin.model.Contract;
import javax.xml.stream.*;
import java.io.*;


public class XMLParser {

    private final File filename = new File("contractList.edin");


    public ObservableList<Contract> loadContract(){
        ObservableList<Contract> list = FXCollections.observableArrayList();
        try {
            if (filename.exists()) {
                XMLInputFactory inputFactory = XMLInputFactory.newFactory();
                XMLStreamReader streamReader = inputFactory.createXMLStreamReader(new FileReader(filename));
                boolean openFirstName = false;
                String _firstName = null;
                boolean openLastName = false;
                String _lastName = null;
                boolean openAddress = false;
                String _address = null;
                boolean openSpeed = false;
                String _speed = null;
                boolean openBandwidth = false;
                String _bandwidth = null;
                boolean openDuration = false;
                String _duration = null;

                while (streamReader.hasNext()) {
                    Contract c;
                    int element = streamReader.next();
                    switch (element) {
                        case XMLStreamReader.START_ELEMENT:
                            String elementName = streamReader.getName().toString();
                            if ("firstName".equalsIgnoreCase(elementName)) {
                                openFirstName = true;
                            } else if ("lastName".equalsIgnoreCase(elementName)) {
                                openLastName = true;
                            } else if ("address".equalsIgnoreCase(elementName)) {
                                openAddress = true;
                            } else if ("speed".equalsIgnoreCase(elementName)) {
                                openSpeed = true;
                            } else if ("bandwidth".equalsIgnoreCase(elementName)) {
                                openBandwidth = true;
                            } else if ("durationInYears".equalsIgnoreCase(elementName)) {
                                openDuration = true;
                            }
                            break;
                        case XMLStreamConstants.CHARACTERS:
                            if (openFirstName) {
                                _firstName = streamReader.getText();
                            } else if (openLastName) {
                                _lastName = streamReader.getText();
                            } else if (openAddress) {
                                _address = streamReader.getText();
                            } else if (openSpeed) {
                                _speed = streamReader.getText();
                            } else if (openBandwidth) {
                                _bandwidth = streamReader.getText();
                            } else if (openDuration) {
                                _duration = streamReader.getText();
                            }
                            break;
                        case XMLStreamConstants.END_ELEMENT:
                            String elementEndName = streamReader.getName().toString();

                            if ("firstName".equalsIgnoreCase(elementEndName)) {
                                openFirstName = false;

                            } else if ("lastName".equalsIgnoreCase(elementEndName)) {
                                openLastName = false;

                            } else if ("address".equalsIgnoreCase(elementEndName)) {
                                openAddress = false;

                            } else if ("speed".equalsIgnoreCase(elementEndName)) {
                                openSpeed = false;

                            } else if ("bandwidth".equalsIgnoreCase(elementEndName)) {
                                openBandwidth = false;

                            } else if ("durationInYears".equalsIgnoreCase(elementEndName)) {
                                openDuration = false;
                                c = new Contract();
                                c.setFirstName(_firstName);
                                c.setLastName(_lastName);
                                c.setAddress(_address);
                                c.setSpeed(Integer.parseInt(_speed));
                                c.setBandwidth(_bandwidth);
                                c.setDuration(_duration);
                                list.add(c);
                            }
                            break;
                    }
                }}
            } catch(Exception e){
                throw new RuntimeException(e.getMessage());
            }
        return list;
    }


    public void saveContracts(ObservableList<Contract> list){
        try {
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new FileWriter(filename));
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement("contract");
            for (Contract c: list) {
                xmlStreamWriter.writeStartElement("firstName");
                xmlStreamWriter.writeCharacters(c.getFirstName());
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("lastName");
                xmlStreamWriter.writeCharacters(c.getLastName());
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("address");
                xmlStreamWriter.writeCharacters(c.getAddress());
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("speed");
                xmlStreamWriter.writeCharacters(c.getSpeed()+"");
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("bandwidth");
                xmlStreamWriter.writeCharacters(c.getBandwidth());
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("durationInYears");
                xmlStreamWriter.writeCharacters(c.getDuration()+"");
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.flush();

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

}
