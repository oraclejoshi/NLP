import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;


public class Sentiment {
	static StanfordCoreNLP pipeline;
	
	public static void init(){
		 Properties props = new Properties();
		  props.setProperty("annotators","tokenize, ssplit , parse, sentiment");
	      pipeline = new StanfordCoreNLP(props);
		
	}
	JSONObject  getPerson(String sentence, String sentiment){
		   JSONObject person = new JSONObject();
		   person .put("sentence", sentence);
		   person .put("sentiment", sentiment);
		   return person ;
		} 

	 public static JSONArray findSentiment(String line) {
		 String[] s =  line.split(Pattern.quote("but"));
		 line = line.replaceAll("but", ".");
		 for(String ss: s) {
			 System.out.println(ss);
		 }
            int jsonval = 0;
	        int mainSentiment=0;
	        JSONArray arr = new JSONArray();
	        
	        
	        if(line != null && line.length()>0){
	            int longest = 0;
	            //System.out.println(line);
		  	      Annotation annotation = new Annotation(line);

	           // Annotation annotation = pipeline.process(line);
		  	      pipeline.annotate(annotation);
		  	    List<CoreMap> sentences = annotation.get(SentencesAnnotation.class);
		  	    
System.out.println(sentences);
	            for (CoreMap sentence : sentences ) {
	    	        JSONObject obj = new JSONObject();
	            	//System.out.println(sentence);
	            	System.out.println("================");
	                Tree tree =	sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
	                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
	                //System.out.println(sentiment);
	                obj.put("Sentence", sentence);
	                obj.put("Sentiment", sentiment);
	              //  arr.add(obj);
	                arr.add(jsonval, obj);
jsonval=jsonval+1;
	                String partText = sentence.toString();
	           

	                //System.out.println(partText);
	                if (partText.length() > longest) {
	                    mainSentiment = sentiment;
	                    //System.out.println(+sentiment+" "+partText.length());
	                    longest = partText.length();
	                    //if(mainSentiment<2) {break;}
	                }
	                //System.out.println(arr);
	                //arr.add(obj);
	                //obj.put("Sentiments", arr);
	            }
	            //System.out.println(arr);
	        }

	    /*    switch (mainSentiment) {
	        case 0:
	            return "Very Negative";
	        case 1:
	            return "Negative";
	        case 2:
	            return "Neutral";
	        case 3:
	            return "Positive";
	        case 4:
	            return "Very Positive";
	        default:
	            return "";
	        }*/
	       return  arr;
	    }
	 
	 public static int findSentimentInt(String line) {

	       
	        int mainSentiment=0;

	        if(line != null && line.length()>0){
	            int longest = 0;
	            //System.out.println(line);
	            Annotation annotation = pipeline.process(line);
	            for (CoreMap sentence :annotation.get(CoreAnnotations.SentencesAnnotation.class )) {
	                Tree tree =	sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
	                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
	                String partText = sentence.toString();
	                if (partText.length() > longest) {
	                    mainSentiment = sentiment;
	                    //System.out.println(+sentiment+" "+partText.length());
	                    longest = partText.length();
	                }
	            }
	        }
	        return (mainSentiment);
	        }

}





