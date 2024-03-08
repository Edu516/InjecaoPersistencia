package Injecao;

import Base.Dados;
import Base.Nota;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Xml implements Injecao {

    @Override
    public boolean gravar(String nome, Dados dados) {
        if (nome.isEmpty()) {
            nome = "Notas";
        }

        try {
            DocumentBuilderFactory fabricaDocumentos = DocumentBuilderFactory.newInstance();
            DocumentBuilder construtorDocumento = fabricaDocumentos.newDocumentBuilder();
            Document documento = construtorDocumento.newDocument();

            Element elementoNotas = documento.createElement("Notas");
            documento.appendChild(elementoNotas);

            for (Nota nota : dados.getNotas()) {
                Element elementoNota = documento.createElement("Nota");
                elementoNota.setAttribute("cod", String.valueOf(nota.getCod()));
                elementoNota.setAttribute("dataCriacao", formatDate(nota.getDataCriacao()));

                elementoNotas.appendChild(elementoNota);

                Element elementoTitulo = documento.createElement("Titulo");
                elementoTitulo.appendChild(documento.createTextNode(nota.getTitulo()));
                elementoNota.appendChild(elementoTitulo);

                Element elementoTexto = documento.createElement("Texto");
                elementoTexto.appendChild(documento.createTextNode(nota.getTexto()));
                elementoNota.appendChild(elementoTexto);
            }

            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            Transformer transformador = fabricaTransformador.newTransformer();
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource origem = new DOMSource(documento);
            StreamResult resultado = new StreamResult(new File(nome + ".xml"));
            transformador.transform(origem, resultado);

            return true;
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Dados ler(String nome) {
        if (nome.isEmpty()) {
            nome = "Notas";
        }

        try {
            File arquivo = new File(nome + ".xml");
            DocumentBuilderFactory fabricaDocumentos = DocumentBuilderFactory.newInstance();
            DocumentBuilder construtorDocumento = fabricaDocumentos.newDocumentBuilder();
            Document documento = construtorDocumento.parse(arquivo);

            Element elementoNotas = (Element) documento.getElementsByTagName("Notas").item(0);
            List<Nota> notas = extrairNotas(elementoNotas);
            Dados d =  new Dados();
            d.setNotas(notas);
            return d;
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Nota> extrairNotas(Element elementoNotas) throws ParseException {
        NodeList nodeListNotas = elementoNotas.getElementsByTagName("Nota");
        List<Nota> notas = new ArrayList<>();

        for (int i = 0; i < nodeListNotas.getLength(); i++) {
            Node nodeNota = nodeListNotas.item(i);

            if (nodeNota.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoNota = (Element) nodeNota;

                int cod = Integer.parseInt(elementoNota.getAttribute("cod"));
                String titulo = elementoNota.getElementsByTagName("Titulo").item(0).getTextContent();
                String texto = elementoNota.getElementsByTagName("Texto").item(0).getTextContent();
                
                String dataString = elementoNota.getAttribute("dataCriacao");
                Date data = parseDate(dataString);
                Nota n = new Nota();
                n.setCod(cod);
                n.setTitulo(titulo);
                n.setTexto(texto);
                n.setDataCriacao(data);
                notas.add(n);
            }
        }

        return notas;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.parse(dateString);
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }
}
