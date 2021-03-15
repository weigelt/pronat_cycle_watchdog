package edu.kit.ipd.pronat.cycle_watchdog;

import edu.kit.ipd.pronat.cycle_watchdog.testagents.NodeAddAgent;
import edu.kit.ipd.pronat.cycle_watchdog.testagents.NodeRemoveAgent;
import edu.kit.ipd.parse.luna.ILuna;
import edu.kit.ipd.parse.luna.Luna;
import edu.kit.ipd.parse.luna.event.AbortEvent;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import org.junit.After;
import org.junit.Before;
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

	private CycleWatchdog cwd;

	@Before
	public void beforeTest() {
		cwd = new CycleWatchdog();
	}

	@After
	public void tearDown() {
		Luna.tearDown();
	}

	@Test
	public void tenRunsNoCycle() {

		ILuna luna = Luna.getInstance();
		wdProps.setProperty("MONITORED_GRAPHS", "10");
		luna.register(cwd);
		cwd.initAbstract(luna);
		NodeAddAgent naa = new NodeAddAgent();
		luna.register(naa);
		naa.initAbstract(luna);
		for (int i = 0; i < 10; i++) {
			naa.setGraph(luna.getCloneOfMainGraph());
			naa.exec();
			luna.updateGraph(naa.getGraph());
			cwd.setGraph(luna.getMainGraph());
			cwd.exec();
		}
		assertFalse(cwd.getCurrEvent() instanceof AbortEvent);
		assertFalse(naa.getCurrEvent() instanceof AbortEvent);
	}

	@Test
	public void instantCycle() {

		ILuna luna = Luna.getInstance();
		wdProps.setProperty("MONITORED_GRAPHS", "3");
		luna.register(cwd);
		cwd.initAbstract(luna);
		NodeAddAgent naa = new NodeAddAgent();
		luna.register(naa);
		naa.initAbstract(luna);
		NodeRemoveAgent nra = new NodeRemoveAgent();
		luna.register(nra);
		nra.initAbstract(luna);

		naa.setGraph(luna.getCloneOfMainGraph());
		naa.exec();
		luna.updateGraph(naa.getGraph());
		cwd.setGraph(luna.getMainGraph());
		cwd.exec();
		nra.setGraph(luna.getCloneOfMainGraph());
		nra.exec();
		luna.updateGraph(nra.getGraph());
		cwd.setGraph(luna.getMainGraph());
		cwd.exec();
		naa.setGraph(luna.getCloneOfMainGraph());
		naa.exec();
		luna.updateGraph(naa.getGraph());
		cwd.setGraph(luna.getMainGraph());
		cwd.exec();

		assertTrue(cwd.getCurrEvent() instanceof AbortEvent);
		assertTrue(naa.getCurrEvent() instanceof AbortEvent);
		assertTrue(nra.getCurrEvent() instanceof AbortEvent);
	}

	@Test
	public void fiveRunsNoCycleThenCycle() {

		ILuna luna = Luna.getInstance();
		wdProps.setProperty("MONITORED_GRAPHS", "7");
		luna.register(cwd);
		cwd.initAbstract(luna);
		NodeAddAgent naa = new NodeAddAgent();
		luna.register(naa);
		naa.initAbstract(luna);
		NodeRemoveAgent nra = new NodeRemoveAgent();
		luna.register(nra);
		nra.initAbstract(luna);
		for (int i = 0; i < 5; i++) {
			naa.setGraph(luna.getCloneOfMainGraph());
			naa.exec();
			luna.updateGraph(naa.getGraph());
			cwd.setGraph(luna.getMainGraph());
			cwd.exec();
		}
		assertFalse(cwd.getCurrEvent() instanceof AbortEvent);
		assertFalse(naa.getCurrEvent() instanceof AbortEvent);
		assertFalse(nra.getCurrEvent() instanceof AbortEvent);

		nra.setGraph(luna.getCloneOfMainGraph());
		nra.exec();
		luna.updateGraph(nra.getGraph());
		cwd.setGraph(luna.getMainGraph());
		cwd.exec();

		assertTrue(cwd.getCurrEvent() instanceof AbortEvent);
		assertTrue(naa.getCurrEvent() instanceof AbortEvent);
		assertTrue(nra.getCurrEvent() instanceof AbortEvent);

	}
}
