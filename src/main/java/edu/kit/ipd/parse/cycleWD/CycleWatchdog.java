package edu.kit.ipd.parse.cycleWD;

import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.agent.AbstractWatchdog;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Sebastian Weigelt
 *
 */

@MetaInfServices(AbstractAgent.class)
public class CycleWatchdog extends AbstractWatchdog {

	private static final String ID = "cycleWatchdog";
	private static final Properties wdProps = ConfigManager.getConfiguration(CycleWatchdog.class);
	private static final Logger logger = LoggerFactory.getLogger(CycleWatchdog.class);

	private static final String PROP_MONITORED_GRAPHS = "MONITORED_GRAPHS";

	private CircularFifoQueue<Long> graph_queue;

	private long lastChangeCounter = -1;

	public CycleWatchdog() {
		setId(ID);
	}

	/**
	 * 
	 */
	@Override
	public void init() {
		graph_queue = new CircularFifoQueue<>(Integer.parseInt(wdProps.getProperty(PROP_MONITORED_GRAPHS)));
	}

	/**
	 * 
	 */
	@Override
	protected void exec() {

		if (lastChangeCounter != graph.getChangeCounter()) {
			lastChangeCounter = graph.getChangeCounter();
			long currHash = graph.hashCode();
			if (graph_queue.contains(currHash)) {
				logger.info("Creating term signal. Detected a cycle: Hash {} was the same for (at least) two of the last {} graphs!",
						currHash, graph_queue.size());
				terminate();
			} else {
				logger.debug("Adding a new hash: {}", currHash);
				graph_queue.add(currHash);
			}
		} else {
			logger.debug("Change counter unchanged: {}", lastChangeCounter);
		}
	}
}