package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLManager {
    public void saveGameState(Board board, String filename) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("game-state");
        doc.appendChild(rootElement);

        Element boardElement = doc.createElement("board");
        rootElement.appendChild(boardElement);

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                Element cellElement = doc.createElement("cell");
                cellElement.setAttribute("row", String.valueOf(i));
                cellElement.setAttribute("column", String.valueOf(j));
                cellElement.setAttribute("disc", board.getDiscAt(i, j).toString());
                boardElement.appendChild(cellElement);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));

        transformer.transform(source, result);
    }

    public Board loadGameState(String filename) throws ParserConfigurationException, SAXException, IOException {
        File file = new File(filename);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList cellList = doc.getElementsByTagName("cell");

        // Feltételezzük, hogy a tábla mérete 6x7
        Board board = new Board(6, 7);

        for (int i = 0; i < cellList.getLength(); i++) {
            Node cellNode = cellList.item(i);
            if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
                Element cellElement = (Element) cellNode;
                int row = Integer.parseInt(cellElement.getAttribute("row"));
                int column = Integer.parseInt(cellElement.getAttribute("column"));
                Disc disc = Disc.valueOf(cellElement.getAttribute("disc"));
                board.setDiscAt(row, column, disc);
            }
        }

        return board;
    }
}