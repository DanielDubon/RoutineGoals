/**
 * 
 */
package dataAccessLayer;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.summary.ResultSummary;


import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class EmbeddedNeo4j implements AutoCloseable{
//crear driver
    private final Driver driver;
    

    public EmbeddedNeo4j( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                                     "SET a.message = $message " +
                                                     "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }
    
    public LinkedList<Ejercicio> getStrings(){
		try(Session session = driver.session()){
			LinkedList<Ejercicio> Strings = session.readTransaction( new TransactionWork<LinkedList<Ejercicio>>()
				{
				@Override
                public LinkedList<Ejercicio> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (e:Entrenamiento) RETURN e.name , e.intensidad, e.duracion, e.objetivo, e.estilo");
                    LinkedList<Ejercicio> myStrings = new LinkedList<Ejercicio>();
                    List<Record> registros = result.list();
                    
                    for (int i = 0; i < registros.size(); i++) {
                    	//myactors.add(registros.get(i).toString());
                    	String name = registros.get(i).get("e.name").toString();
                    	
                    	String intensidad = registros.get(i).get("e.intensidad").toString();
                    	
                    	String duracion = registros.get(i).get("e.duracion").toString();
                    	
                    	String objetivo = registros.get(i).get("e.objetivo").toString();
                    	
                    	String estilo = registros.get(i).get("e.estilo").toString();
                    	
                    	
                    	Ejercicio rs = new Ejercicio(name, intensidad, duracion, objetivo, estilo);
                   	 	myStrings.add(rs);
                    }
                    
                    return myStrings;
                	}	
				
				});
			return Strings;
		}
	}
    

    public LinkedList<Ejercicio> getSearch(String intensidad, String duracion, String objetivo, String estilo){
		try ( Session session = driver.session() ){
   		 
   		 LinkedList<Ejercicio> Strings = session.readTransaction( new TransactionWork<LinkedList<Ejercicio>>()
            {
                @Override
                
                
                public LinkedList<Ejercicio> execute( Transaction tx )
                {
                	System.out.println(intensidad);
                	System.out.println(duracion);
                	System.out.println(objetivo);
                	System.out.println(estilo);
                    Result result = tx.run( "MATCH (e:Entrenamiento) WHERE e.intensidad = '"+intensidad+"' OR e.duracion = '"+duracion+"' OR e.objetivo = '"+objetivo+"' OR e.estilo = '"+estilo+"' RETURN e.name, e.intensidad, e.duracion, e.objetivo, e.estilo;");     
                    
                    LinkedList<Ejercicio> myStrings = new LinkedList<Ejercicio>();
                    List<Record> registros = result.list();
                    for (int i = 0; i < registros.size(); i++) {
                   	 //myactors.add(registros.get(i).toString());
                    	String name = registros.get(i).get("e.name").toString();
                    	
                    	String intensidad = registros.get(i).get("e.intensidad").toString();
                    	
                    	String duracion = registros.get(i).get("e.duracion").toString();
                    	
                    	String objetivo = registros.get(i).get("e.objetivo").toString();
                    	
                    	String estilo = registros.get(i).get("e.estilo").toString();
                  
                    
                    	Ejercicio rs = new Ejercicio(name, intensidad, duracion, objetivo, estilo);
                    	
                    	myStrings.add(rs);
                    }
                    
                    return myStrings;
                }
            } );
            
            return Strings;
        }
	}
	
	public String insertString(String name, String intensidad, String duracion, String objetivo, String estilo){
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    //tx.run( "CREATE (Test:Movie {title:'" + title + "', released:"+ releaseYear +", tagline:'"+ tagline +"'})");
                	tx.run("CREATE (e:Entrenamiento {name: '"+name+"', intensidad: '"+intensidad+"', duracion: '"+duracion+"', objetivo: '"+objetivo+"', estilo: '"+estilo+"'})");
                    
                    return "OK";
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }
	
	
}
