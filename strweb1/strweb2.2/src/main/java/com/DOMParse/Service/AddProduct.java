package com.DOMParse.Service;

import com.DOMParse.Entity.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddProduct {
    public static void main(String[] args) throws IOException,
            ParserConfigurationException, SAXException, TransformerException {

        File file = new File("src/main/resources/products.xml");
        BufferedReader input = null;
        try{
            input = new BufferedReader(new InputStreamReader(System.in));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document  = builder.parse(file);
            System.out.println("Enter product name: ");
            String name = input.readLine();
            System.out.println("Enter product brand: ");
            String brand = input.readLine();
            System.out.println("Enter type: ");
            String type = input.readLine();
            System.out.println("Enter product price: ");
            Double price = Double.parseDouble(input.readLine());
            Product product = new Product(name, brand, type, price);

            /*Element element = getEmployeeNode(employee, document);
            document.getDocumentElement().appendChild(element);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult sResult = new StreamResult(file);
            transformer.transform(source, sResult);
            System.out.println("Product has been added successfully.");
*/

        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    private static Element getProductNode(Product product, Document document) {

        Element element = document.createElement("product");
        NodeList list = document.getElementsByTagName("product");
        int count = list.getLength();
        element.setAttribute("id", String.valueOf(++count));

        /*Element name = getPropertyNode("name", document, product.getName());
        element.appendChild(name);
        Element brand = getPropertyNode("brand", document, product.getBrand());
        element.appendChild(brand);
        Element type = getPropertyNode("type", document, product.getType());
        element.appendChild(type);
        Element price = getPropertyNode("role", document, String.valueOf(product.getPrice()) );
        element.appendChild(price);*/
        return element;
    }


    private static Element getPropertyNode(String property, Document document, String value) {

        Element element = document.createElement(property);
        element.setTextContent(value);
        return element;
    }

}

