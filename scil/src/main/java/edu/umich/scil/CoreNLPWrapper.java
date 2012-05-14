package edu.umich.scil;

import java.util.Properties;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

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
}
