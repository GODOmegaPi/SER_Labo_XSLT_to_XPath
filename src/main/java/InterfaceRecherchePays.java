import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class InterfaceRecherchePays extends JFrame {

    private JPanel panelRecherche = new JPanel(new FlowLayout());

    private JComboBox<String> continents = new JComboBox<>();
    private JComboBox<String> langages = new JComboBox<>();
    private JButton createXSL = new JButton("Générer XSL");
    private JTextField superficieMin = new JTextField(5);
    private JTextField superficieMax = new JTextField(5);

    public InterfaceRecherchePays(File xmlFile) {
        createXSL.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                XSLGenerator xslg = new XSLGenerator();
            }
        });

        // parse and load file into memory;
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(xmlFile);
            XPathFactory xpath = XPathFactory.instance();
            XPathExpression<Element> exprRegions = xpath.compile("//element/region[not(. = following::region/.)]", Filters.element());

            continents.addItem("Tous");
            for (Element element : exprRegions.evaluate(document)) {
                continents.addItem(element.getValue());
            }

            XPathExpression<Element> exprLang = xpath.compile("//element/languages/element/name[not(. = following::name/.)]", Filters.element());

            langages.addItem("Tous");
            for (Element element : exprLang.evaluate(document)) {
                langages.addItem(element.getValue());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // create xpath factory
        XPathFactory xpath = XPathFactory.instance();

        setLayout(new BorderLayout());

        panelRecherche.add(new JLabel("Choix d'un continent"));
        panelRecherche.add(continents);

        panelRecherche.add(new JLabel("Choix d'une langue"));
        panelRecherche.add(langages);

        panelRecherche.add(new JLabel("Superficie minimum"));
        panelRecherche.add(superficieMin);

        panelRecherche.add(new JLabel("Superficie maximum"));
        panelRecherche.add(superficieMax);

        panelRecherche.add(createXSL);

        add(panelRecherche, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Interface de recherche de pays");
    }

    public static void main(String ... args) {
        new InterfaceRecherchePays(new File("src/main/resources/data/countries.xml"));
    }
}
