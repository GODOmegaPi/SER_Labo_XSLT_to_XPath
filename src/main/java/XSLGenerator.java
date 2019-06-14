import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.awt.ModalExclude;

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
        final String filter = generateFilter();
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
            countryName.setAttribute("data-toggle", "modal");
            Element countryModal = document.createElement("xsl:attribute");
            countryModal.setAttribute("name", "data-target");
            Element countryModalPath = document.createElement("xsl:value-of");
            countryModalPath.setAttribute("select", "concat('#', alpha3Code)");
            countryModal.appendChild(countryModalPath);
            countryName.appendChild(countryModal);

            Element modal = generateModal(document);

            Element countryNameSelect = document.createElement("xsl:value-of");
            countryNameSelect.setAttribute("select", "translations/fr");

            Element countryFlag = document.createElement("img");
            countryFlag.setAttribute("width", "25");
            countryFlag.setAttribute("height", "20");

            Element countryFlagSrc = document.createElement("xsl:attribute");
            countryFlagSrc.setAttribute("name", "src");
            Element countryFlagSrcPath = document.createElement("xsl:value-of");
            countryFlagSrcPath.setAttribute("select", "flag");

            countryFlagSrc.appendChild(countryFlagSrcPath);
            countryFlag.appendChild(countryFlagSrc);

            countryName.appendChild(countryNameSelect);
            countryName.appendChild(countryFlag);
            countries.appendChild(countryName);
            countries.appendChild(modal);

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

    private String generateFilter() {
        String filter = "[";

        if(this.continent != null){
            filter += "region = " + this.continent + " and ";
        }
        if(this.language != null){
            //filter += "region = " + this.language + " and ";
        }
        if(this.minSize != Integer.MIN_VALUE){
            filter += "area > " + this.minSize + " and ";
        }
        if(this.maxSize != Integer.MAX_VALUE){
            filter += "area < " + this.maxSize + " and ";
        }

        if(filter.length() > 1) {
            return filter.substring(0, filter.length() - 5) + "]";
        }

        return "";
    }

    private Element generateModal(Document document) {
        Element modal = document.createElement("div");
        modal.setAttribute("class", "modal fade");
        modal.setAttribute("tab-index", "-1");
        modal.setAttribute("role", "dialog");

        Element modalId = document.createElement("xsl:attribute");
        modalId.setAttribute("name", "id");

        Element modalIdPath = document.createElement("xsl:value-of");
        modalIdPath.setAttribute("select", "alpha3Code");

        Element modalDialog = document.createElement("div");
        modalDialog.setAttribute("class", "modal-dialog");
        modalDialog.setAttribute("role", "document");

        Element modalContent = document.createElement("div");
        modalContent.setAttribute("class", "modal-content");

        Element modalHeader = document.createElement("div");
        modalHeader.setAttribute("class", "modal-header");

        Element modalTitle = document.createElement("h5");
        modalTitle.setAttribute("class", "modal-title");
        Element modalTitleValue = document.createElement("xsl:value-of");
        modalTitleValue.setAttribute("select", "translations/fr");
        modalTitle.appendChild(modalTitleValue);

        Element modalBody = document.createElement("div");
        modalBody.setAttribute("class", "modal-body");

        Element modalBodyContainer = document.createElement("div");
        modalBodyContainer.setAttribute("class", "container-fluid");

        Element modalBodyRow = document.createElement("div");
        modalBodyRow.setAttribute("class", "row");

        Element modalBodyColImg = document.createElement("div");
        modalBodyColImg.setAttribute("class", "col-md-6");

        Element countryFlag = document.createElement("img");
        countryFlag.setAttribute("width", "150");
        countryFlag.setAttribute("height", "125");

        Element countryFlagSrc = document.createElement("xsl:attribute");
        countryFlagSrc.setAttribute("name", "src");
        Element countryFlagSrcPath = document.createElement("xsl:value-of");
        countryFlagSrcPath.setAttribute("select", "flag");
        countryFlagSrc.appendChild(countryFlagSrcPath);
        countryFlag.appendChild(countryFlagSrc);
        modalBodyColImg.appendChild(countryFlag);

        Element modalBodyColInfos = document.createElement("div");
        modalBodyColInfos.setAttribute("class", "col-md-6");

        Element countryPopulation = document.createElement("p");
        Element countryPopulationPath = document.createElement("xsl:value-of");
        countryPopulationPath.setAttribute("select", "concat(concat('Population: ', population), ' habitants')");
        countryPopulation.appendChild(countryPopulationPath);

        Element countrySize = document.createElement("p");
        Element countrySizePath = document.createElement("xsl:value-of");
        countrySizePath.setAttribute("select", "concat(concat('Superficie: ', area), ' km^2')");
        countrySize.appendChild(countrySizePath);

        Element countryContinent = document.createElement("p");
        Element countryContinentPath = document.createElement("xsl:value-of");
        countryContinentPath.setAttribute("select", "concat('Continent: ', region)");
        countryContinent.appendChild(countryContinentPath);

        Element countrySubContinent = document.createElement("p");
        Element countrySubContinentPath = document.createElement("xsl:value-of");
        countrySubContinentPath.setAttribute("select", "concat('Sous-Continent: ', subregion)");
        countrySubContinent.appendChild(countrySubContinentPath);

        Element countryCity = document.createElement("p");
        Element countryCityPath = document.createElement("xsl:value-of");
        countryCityPath.setAttribute("select", "concat('Capitale: ', capital)");
        countryCity.appendChild(countryCityPath);

        Element languagesTitle = document.createElement("h2");
        languagesTitle.setTextContent("Langues parlées");

        Element languagesPath = document.createElement("xsl:for-each");
        languagesPath.setAttribute("select", "languages/element");

        Element langs = document.createElement("p");
        Element langsNameSelect = document.createElement("xsl:value-of");
        langsNameSelect.setAttribute("select", "name");
        langs.appendChild(langsNameSelect);
        languagesPath.appendChild(langs);

        Element modalFooter = document.createElement("div");
        modalFooter.setAttribute("class", "modal-footer");

        Element modalButtonClose = document.createElement("button");
        modalButtonClose.setAttribute("type", "button");
        modalButtonClose.setAttribute("class", "btn btn-primary");
        modalButtonClose.setAttribute("data-dismiss", "modal");
        modalButtonClose.setTextContent("Fermer");

        modalHeader.appendChild(modalTitle);

        modalBodyColInfos.appendChild(countryCity);
        modalBodyColInfos.appendChild(countryPopulation);
        modalBodyColInfos.appendChild(countrySize);
        modalBodyColInfos.appendChild(countryContinent);
        modalBodyColInfos.appendChild(countrySubContinent);

        modalBodyRow.appendChild(modalBodyColImg);
        modalBodyRow.appendChild(modalBodyColInfos);

        modalBodyContainer.appendChild(modalBodyRow);

        modalBody.appendChild(modalBodyContainer);
        modalBody.appendChild(languagesTitle);
        modalBody.appendChild(languagesPath);

        modalFooter.appendChild(modalButtonClose);

        modalContent.appendChild(modalHeader);
        modalContent.appendChild(modalBody);
        modalContent.appendChild(modalFooter);

        modalDialog.appendChild(modalContent);

        modalId.appendChild(modalIdPath);
        modal.appendChild(modalId);
        modal.appendChild(modalDialog);

        return modal;
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
