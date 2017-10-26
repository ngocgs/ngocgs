
package java_xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class xml {
    
    JFrame jme;
    JTable table;
    JScrollPane scr;
    JLabel page, devPage  ;
    JButton btpre2, btpre1, btnext1, btnext2;
    JButton btadd, btupdate, btdel;
    DefaultTableModel deTM;
    NodeList nodeList;
    
    public static void main(String[] args) throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        System.out.println(" loading......... ");
        
        String url = "https://youtube-api-challenger.appspot.com/xml/members";
        
        URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document doc = builder.parse(con.getInputStream());
        doc.getDocumentElement().normalize();
        
        NodeList nodeList = doc.getElementsByTagName("Member");
        
        for( int i =0; i<nodeList.getLength(); i++){
            System.out.println("------------------");
            Node node = nodeList.item(i);
            Element element = (Element) node;
            System.out.println( "Id " + element.getAttribute("id"));
            System.out.println("UserName " + element.getElementsByTagName("UserName")   .item(0).getTextContent() );
            System.out.println("FullName " + element.getElementsByTagName("FullName")   .item(0).getTextContent() );
            System.out.println("Email " +    element.getElementsByTagName("Email")      .item(0).getTextContent() );
            System.out.println("Password " + element.getElementsByTagName("Password")   .item(0).getTextContent() );
            System.out.println("Birthday " + element.getElementsByTagName("Birthday")   .item(0).getTextContent() );
            System.out.println("Gender " +   element.getElementsByTagName("Gender")     .item(0).getTextContent() );
            System.out.println("Avatar " +   element.getElementsByTagName("Avatar")     .item(0).getTextContent() );
            System.out.println("Status " +   element.getElementsByTagName("Status")     .item(0).getTextContent() );
        }
    }
}
