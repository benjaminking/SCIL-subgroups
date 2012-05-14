package edu.umich.scil;

import static org.junit.Assert.assertEquals;
import net.sf.javaml.core.Instance;

import org.junit.Test;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class FeatureSelectorTest
{
	CoreNLPWrapper wrapper = new CoreNLPWrapper();
	
	@Test
	public void testWordDistance()
	{
		String text = "He hates the government";
		Annotation anno = wrapper.annotateText(text);
		FeatureSelector fSelect = new FeatureSelector(anno);
		
		Instance i = fSelect.getFeatures(1, 3);
		assertEquals(new Double(2.0), i.get("distance".hashCode()));
	}
	
}
