package edu.kit.ipd.parse.cycleWD;

import edu.kit.ipd.parse.luna.Luna;
import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.ParseGraph;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Sebastian Weigelt
 * 
 */
public class CycleWatchdogTest {

	private static final Properties wdProps = ConfigManager.getConfiguration(CycleWatchdog.class);
	private static final Properties lunaProps = ConfigManager.getConfiguration(Luna.class);

	private IGraph graph;
	private CycleWatchdog cwd;

	@BeforeClass
	public static void SetUp() {
		lunaProps.setProperty("TERM_SIGNAL_TYPE", "terminate");
	}

	@Before
	public void beforeTest() {
		graph = new ParseGraph();
		cwd = new CycleWatchdog();
		cwd.init();
	}
}
