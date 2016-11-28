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
import java.util.ArrayList;
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
        gp.evolve(1000);
        System.out.println();
        System.out.println("Solving " + dataSet.getName() + " dataset");
        gp.outputSolution(gp.getAllTimeBest());
        System.out.println();

        IGPProgram best = gp.getAllTimeBest();

        double close = 0;
        double mmre = 0.0;
        double percent = 0.25;
        DecimalFormat df = new DecimalFormat("#.00");

        for (int i = 1; i <= dataSet.getRecords().size(); i++) {
            DataRecord record = dataSet.getRecord(i - 1);
            for (int j = 0; j < dataSet.getAttributes().size(); j++) {
                String v = dataSet.getAttribute(j);
                ((Variable) best.getNodeSets()[0][j]).set(record.get(v));
            }

            double val = best.execute_double(0, new Object[] {});
            double effort = record.getEffort();

            System.out.print(i + ".\t");
            System.out.print("Cost " + df.format(val) + "\tActual " + effort);
            double diff = Math.abs(val - effort);
            mmre += (diff / effort);
            if (diff / effort < percent) {
                System.out.println("\tCLOSE");
                close++;
            } else {
                System.out.println();
            }
        }

        int size = dataSet.getRecords().size();
        double pred = close / size;
        mmre /= size;
        System.out.println();
        System.out.println("Total: " + dataSet.getRecords().size());
        System.out.format("PRED(%.0f): %.2f\n", (percent * 100), (pred * 100));
        System.out.format("MMRE: %.3f\n", (mmre));
    }
}
