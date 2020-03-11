package eu.rutolo.automusico.manager;

import android.util.Log;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import eu.rutolo.automusico.Utils;
import eu.rutolo.automusico.db.Categoria;

public class Composicion {

    private String nombre;
    private HashMap<Categoria, Integer> vals;

    public Composicion() {
        vals = new HashMap<>();
    }

    public Composicion(HashMap<Categoria, Integer> vals) {
        this.vals = vals;
    }

    public void addVal(Categoria c, int val) {
        vals.put(c, val);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void saveToXml(File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.newDocument();
            // elemnto raiz
            Element root = doc.createElement("composicion");
            // atrib version, por si las moscas
            Attr attrVersion = doc.createAttribute("version");
            attrVersion.setValue("1");
            root.setAttributeNode(attrVersion);
            doc.appendChild(root);

            // nombre
            Element nom = doc.createElement("nombre");
            nom.appendChild(doc.createTextNode(getNombre()));
            root.appendChild(nom);

            // n elementos categoria
            for (Map.Entry<Categoria, Integer> e : vals.entrySet()) {
                Element cat = doc.createElement("categoria");
                root.appendChild(cat);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(Integer.toString(e.getKey().getId())));
                cat.appendChild(id);

                Element cantidad = doc.createElement("cantidad");
                cantidad.appendChild(doc.createTextNode(Integer.toString(e.getValue())));
                cat.appendChild(cantidad);
            }

            // escribir datos al archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer trans = transformerFactory.newTransformer();
            DOMSource src = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            trans.transform(src, result);

            Log.d(Utils.DTAG, "Guardado en " + file.getAbsolutePath());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
