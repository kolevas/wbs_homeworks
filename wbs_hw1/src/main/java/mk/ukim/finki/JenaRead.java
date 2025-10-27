package mk.ukim.finki;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.VCARD;

public class JenaRead {
    public static void main(String[] args) {
    Model model = ModelFactory.createDefaultModel();
    model.read("file:/Users/snezhanakoleva/IdeaProjects/wbs/wbs_hw1/src/main/resources/my_model.xml",null, "RDF/XML");
    System.out.println("Printing model read from file:\n");
    model.write(System.out, "TURTLE");

        String myURI = "https://www.linkedin.com/in/snezhana-koleva-2240182bb/";
        Resource me = model.getResource(myURI);

        String fullName = me.getProperty(VCARD.FN).getString();
        String nickname = me.getProperty(VCARD.NICKNAME).getString();
        int age = me.getProperty(FOAF.age).getInt();
        String gender = me.getProperty(FOAF.gender).getString();
        Resource homepage = me.getProperty(FOAF.homepage).getResource();

        System.out.println("\nValues from the resource:");
        System.out.println("Full Name: " + fullName);
        System.out.println("Nickname: " + nickname);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Homepage: " + homepage.getURI());
    }
}