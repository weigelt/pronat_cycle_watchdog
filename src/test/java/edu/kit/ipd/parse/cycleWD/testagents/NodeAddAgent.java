package edu.kit.ipd.parse.cycleWD.testagents;

import edu.kit.ipd.parse.luna.agent.AbstractAgent;

public class NodeAddAgent extends AbstractAgent {
	@Override
	public void exec() {
		if (!graph.hasNodeType("test")) {
			graph.createNodeType("test");
		}
		graph.createNode(graph.getNodeType("test"));
	}

	@Override
	public void init() {

	}
}
