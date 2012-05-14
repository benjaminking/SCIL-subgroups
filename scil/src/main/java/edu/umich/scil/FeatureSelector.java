package edu.umich.scil;

import java.util.List;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class FeatureSelector
{
	Annotation sentence;
	
	public FeatureSelector(Annotation sentence)
	{
		this.sentence = sentence;
	}
	
	public Instance getFeatures(int opinionWordTokenOffset, int targetWordTokenOffset)
	{
		Instance features = new SparseInstance();
		
		features.add(getDistanceFeature(opinionWordTokenOffset, targetWordTokenOffset));
		
		return features;
	}

	public Instance getDistanceFeature(int opinionWordTokenOffset, int targetWordTokenOffset)
	{
		Instance features = new SparseInstance();
		//CoreMap s = sentence.get(SentencesAnnotation.class).get(0); // there should only be one sentence
		features.put("distance".hashCode(), (double)targetWordTokenOffset - opinionWordTokenOffset);
		
		return features;
	}
}
