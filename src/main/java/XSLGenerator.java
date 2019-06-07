import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;

public class XSLGenerator {
    private String continent, language;
    private int minSize, maxSize;

    public XSLGenerator(){
        this.continent = null;
        this.language = null;
        this.minSize = Integer.MIN_VALUE;
        this.maxSize = Integer.MAX_VALUE;
    }

    public void generate(){
        try{
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();

            Element racine = document.createElement("xsl:stylesheet");
            racine.setAttribute("version", "1.0");
            racine.setAttribute("xmlns:xsl", "http://www.w3.org/1999/XSL/Transform");

            Element template = document.createElement("xsl:template");
            template.setAttribute("match", "/");

            Element html = document.createElement("html");
            Element head = document.createElement("head");

            Element jquery = document.createElement("script");
            jquery.setAttribute("src", "js/jquery-3.4.1.min.js");
            head.appendChild(jquery);

            Element bootstrapJs = document.createElement("script");
            bootstrapJs.setAttribute("src", "js/bootstrap.min.js");
            head.appendChild(bootstrapJs);

            Element bootstrapCSS = document.createElement("link");
            bootstrapCSS.setAttribute("rel", "stylesheet");
            bootstrapCSS.setAttribute("href", "css/bootstrap.min.css");
            head.appendChild(bootstrapCSS);

            html.appendChild(head);

            Element body = document.createElement("body");
            Element countries = document.createElement("xsl:for-each");
            countries.setAttribute("select", "//countries/element");

            Element countryName = document.createElement("p");
            Element countryNameSelect = document.createElement("xsl:value-of");
            countryNameSelect.setAttribute("select", "name");
            Element countryFlag = document.createElement("i");
            countryFlag.setAttribute("class", "fas fa-flag");
            countryName.appendChild(countryNameSelect);
            countryName.appendChild(countryFlag);
            countries.appendChild(countryName);
            body.appendChild(countries);
            html.appendChild(body);
            template.appendChild(html);
            racine.appendChild(template);

            document.appendChild(racine);

            // Writing the XSL file
            DOMSource source = new DOMSource(document);

            FileWriter writer = new FileWriter(new File("src/main/resources/data/countries.xsl"));
            StreamResult result = new StreamResult(writer);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
