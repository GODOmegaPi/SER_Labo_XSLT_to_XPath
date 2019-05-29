import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XSLGenerator {
    private String continent, language;
    private int minSize, maxSize;

    public XSLGenerator(){

    }

    public void generate(){
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
           final DocumentBuilder builder = factory.newDocumentBuilder();
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
