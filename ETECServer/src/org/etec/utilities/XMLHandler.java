package org.etec.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.etec.datastructures.List;
import org.etec.management.DistributionCenter;
import org.etec.management.GasStation;
import org.etec.management.Product;
import org.etec.management.Store;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHandler {
	
	/**
	 * Carga las tiendas iniciales.
	 * @param list la lista donde se agregan.
	 */
	public static void load_stores(List<Store> list){
		
		try{
			File stores_file = new File("C:/Users/dell-pc/Desktop/ETECServerG/ETECServer/xmlfiles/stores.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(stores_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("store");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					list.addLast(new Store("Store", element.getElementsByTagName("name").item(0).getTextContent(), element.getElementsByTagName("category").item(0).getTextContent(), Integer.parseInt(element.getAttribute("id"))));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga los centros de distribución iniciales.
	 * @param list la lista donde se agregan.
	 */
	public static void load_centers(List<DistributionCenter> list){
		try{
			File centers_file = new File("C:/Users/dell-pc/Desktop/ETECServerG/ETECServer/xmlfiles/centers.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(centers_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("center");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					list.addLast(new DistributionCenter("Center", element.getElementsByTagName("name").item(0).getTextContent(), Integer.parseInt(element.getAttribute("id"))));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga las gasolineras iniciales.
	 * @param list la lista donde se agregan.
	 */
	public static void load_stations(List<GasStation> list){
		try{
			File stations_file = new File("C:/Users/dell-pc/Desktop/ETECServerG/ETECServer/xmlfiles/stations.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(stations_file);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("station");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					list.addLast(new GasStation("Station", element.getElementsByTagName("name").item(0).getTextContent(), Integer.parseInt(element.getAttribute("id"))));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga los productos de las tiendas electronicas.
	 * @param list la lista donde se agregan.
	 */
	public static void load_electronic_products(Product[] list,List<Product> all) {
		try{
			File electronic_products = new File("C:/Users/dell-pc/Desktop/ETECServerG/ETECServer/xmlfiles/electronics.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(electronic_products);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("product");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Product product = new Product(element.getElementsByTagName("name").item(0).getTextContent(), 
							Integer.parseInt(element.getElementsByTagName("cost").item(0).getTextContent()), Integer.parseInt(element.getAttribute("id")));
					list[temp] = product;
					all.addFirst(product);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga los productos de las tiendas de muebles.
	 * @param list la lista donde se agregan.
	 */
	public static void load_furniture_products(Product[] list, List<Product> all) {
		try{
			File furniture_products = new File("C:/Users/dell-pc/Desktop/ETECServerG/ETECServer/xmlfiles/furniture.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(furniture_products);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("product");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Product product = new Product(element.getElementsByTagName("name").item(0).getTextContent(), 
							Integer.parseInt(element.getElementsByTagName("cost").item(0).getTextContent()), Integer.parseInt(element.getAttribute("id")));
					list[temp] = product;
					all.addFirst(product);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga los productos de las tiendas de ropa.
	 * @param list la lista donde se agregan.
	 */
	public static void load_clothing_products(Product[] list , List<Product> all) {
		try{
			File clothing_products = new File("C:/Users/dell-pc/Desktop/ETECServerG/ETECServer/xmlfiles/clothing.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(clothing_products);
			
			document.getDocumentElement().normalize();
			
			NodeList node_list = document.getElementsByTagName("product");
			
			for(int temp = 0; temp < node_list.getLength(); temp++){
				Node node = node_list.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					Product product = new Product(element.getElementsByTagName("name").item(0).getTextContent(), 
							Integer.parseInt(element.getElementsByTagName("cost").item(0).getTextContent()), Integer.parseInt(element.getAttribute("id")));
					list[temp] = product;
					all.addFirst(product);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
