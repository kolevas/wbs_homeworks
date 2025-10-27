package mk.ukim.finki;

import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.VCARD;

public class JenaModel {

    public static void main(String[] args) {
        Model model = ModelFactory.createDefaultModel();
        String myURI = "https://www.linkedin.com/in/snezhana-koleva-2240182bb/";
        Resource resource = model.createResource(myURI)
                .addProperty(VCARD.FN, "Snezhana Koleva")
                .addProperty(VCARD.NICKNAME, "Sneze")
                .addProperty(FOAF.age, model.createTypedLiteral(22))
                .addProperty(FOAF.gender, "female")
                .addProperty(FOAF.homepage, model.createResource("https://portfolio.snezana.dev"));
        StmtIterator iter = model.listStatements();

        System.out.println("Printing with model.listStatements():\n");
        while (iter.hasNext())
        {
            Statement stmt = iter.nextStatement();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();
            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }

        System.out.println("\nPrinting with model.write(), in RDF/XML:\n");
        model.write(System.out, "RDF/XML");

        System.out.println("\nPrinting with model.write(), in Pretty RDF/XML:\n");
        model.write(System.out, "RDF/XML-ABBREV");

        System.out.println("\nPrinting with model.write(), in N-Triples:\n");
        model.write(System.out, "N-TRIPLES");

        System.out.println("\nPrinting with model.write(), in Turtle:\n");
        model.write(System.out, "TURTLE");

    }
}
