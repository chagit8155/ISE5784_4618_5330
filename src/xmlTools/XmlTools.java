package xmlTools;

import scene.Scene;
import primitives.*;
import renderer.*;
import geometries.*;
import lighting.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.MissingResourceException;



/**
 * A class to render a Scene object from an XML file.
 */
public class XmlTools {
    /**
     * Renders a scene object from an XML file.
     *
     * @param  str   the path to the XML file
     * @return       the Scene object rendered from the XML file
     */
    public static Scene renderFromXmlFile(String str) throws ParserConfigurationException, IOException, SAXException {
        List<String> list = List.of("sphere","cylinder", "triangle", "plane","polygon","tube");
        File inputFile = new File(str);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        Element sceneElement = doc.getDocumentElement();

        // Creating the Scene object
        Scene scene;
        String nameColorStr = sceneElement.getAttribute("name");
        if (!(nameColorStr == "" || nameColorStr.isEmpty())) {
            scene = new Scene(sceneElement.getAttribute("name"));
        }
        else {
            throw new MissingResourceException("Missing scene name", Camera.class.getSimpleName(),"name");
        }

        // Extracting background color attribute
        String backgroundColorStr = sceneElement.getAttribute("background-color");
        if (!(backgroundColorStr == "" || backgroundColorStr.isEmpty())) {
            String[] bgColorArray = backgroundColorStr.split(" ");
            Color backgroundColor = new Color(Integer.parseInt(bgColorArray[0]),
                    Integer.parseInt(bgColorArray[1]),
                    Integer.parseInt(bgColorArray[2]));
            scene.setBackground(backgroundColor);
        }

        // Extracting ambient light
        NodeList ambientLightList = sceneElement.getElementsByTagName("ambient-light");
        if (ambientLightList.getLength() > 0) {
            Element ambientLightElement = (Element) ambientLightList.item(0);
            String ambientColorStr = ambientLightElement.getAttribute("color");
            String[] ambientColorArray = ambientColorStr.split(" ");
            Color ambientColor = new Color(Integer.parseInt(ambientColorArray[0]),
                    Integer.parseInt(ambientColorArray[1]),
                    Integer.parseInt(ambientColorArray[2]));
            AmbientLight ambientLight = new AmbientLight(ambientColor,1);
            scene.setAmbientLight(ambientLight);
        }

        // Extracting geometries
        Element geometriesElement = (Element) doc.getElementsByTagName("geometries").item(0);
        for (String i : list)
        {
            NodeList geometriesList = geometriesElement.getElementsByTagName(i);
            int length = geometriesList.getLength();
            if (length == 0) continue;
            for (int j = 0; j < length; j++) {
                Element item = (Element) geometriesList.item(j);
                scene.geometries.add(createGeometry(i, item));
            }
        }
        return scene;
    }

    /**
     * Creates a geometry object based on the type and element provided.
     *
     * @param  type    the type of geometry to create
     * @param  element the element containing the necessary information
     * @return         the created geometry object
     */
    private static Geometry createGeometry(String type, Element element) {
        switch (type) {
            case "sphere":
                Point p0Sphere= getPointFromString(element.getAttribute("center"));
                double radiusSphere = getDoubleFromString(element.getAttribute("radius"));
                return new Sphere( radiusSphere, p0Sphere);
            case "triangle":
                Point p0Triangle = getPointFromString(element.getAttribute("p0"));
                Point p1Triangle = getPointFromString(element.getAttribute("p1"));
                Point p2Triangle = getPointFromString(element.getAttribute("p2"));
                return new Triangle(p0Triangle, p1Triangle, p2Triangle);
            case "cylinder":
                String[] axisCylinder = element.getAttribute("axis").split(" {2}");
                Point p0Cylinder = getPointFromString(axisCylinder[0]);
                Vector v0Cylinder = (Vector)getPointFromString(axisCylinder[1]);
                double radiusCylinder = getDoubleFromString(element.getAttribute("radius"));
                double heightCylinder = getDoubleFromString(element.getAttribute("height"));
                return new Cylinder(radiusCylinder, new Ray(p0Cylinder, v0Cylinder), heightCylinder);
            case "plane":
                if(element.hasAttribute("normal"))
                {
                    Point p0Plane = getPointFromString(element.getAttribute("p0"));
                    Vector v0Plane = (Vector)getPointFromString(element.getAttribute("normal"));
                    return new Plane(p0Plane, v0Plane);
                }
                Point p0Plane = getPointFromString(element.getAttribute("p0"));
                Point p1Plane = getPointFromString(element.getAttribute("p1"));
                Point p2Plane = getPointFromString(element.getAttribute("p2"));
                return new Plane(p0Plane, p1Plane, p2Plane);
            case "polygon":
                Point[] points = new Point[element.getAttributes().getLength()];
                for (int i = 0; i < element.getAttributes().getLength(); i++) {
                    points[i] = getPointFromString(element.getAttribute("p"+i));
                }
                return new Polygon(points);
            case "tube":
                String[] axis = element.getAttribute("axis").split(" {2}");
                Point p0Tube = getPointFromString(axis[0]);
                Vector v0Tube = (Vector)getPointFromString(axis[1]);
                double radiusTube = getDoubleFromString(element.getAttribute("radius"));
                return new Tube(radiusTube, new Ray(p0Tube, v0Tube));
        }
        return null;
    }

    /**
     * Converts a string to a double value.
     *
     * @param  radius  the string to be converted to a double
     * @return        the double value parsed from the string
     */
    private static double getDoubleFromString(String radius) {
        return Double.parseDouble(radius);
    }

    /**
     * Parse a string to create a Point object.
     *
     * @param  str   the string containing the coordinates
     * @return      a Point object with the parsed coordinates
     */
    private static Point getPointFromString(String str) {
        String[] centerArray = str.split("\\s+");
        double x = Double.parseDouble(centerArray[0]);
        double y = Double.parseDouble(centerArray[1]);
        double z = Double.parseDouble(centerArray[2]);
        return new Point(x, y, z);
    }
}
