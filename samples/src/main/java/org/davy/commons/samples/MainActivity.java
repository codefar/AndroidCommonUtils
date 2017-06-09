package org.davy.commons.samples;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.Toast;

import org.davy.commons.FileUtils;
import org.davy.commons.IOUtils;
import org.davy.commons.NetworkUtil;
import org.davy.commons.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, NetworkUtil.getCurrentIp(this), Toast.LENGTH_LONG).show();
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("AndroidManifest.xml");
            int length = inputStream.available();
            byte[] datas = new byte[length];
            getAssets().open("AndroidManifest.xml").read(datas);
            String content = new String(datas);
            content =content.replaceAll("\\s*\n\\s*","");
            System.out.println(content);
            Document document = XmlUtil.parserXml(content);
            System.out.println(document.getXmlEncoding() + "-" + document.getXmlVersion() + "-" + document.getXmlStandalone());
            StringBuilder stringBuilder = new StringBuilder();
            printNode("=", document, stringBuilder);
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        Test test = new Test();
        test.show();
    }

    private String nodeType2String(short type) {
        String str = "WRONG_TYTPE";
        switch (type) {
            case 1:
                str = "ELEMENT";
                break;
            case 2:
                str = "ATTRIBUTE";
                break;
            case 3:
                str = "TEXT";
                break;
            case 4:
                str = "CDATA_SECTION";
                break;
            case 5:
                str = "ENTITY_REFERENCE";
                break;
            case 6:
                str = "ENTITY";
                break;
            case 7:
                str = "PROCESSING_INSTRUCTION";
                break;
            case 8:
                str = "COMMENT";
                break;
            case 9:
                str = "DOCUMENT";
                break;
            case 10:
                str = "DOCUMENT_TYPE";
                break;
            case 11:
                str = "DOCUMENT_FRAGMENT";
                break;
            case 12:
                str = "NOTATION";
                break;
        }
        return str;
    }

   private void printNode(String prex, Node node, StringBuilder stringBuilder){
       if (node == null) {
           return;
       }
       stringBuilder.append(prex).append("name:" + node.getNodeName() + " \n")
               .append(prex).append("type:" + nodeType2String(node.getNodeType()) + " \n")
               .append(prex).append("value:" + node.getNodeValue() + " \n")
               .append(prex).append("nsUrl:" + node.getNamespaceURI() + " \n")
               .append(prex).append("Attrs:\n" + printNodeMap(node.getAttributes()) + " \n");

       if (node.getChildNodes() != null && node.getChildNodes().getLength() > 0 ) {
           for (int i = 0; i < node.getChildNodes().getLength(); i++) {
               printNode(prex + "=",node.getChildNodes().item(i), stringBuilder);
           }
       }
   }

    private String printNodeMap(NamedNodeMap nodemap){
        if (nodemap == null) {
            return "\n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int len = nodemap.getLength();

        if (len > 0) {
            for (int i = 0; i < len; i++) {
                stringBuilder.append(nodemap.item(i).getNodeName() + ":=" + nodemap.item(i).getNodeValue() + "\n");
            }
        } else {
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
