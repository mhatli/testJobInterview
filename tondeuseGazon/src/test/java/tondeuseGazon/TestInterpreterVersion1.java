package tondeuseGazon;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.InterpreterLuncher;
import com.app.program.InterpreterI;
import com.app.shared.PositionOriented;
import com.app.shared.VersionOfInterface;

class TestInterpreterVersion1 {

	@BeforeEach
	void setUp() throws Exception {
		BasicConfigurator.configure();
//
//		Appender console = new ConsoleAppender();
//		console.setLayout(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
//		Logger root = Logger.getRootLogger();
//		root.addAppender(console);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() throws Exception {

		InterpreterI<?> interpreter = InterpreterLuncher.get(VersionOfInterface.V1,
				"src/test/resources/testFile/testScenario1.txt");
		assertNotNull(interpreter, "program has not initialized correctly!");
		Optional<?> result = interpreter.call();
		assertNotNull(result.isPresent(), "the program has not output");
		assertThat(result.get(), instanceOf(List.class));

		assertEquals(2, ((List) result.get()).size(), "Unexpected program output");
		assertThat(((List) result.get()).get(0), instanceOf(PositionOriented.class));
		Iterator<PositionOriented> iterator = ((List<PositionOriented>) result.get()).iterator();

		assertEquals("1 3 N", iterator.next().toString(), "Unexpected single result output");
		assertEquals("5 1 E", iterator.next().toString(), "Unexpected single result output");

	}

}
