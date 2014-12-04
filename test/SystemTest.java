import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.Test;

public class SystemTest {

	@Test
	public void testAnalyzeTimeConsumption100TestCases() {

		IndataReader ir = new IndataReader();
		TSPsolver solver = new TSPsolver();
		Random rand = new Random();
		// EdgeDistanceComparator edgeDistanceComparator = new EdgeDistanceComparator();
		// ClarkeWright cw = new ClarkeWright(rand, edgeDistanceComparator);
		NearestNeighbour nn = new NearestNeighbour(rand);
		long startTimeTotal = 0;
		long elapsedTimeTotal;
		long startTimeRead = 0;
		long elapsedTimeRead;
		long startTimeSolve = 0;
		long elapsedTimeSolve;
		TestDataGenerator generator = new TestDataGenerator(1000);
		String worstIndataRead = " ";
		String worstIndataSolve = " ";
		long worstTotalTime = 0;
		long worstReadTime = 0;
		long worstSolveTime = 0;
		long sumRuntime = 0;
		double avverageRunTime;
		int nTests = 10000;
		for (int i = 0; i < nTests; i++) {
			generator = new TestDataGenerator(rand.nextInt(1000) + 1);
			String indata = generator.getTestCase();
			InputStream indataStream = new ByteArrayInputStream(indata.getBytes(StandardCharsets.UTF_8));
			// Kattio io = new Kattio(indataStream);
			startTimeTotal = System.currentTimeMillis();

			startTimeRead = System.currentTimeMillis();
			double[][] coordinates = ir.read();
			elapsedTimeRead = System.currentTimeMillis() - startTimeRead;
			System.err.println("Time to calculate graphData " + elapsedTimeRead);
			startTimeSolve = System.currentTimeMillis();
			// solver.solve(graphData, cw);
			TwoOpt opt = new TwoOpt();
			solver.solveLight(coordinates, nn, opt);
			elapsedTimeSolve = System.currentTimeMillis() - startTimeSolve;
			System.err.println("Time to calculate solution " + elapsedTimeSolve);
			elapsedTimeTotal = System.currentTimeMillis() - startTimeTotal;
			System.err.println("Time to run program  " + elapsedTimeTotal);

			if (elapsedTimeRead > worstReadTime) {
				worstReadTime = elapsedTimeRead;
				worstIndataRead = indata;
			}
			if (elapsedTimeSolve > worstSolveTime) {
				worstSolveTime = elapsedTimeSolve;
				worstIndataSolve = indata;
			}
			if (elapsedTimeTotal > worstTotalTime) {
				worstTotalTime = elapsedTimeTotal;
			}
			sumRuntime = sumRuntime + elapsedTimeTotal;
		}
		avverageRunTime = (double) sumRuntime / (double) nTests;
		System.err.println("max time to do read" + worstReadTime);
		System.err.println("max time to do solve" + worstSolveTime);
		System.err.println("max time to run program in total " + worstTotalTime);
		System.err.println("average runtime was " + avverageRunTime);
		System.out.println("The worst indata to read was");
		System.out.println(worstIndataRead);

		System.out.println("The worst indata to solve was");
		System.out.println(worstIndataSolve);

	}
}
