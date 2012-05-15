package edu.umich.scil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;

public class FeatureSelector
{
	Annotation anno;
	
	public FeatureSelector(Annotation anno)
	{
		this.anno = anno;
	}
	
	public Instance getFeatures(int opinionWordIndex, int targetWordIndex)
	{
		Instance features = new SparseInstance();
		
		features.putAll(getDistanceFeature(opinionWordIndex, targetWordIndex));
		features.putAll(getDependencyFeatures(opinionWordIndex, targetWordIndex));
		features.putAll(getSyntacticFeatures(opinionWordIndex, targetWordIndex));
		
		return features;
	}

	public Map<Integer, Double> getDistanceFeature(int opinionWordIndex, int targetWordIndex)
	{
		Map<Integer, Double> features = new HashMap<Integer, Double>();
		//CoreMap s = sentence.get(SentencesAnnotation.class).get(0); // there should only be one sentence
		features.put("gen_distance".hashCode(), (double)targetWordIndex - opinionWordIndex);
			
		return features;
	}
	
	public Map<Integer, Double> getDependencyFeatures(int opinionWordIndex, int targetWordIndex)
	{
		Map<Integer, Double> features = new HashMap<Integer, Double>();
		CoreMap sentence = anno.get(SentencesAnnotation.class).get(0);
		SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
		
		IndexedWord opinionWord = dependencies.getNodeByIndex(opinionWordIndex);
		IndexedWord targetWord = dependencies.getNodeByIndex(targetWordIndex);
		
		int commonAncestorDist = dependencies.commonAncestor(opinionWord, targetWord);
		features.put("dep_commonAncestorDistance".hashCode(), new Double(commonAncestorDist));
		
		List<IndexedWord> directedPathNodes = dependencies.getShortestDirectedPathNodes(opinionWord, targetWord);
		List<SemanticGraphEdge> directedPathEdges = dependencies.getShortestDirectedPathEdges(opinionWord, targetWord);
		List<IndexedWord> undirectedPathNodes = dependencies.getShortestUndirectedPathNodes(opinionWord, targetWord);
		List<SemanticGraphEdge> undirectedPathEdges = dependencies.getShortestUndirectedPathEdges(opinionWord, targetWord);
		
		String directedNodePath = "dep_dir_nodes";
		String undirectedNodePath = "dep_und_nodes";
		
		for(IndexedWord w : directedPathNodes)
		{
			String pos = w.get(PartOfSpeechAnnotation.class);
			directedNodePath += "_" + pos;
			features.put(("dep_dir_node_" + pos).hashCode(), 1.0);
		}
		
		for(IndexedWord w : undirectedPathNodes)
		{
			String pos = w.get(PartOfSpeechAnnotation.class);
			undirectedNodePath += "_" + pos;
			features.put(("dep_und_node_" + pos).hashCode(), 1.0);
		}
		
		features.put(directedNodePath.hashCode(), 1.0);
		features.put(undirectedNodePath.hashCode(), 1.0);
		
		String directedEdgePath = "dep_dir_edges";
		String undirectedEdgePath = "dep_und_edges";
		
		for(SemanticGraphEdge s : directedPathEdges)
		{
			String label = s.getRelation().toString();
			directedEdgePath += "_" + label;
			features.put(("dep_dir_edge_" + label).hashCode(), 1.0);
		}
		
		for(SemanticGraphEdge s : undirectedPathEdges)
		{
			String label = s.getRelation().toString();
			undirectedEdgePath += "_" + label;
			features.put(("dep_und_edge_" + label).hashCode(), 1.0);
		}
		
		features.put(directedEdgePath.hashCode(), 1.0);
		features.put(undirectedEdgePath.hashCode(), 1.0);
		
		return features;
	}
	
	public Map<Integer, Double> getSyntacticFeatures(int opinionWordIndex, int targetWordIndex)
	{
		Map<Integer, Double> features = new HashMap<Integer, Double>();
		
		return features;
	}
}
