package edu.umich.scil;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLPWrapper
{
	StanfordCoreNLP pipeline;
	
	public CoreNLPWrapper()
	{
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public Annotation annotateText(String text)
	{
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);
		return doc;
	}
	
	public static IndexedWord getIndexedWord(Annotation anno, String word)
	{
		CoreMap sentence = anno.get(SentencesAnnotation.class).get(0);
		for(CoreLabel token : sentence.get(TokensAnnotation.class))
		{
			if(token.get(TextAnnotation.class).equals(word))
			{
				return new IndexedWord(token);
			}
		}
		return null;
	}
}
