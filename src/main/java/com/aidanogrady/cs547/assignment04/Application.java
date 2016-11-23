package com.aidanogrady.cs547.assignment04;

import com.aidanogrady.cs547.assignment04.data.DataParser;
import com.aidanogrady.cs547.assignment04.data.DataSet;
import com.aidanogrady.cs547.assignment04.jgap.CostProblem;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.GPGenotype;

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
        attributes.add("SLOC");

        DataSet dataSet = DataParser.parseFile(fileName, attributes);
        GPProblem problem = new CostProblem(dataSet);
        GPGenotype gp = problem.create();
        gp.setVerboseOutput(true);
        gp.evolve(500);
        System.out.println("Solving " + dataSet.getName() + " dataset");
        gp.outputSolution(gp.getAllTimeBest());
    }
}
