package org.testovoe;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

        String code = "";
        String date = "";

        for (int i = 0; i < args.length; i++) {

            if (args[i].startsWith("--code=")) {

                code = args[i].replaceAll("--code=", "");

            } else if (args[i].startsWith("--date=")) {

                date = args[i].replaceAll("--date=", "");
            }

        }
        String res = parseValue(date, code);

        if (!res.isEmpty()) {
            System.out.println(res);
        } else {
            System.out.println("Вы ввели некорректные данные");
        }
    }

    public static String parseValue(String date, String code) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        MyParse myParse = new MyParse();

        URL url = new URL("https://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date);
        parser.parse(url.openStream(), myParse);
        Value value = myParse.getValueCon();
        var ref = new Object() {
            String result = "";
        };

        value.valueArtList.forEach(valueArt -> {

            if (code.equals(valueArt.getValuteName())) {
                float getValue = Float.parseFloat(valueArt.getValue().replaceAll(",", "."));
                String valuteName = valueArt.getValuteName();
                String valuteFullName = valueArt.getValuteFullName();
                int nominal = valueArt.getNominal();

                String resultValue = String.valueOf(getValue / nominal);

                ref.result = valuteName + "(" + valuteFullName + "): " + resultValue;

            }
        });

        return ref.result;
    }
}