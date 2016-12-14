/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mongodb_teste;

/**
 *
 * @author miguelfreitas
 */
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import static java.lang.String.format;
import static java.lang.String.format;
import org.bson.Document;

import java.text.DateFormat;
import static java.text.MessageFormat.format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.util.Arrays.asList;
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bson.conversions.Bson;


public class MongoDB_teste {

    /**
     * @param args the command line arguments
     */
    public static void teste(String[] args) throws ParseException {
        // TODO code application logic here
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("BDprject");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        /*db.getCollection("Professores").insertOne(
        new Document("nome", "Lana")
                .append("email", "123@ufu.com")
                .append("materias", asList(
                        new Document()
                                .append("nome", "EDG")
                                .append("nota", "B"),
                        new Document()
                                .append("nome", "BD")
                                .append("nota", "B")))
                .append("telefone", "435341543"));*/
        Iterable<Document> iterable = db.getCollection("Professores").aggregate(Arrays.asList((Aggregates.match(new Document("nome", "Miguel"))), 
                                                                                Aggregates.unwind("$materias"), 
                                                                                Aggregates.group("$materias.nota", Accumulators.sum("total", 1)),
                                                                                Aggregates.sort(new Document("total", -1))));
        Iterator<Document> it = iterable.iterator();
        while(it.hasNext()){
            System.out.println(it.next().get("_id"));
        }
    }
    
}
