package com.aidanogrady.cs547.assignment04.jgap;

import com.aidanogrady.cs547.assignment04.data.DataSet;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * The Problem class for cost estimation.
 *
 * @author Aidan O'Grady
 * @since 0.2
 */
public class CostProblem extends GPProblem {
    /**
     * The dataset to be solved.
     */
    private DataSet dataSet;

    /**
     * The variables to be used in GP.
     */
    private List<Variable> variables;

    public CostProblem(DataSet dataSet) throws InvalidConfigurationException {
        super(new GPConfiguration());
        GPConfiguration config = getGPConfiguration();

        this.dataSet = dataSet;
        this.variables = new ArrayList<>();
        for (String at : dataSet.getAttributes()) {
            variables.add(Variable.create(config, at, CommandGene.DoubleClass));
        }

        config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        config.setMaxInitDepth(10);
        config.setPopulationSize(5000);
        config.setMaxCrossoverDepth(20);
        config.setFitnessFunction(new CostFitnessFunction(dataSet, variables));
        config.setStrictProgramCreation(true);
    }

    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration config = getGPConfiguration();
        Class[] types = {CommandGene.DoubleClass};
        Class[][] argTypes = {{}};

        CommandGene[] varSet = new CommandGene[variables.size()];
        variables.toArray(varSet);
        CommandGene[] functSet = {
                new Add(config, CommandGene.DoubleClass),
                new Subtract(config, CommandGene.DoubleClass),
                new Multiply(config, CommandGene.DoubleClass),
                new Divide(config, CommandGene.DoubleClass),
                new Pow(config, CommandGene.DoubleClass),
                new Log(config, CommandGene.DoubleClass),
                new Exp(config, CommandGene.DoubleClass),
                new Terminal(config, CommandGene.DoubleClass, 0, 100, false)
        };

        int size = varSet.length + functSet.length;
        CommandGene[] nodeSet = new CommandGene[size];
        System.arraycopy(varSet, 0, nodeSet, 0, varSet.length);
        System.arraycopy(functSet, 0, nodeSet, varSet.length, functSet.length);
        CommandGene[][] nodeSets = {
            nodeSet
        };

        return GPGenotype.randomInitialGenotype(
                config, types, argTypes, nodeSets, 20, true
        );
    }
}
