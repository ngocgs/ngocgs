
package java_xml;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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


public class frame {
    JFrame jme;
    JTable table;
    JScrollPane scr;
    JLabel page, devPage  ;
    JButton btpre, btnext;
    int count, trang = 1, sotrang;
    DefaultTableModel deTM;
    JPanel jp;
    
    
    public  frame () throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
    // 
        jme = new JFrame("frameJtable");
        jme.setSize(800, 500);
        jme.setLayout(null);
        
        // default table model: thêm cột vào mô hình bảng
        deTM = new DefaultTableModel();
        deTM.addColumn("ID");
        deTM.addColumn("userName");
        deTM.addColumn("fullName");
        deTM.addColumn("email");
        deTM.addColumn("password");
        deTM.addColumn("birthday");
        deTM.addColumn("gender");
        deTM.addColumn("avatar");
        deTM.addColumn("status");
        //
        table = new JTable();
        table.setModel(deTM);
        scr = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scr.setSize(780, 200);
        jme.add(scr);
        
        //code in insert data vào bảng
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
        
        ArrayList<Member> list;
        String[] str = new String[9]; 
            
        for( int i =0; i<nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            Element elm= (Element) node;
            count++;
        }
    
        for( int i=0; i<3; i++){
            Node node = nodeList.item(i);
            Element elm= (Element) node;
            
            str[0] = elm.getAttribute("id");
            str[1] = elm.getElementsByTagName("UserName")   .item(0).getTextContent();
            str[2] = elm.getElementsByTagName("FullName")   .item(0).getTextContent();
            str[3] = elm.getElementsByTagName("Email")      .item(0).getTextContent();
            str[4] = elm.getElementsByTagName("Password")   .item(0).getTextContent();
            str[5] = elm.getElementsByTagName("Birthday")   .item(0).getTextContent();
            str[6] = elm.getElementsByTagName("Gender")     .item(0).getTextContent();
            str[7] = elm.getElementsByTagName("Avatar")     .item(0).getTextContent();
            str[8] = elm.getElementsByTagName("Status")     .item(0).getTextContent();
            deTM.addRow(str);
        }
        
        
        
        // pre
        btpre = new JButton(" < ");
        btpre.setBounds(50, 220, 100, 30);
        btpre.addActionListener(new Pre());
        jme.add(btpre);
        
        // next
        btnext = new JButton(" > ");
        btnext.setBounds(200, 220, 100, 30);
        btnext.addActionListener(new Next());
        jme.add(btnext);
        
        //trang
        page = new JLabel("1");
        page.setBounds(170, 220, 30, 30);
        jme.add(page);
        
        //chia trang
        devPage = new JLabel();
        devPage.setBounds(330, 220, 30, 30);
        jme.add(devPage);
        
        jme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jme.setVisible(true);
    }
    
    public static void main(String[] args) throws IOException, MalformedURLException, ParserConfigurationException, SAXException {
        frame f = new frame();
        f.countDB();
    }
    
    public void countDB () {
        System.out.println(count);
        
        if( count % 3 == 0){
            sotrang = count/3;
        }else{
            sotrang = 1 + count/3;
        }
        
        devPage.setText("1/" + sotrang);
        page.setText("1");
    }
    
    class Pre implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if((trang > 1)){
                try {
                    trang --;
                    loadData(trang);
                    page.setText("" + trang );
                    devPage.setText( trang + "/" + sotrang);
                } catch (Exception ex) {
                    Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //ok
    class Next implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if((trang < sotrang)){
                try {
                    trang ++;
                    loadData(trang);
                    page.setText("" + trang );
                    devPage.setText( trang + "/" + sotrang);
                } catch (Exception ex) {
                    Logger.getLogger(frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void loadData( int trang)throws Exception {
        int start, end;
        start = 3*(trang-1) ;
        end = 3*trang;
        if( 3*trang > count ){
            end = count;
        }
        
        deTM.removeRow(0);
        deTM.removeRow(0);
        deTM.removeRow(0);
        
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
        
        String[] str = new String[9]; 
            
        for( int i = start; i< end ; i++){
            Node node = nodeList.item(i);
            Element elm= (Element) node;
            
            str[0] = elm.getAttribute("id");
            str[1] = elm.getElementsByTagName("UserName")   .item(0).getTextContent();
            str[2] = elm.getElementsByTagName("FullName")   .item(0).getTextContent();
            str[3] = elm.getElementsByTagName("Email")      .item(0).getTextContent();
            str[4] = elm.getElementsByTagName("Password")   .item(0).getTextContent();
            str[5] = elm.getElementsByTagName("Birthday")   .item(0).getTextContent();
            str[6] = elm.getElementsByTagName("Gender")     .item(0).getTextContent();
            str[7] = elm.getElementsByTagName("Avatar")     .item(0).getTextContent();
            str[8] = elm.getElementsByTagName("Status")     .item(0).getTextContent();
            deTM.addRow(str);
        }
    
        
    }
}
