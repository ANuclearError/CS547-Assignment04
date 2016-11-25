package com.aidanogrady.cs547.assignment04;

import com.aidanogrady.cs547.assignment04.data.DataParser;
import com.aidanogrady.cs547.assignment04.data.DataRecord;
import com.aidanogrady.cs547.assignment04.data.DataSet;
import com.aidanogrady.cs547.assignment04.jgap.CostProblem;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

        String fileName = "datasets/albrecht.arff";
        List<String> attributes = new ArrayList<>();
        attributes.add("AdjFP");

        DataSet dataSet = DataParser.parseFile(fileName, attributes);

        if (dataSet != null) {
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
            double percent = 0.1;
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
                if (diff / effort < percent) {
                    System.out.println("\tCLOSE");
                    close++;
                } else {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("CLOSE = within " + percent * 100 + "% of actual");
            System.out.println("Total: " + dataSet.getRecords().size());
            System.out.println("Close: " + close);
            System.out.println("Percent: " + (close / dataSet.getRecords().size()));
        }
    }
}
