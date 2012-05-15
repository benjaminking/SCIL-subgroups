package edu.umich.scil;

import static org.junit.Assert.assertEquals;
import net.sf.javaml.core.Instance;

import org.junit.Test;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
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
		
		Instance i = fSelect.getFeatures(2, 4);
		assertEquals(new Double(2.0), i.get("gen_distance".hashCode()));
	}
	
	@Test
	public void testDependencyFeatures()
	{
		String text = "He hates the government";
		Annotation anno = wrapper.annotateText(text);
		FeatureSelector fSelect = new FeatureSelector(anno);
		
		Instance i = fSelect.getFeatures(2, 4);
		assertEquals(new Double(-1.0), i.get("dep_commonAncestorDistance".hashCode()));
		assertEquals(new Double(1.0), i.get("dep_und_node_VBZ".hashCode()));
		assertEquals(new Double(1.0), i.get("dep_und_nodes_VBZ_NN".hashCode()));
		assertEquals(new Double(1.0), i.get("dep_und_edge_dobj".hashCode()));
		assertEquals(new Double(1.0), i.get("dep_und_edges_dobj".hashCode()));
	}
}
