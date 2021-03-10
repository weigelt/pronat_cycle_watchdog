package edu.kit.ipd.parse.cycleWD.testagents;

import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.graph.INodeType;

public class NodeRemoveAgent extends AbstractAgent {
	@Override
	public void exec() {
		INodeType testType = graph.getNodeType("test");
		if (testType != null && graph.getNodesOfType(testType).size() > 0) {
			INode lastNode = graph.getNodesOfType(testType).get(graph.getNodesOfType(testType).size() - 1);
			graph.deleteNode(lastNode);
		}
	}

	@Override
	public void init() {

	}
}
