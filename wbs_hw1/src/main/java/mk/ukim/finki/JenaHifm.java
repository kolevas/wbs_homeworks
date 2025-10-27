package mk.ukim.finki;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDFS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JenaHifm {
    public static void main(String[] args) {

        Model model = ModelFactory.createDefaultModel();
        model.read("file:/Users/snezhanakoleva/IdeaProjects/wbs/wbs_hw1/src/main/resources/hifm-dataset.ttl",null, "TURTLE");
        System.out.println("Printing model read from file:\n");
        List<String> drugNames = new ArrayList<>();

        //22
        ResIterator drugs = model.listResourcesWithProperty(RDFS.label);
        while (drugs.hasNext()) {
            Resource drug = drugs.nextResource();
            Statement stmt = drug.getProperty(RDFS.label);
            if (stmt != null && stmt.getObject().isLiteral()) {
                drugNames.add(stmt.getObject().asLiteral().getString());
            }
        }
        Collections.sort(drugNames);
        System.out.println("All drugs (sorted alphabetically):");
        drugNames.forEach(System.out::println);

        //23
        Resource exampleDrug = model.listResourcesWithProperty(RDFS.label).nextResource();
        System.out.println("\n\n\n\nRelations for drug: " + exampleDrug.getProperty(RDFS.label).getString());
        StmtIterator props = exampleDrug.listProperties();
        while (props.hasNext()) {
            Statement stmt = props.nextStatement();
            String predicate = stmt.getPredicate().getLocalName();
            String object;
            if (stmt.getObject().isLiteral()) {
                object = stmt.getObject().asLiteral().getString();
            } else {
                object = stmt.getObject().asResource().getURI();
            }
            System.out.println(predicate + " -> " + object);
        }
        //24
        Property similarTo = model.createProperty("http://www.hifm.org.mk/ontology#similarTo");
        StmtIterator similarDrugs = exampleDrug.listProperties(similarTo);
        System.out.println("\n\n\n\nDrugs similar to " + exampleDrug.getProperty(RDFS.label).getString() + ":");
        while (similarDrugs.hasNext()) {
            Statement stmt = similarDrugs.nextStatement();
            Resource similar = stmt.getObject().asResource();
            Statement nameStmt = similar.getProperty(RDFS.label);
            if (nameStmt != null) {
                System.out.println(" - " + nameStmt.getString());
            }
        }
        //25
        Property price = model.createProperty("http://www.hifm.org.mk/ontology#refPriceWithVAT");
        Statement priceStmt = exampleDrug.getProperty(price);
        if (priceStmt != null) {
            System.out.println("\nPrice of " + exampleDrug.getProperty(RDFS.label).getString() + ": " +
                    priceStmt.getObject().asLiteral().getString());
        }

        System.out.println("Prices of similar drugs:");
        similarDrugs = exampleDrug.listProperties(similarTo);
        while (similarDrugs.hasNext()) {
            Statement stmt = similarDrugs.nextStatement();
            Resource similar = stmt.getObject().asResource();
            Statement simNameStmt = similar.getProperty(RDFS.label);
            Statement simPriceStmt = similar.getProperty(price);
            if (simNameStmt != null && simPriceStmt != null) {
                System.out.println(" - " + simNameStmt.getString() + ": " + simPriceStmt.getObject().asLiteral().getString());
            }
        }
    }
}
