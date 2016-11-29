package com.aidanogrady.cs547.assignment04;

import com.aidanogrady.cs547.assignment04.data.DataParser;
import com.aidanogrady.cs547.assignment04.data.DataRecord;
import com.aidanogrady.cs547.assignment04.data.DataSet;
import com.aidanogrady.cs547.assignment04.jgap.CostProblem;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Main entry point.
 *
 * @author Aidan O'Grady
 * @since 0.0
 */
public class Application {
    public static void main(String[] args) throws Exception {
        System.out.println("CS547 Assignment 04: Software Cost Estimation");
        System.out.println("Author: Aidan O'Grady (201218150)\n");

        if (args.length < 2) {
            System.out.println("USAGE: <file> <attributes>...");
        } else {
            String fileName = args[0];
            String[] attr = Arrays.copyOfRange(args, 1, args.length);
            List<String> attributes = Arrays.asList(attr);
            DataSet dataSet = DataParser.parseFile(fileName, attributes);
            if (dataSet != null)
                run(dataSet);
        }
    }

    private static void run(DataSet dataSet) throws Exception {
        GPProblem problem = new CostProblem(dataSet);
        GPGenotype gp = problem.create();
        gp.setVerboseOutput(true);
        gp.evolve(500);
        System.out.println();
        System.out.println("Solving " + dataSet.getName() + " dataset");
        gp.outputSolution(gp.getAllTimeBest());
        System.out.println();

        IGPProgram best = gp.getAllTimeBest();

        double mmre = 0.0;
        double[] percent = {0.5, 0.25, 0.1};
        double[] pred = {0, 0, 0};

        for (int i = 1; i <= dataSet.getRecords().size(); i++) {
            DataRecord record = dataSet.getRecord(i - 1);
            for (int j = 0; j < dataSet.getAttributes().size(); j++) {
                String v = dataSet.getAttribute(j);
                ((Variable) best.getNodeSets()[0][j]).set(record.get(v));
            }

            double val = best.execute_double(0, new Object[] {});
            double effort = record.getEffort();

            System.out.print(i + ".\t");
            System.out.format("Estimated %03.3f\tActual %03.2f", val, effort);
            double diff = Math.abs(val - effort);
            mmre += (diff / effort);

            String p = "";
            for (int j = 0; j < percent.length; j++) {
                if (diff / effort < percent[j]) {
                    p = String.format("\t within %.0f%%", (percent[j] * 100));
                    pred[j]++;
                }
            }
            System.out.println(p);
        }

        int size = dataSet.getRecords().size();
        mmre /= size;
        System.out.println();
        System.out.format("MMRE: %.3f\n", (mmre));
        for (int i = 0; i < pred.length; i++) {
            percent[i] *= 100;
            System.out.format("PRED(%.0f): %.0f / %d (%.3f)\n",
                    percent[i],
                    pred[i],
                    size,
                    pred[i] / size);
        }
    }
}
