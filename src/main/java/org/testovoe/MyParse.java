package org.testovoe;

import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MyParse extends DefaultHandler {

    private static final String VALCURS = "ValCurs";
    private static final String VALUTE = "Valute";
    private static final String CHARCODE = "CharCode";
    private static final String VALUE = "Value";
    private static final String NOMINAL = "Nominal";
    private static final String NAMEOFVALUE = "Name";

    @Getter
    private Value valueCon;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        valueCon = new Value();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) {

        switch (qName) {
            case VALCURS -> valueCon.valueArtList = new ArrayList<>();
            case VALUTE -> valueCon.valueArtList.add(new ValueArt());
            case CHARCODE, NAMEOFVALUE, NOMINAL, VALUE -> elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        switch (qName) {
            case CHARCODE -> latestArticle().setValuteName(elementValue.toString());
            case VALUE -> latestArticle().setValue(elementValue.toString());
            case NOMINAL -> latestArticle().setNominal(Integer.parseInt(elementValue.toString()));
            case NAMEOFVALUE -> latestArticle().setValuteFullName(elementValue.toString());
        }
    }

    private ValueArt latestArticle() {

        List<ValueArt> articleList = valueCon.valueArtList;
        int latestArticleIndex = articleList.size() - 1;

        return articleList.get(latestArticleIndex);
    }

}
