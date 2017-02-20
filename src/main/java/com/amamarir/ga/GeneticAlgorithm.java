/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2017 Amine Amarir
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 ******************************************************************************/
package com.amamarir.ga;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author amamarir
 * 
 * Genetic Algorithm Class
 */
public class GeneticAlgorithm<C extends Chromosome<C>, T extends Comparable<T>> {

	private Population<C> population;

	private final FitnessFunction<C, T> fitnessFunc;

	/**
	 * Chromosome Comparator
	 */
	private final Comparator<C> chromosomeComparator = new Comparator<C>() {

		@Override
		public int compare(C chr1, C chr2) {
			T fit1 = GeneticAlgorithm.this.fit(chr1);
			T fit2 = GeneticAlgorithm.this.fit(chr2);
			int res = fit1.compareTo(fit2);
			return res;
		}

	};

	/**
	 * Constructor
	 * 
	 * @param population
	 * @param fitnessfunction
	 */
	public GeneticAlgorithm(Population<C> population, FitnessFunction<C, T> fitnessFunc) {
		this.fitnessFunc = fitnessFunc;
		this.population = population;
	}

	/**
	 * evolve
	 */
	private void evolve() {

		Population<C> newPopulation = this.population.clone();

		for (int i = 0; i < this.population.getSize() / 2; i++) {
			C ch1 = this.population.getRandomChromosome();
			this.population.removeChromosome(ch1);
			C ch2 = this.population.getRandomChromosome();
			this.population.removeChromosome(ch1);

			List<C> crossovered = ch1.crossover(ch2);

			for (C c : crossovered) {
				if (new Random().nextDouble() < Constants.MUTATION_RATE) {
					newPopulation.addChromosome(c.mutate());
				} else {
					newPopulation.addChromosome(c);
				}
			}
		}

		newPopulation.sortPopulationByFitness(this.chromosomeComparator);
		newPopulation.trim(Constants.DEFAULT_NUMBER_OF_CHROMOSOMES);
		
		this.population = newPopulation;
	}

	/**
	 * evolve for a number of iterations
	 * 
	 * @param int nb
	 */
	public void evolve(int count) {
		System.out.println("iter\tfitness\tchromosome");
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (int i = 0; i < count; i++) {
			evolve();
			C ch = this.getBest();
			T fit = this.fitnessValue(ch);
			System.out.println(String.format("%s\t%s\t%s", i, formatter.format(fit), ch));
		}
	}

	/**
	 * Get fitness value for a specific chromosome
	 * 
	 * @param C Chromosome
	 * @return T fitness
	 */
	public T fitnessValue(C chromosome) {
		return this.fit(chromosome);
	}

	/**
	 * fitness
	 * 
	 * @param C Chromosome
	 * @return T fit
	 */
	private T fit(C chr) {
		T fit = GeneticAlgorithm.this.fitnessFunc.calculate(chr);
		return fit;
	}
	
	/**
	 * Best chromosome in the population
	 * 
	 * @return C
	 */
	public C getBest() {
		return this.population.getChromosomeByIndex(0);
	}

	/**
	 * Worst chromosome in the population
	 * 
	 * @return C
	 */
	public C getWorst() {
		return this.population.getChromosomeByIndex(this.population.getSize() - 1);
	}
}
