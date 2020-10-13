package edu.kit.ipd.parse.cycleWD;

import edu.kit.ipd.parse.luna.AbstractLuna;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.agent.AbstractWatchdog;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.kohsuke.MetaInfServices;

import java.util.Properties;

/**
 * @author Sebastian Weigelt
 *
 */

@MetaInfServices(AbstractAgent.class)
public class CycleWatchdog extends AbstractWatchdog {

	private static final String ID = "cycleWatchdog";
	private static final Properties wdProps = ConfigManager.getConfiguration(CycleWatchdog.class);
	private static final Properties lunaProps = ConfigManager.getConfiguration(AbstractLuna.class);

	private static final String PROP_TERM_SIGNAL_TYPE = "TERM_SIGNAL_TYPE";
	private static final String PROP_MONITORED_GRAPHS = "MONITORED_GRAPHS";

	private String termSignalType;
	private CircularFifoQueue<Long> graph_queue;

	public CycleWatchdog() {
		setId(ID);
	}

	/**
	 * 
	 */
	@Override
	public void init() {
		termSignalType = lunaProps.getProperty(PROP_TERM_SIGNAL_TYPE);
		graph_queue = new CircularFifoQueue<>(Integer.parseInt(wdProps.getProperty(PROP_MONITORED_GRAPHS)));
	}

	/**
	 * 
	 */
	@Override
	protected void exec() {
		long currHash = graph.hashCode();
		if (graph_queue.contains(currHash)) {
			graph.createNodeType(termSignalType);
		} else {
			graph_queue.add(currHash);
		}
	}
}