package com.aidanogrady.cs547.assignment04.jgap;

import com.aidanogrady.cs547.assignment04.data.DataRecord;
import com.aidanogrady.cs547.assignment04.data.DataSet;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

import java.util.List;

/**
 * The fitness fucntion outlines how well the genetic program performs by
 * comparing the effort calculated by genetic program and the effort defiend in
 * file.
 *
 * @author Aidan O'Grady
 * @since 0.2
 */
public class CostFitnessFunction extends GPFitnessFunction {
    /**
     * NO_ARGS ensures that there are no other arguments given to gp.
     */
    private static Object[] NO_ARGS = new Object[0];

    /**
     * The dataset to be solved.
     */
    private DataSet dataSet;

    /**
     * The variables to be used in GP.
     */
    private List<Variable> variables;

    /**
     * Constructs an ew cost fitness function with the given dataset and
     * variables.
     *
     * @param dataSet the dataset to use for evaluation
     * @param variables the variables to use in gp
     */
    public CostFitnessFunction(DataSet dataSet, List<Variable> variables) {
        this.dataSet = dataSet;
        this.variables = variables;
    }

    @Override
    protected double evaluate(IGPProgram gp) {
        double result = 0;
        for (DataRecord record : dataSet.getRecords()) {
            for (Variable var : variables) {
                var.set(record.get(var.getName()));
            }
            double res = gp.execute_double(0, NO_ARGS);
            result += (Math.abs(res - record.getEffort()) / record.getEffort());
        }
        return result / dataSet.getRecords().size();
    }
}
