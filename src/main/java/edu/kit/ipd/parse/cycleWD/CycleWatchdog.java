package edu.kit.ipd.parse.cycleWD;

import edu.kit.ipd.parse.luna.AbstractLuna;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.agent.AbstractWatchdog;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
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
	private static final Logger logger = LoggerFactory.getLogger(CycleWatchdog.class);
	private static final Properties wdProps = ConfigManager.getConfiguration(CycleWatchdog.class);
	private static final Properties lunaProps = ConfigManager.getConfiguration(AbstractLuna.class);

	private static final String PROP_TERM_SIGNAL_TYPE = "TERM_SIGNAL_TYPE";

	private String termSignalType;

	public CycleWatchdog() {
		setId(ID);
	}

	/**
	 * 
	 */
	@Override
	public void init() {
		termSignalType = lunaProps.getProperty(PROP_TERM_SIGNAL_TYPE);
	}

	/**
	 * 
	 */
	@Override
	protected void exec() {

	}
}
