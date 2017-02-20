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
package com.amamarir;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.amamarir.ga.Chromosome;
import com.amamarir.ga.Constants;
import com.amamarir.ga.FitnessFunction;
import com.amamarir.ga.GeneticAlgorithm;
import com.amamarir.ga.Population;

/**
 * @author amamarir
 *
 * Demo Class
 */
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Population<MyChromosome> population = createInitialPopulation();
		GeneticAlgorithm<MyChromosome, Double> ga = new GeneticAlgorithm<>(population, new FitnessFunction<MyChromosome, Double>() {

			@Override
			public Double calculate(MyChromosome chromosome) {
				int[] fit = {120,82,85,77,95,81,87,98,97};
				Double valfit = 0.0;
				for(int i = 0; i < chromosome.length; i++){
					valfit += fit[Integer.parseInt("" + chromosome.value.charAt(i))];
				}
				valfit /= chromosome.length;
				return valfit;
			}
		});
		
		ga.evolve(Constants.DEFAULT_NUMBER_OF_ITERATIONS);
	}

	/**
	 * @return
	 */
	private static Population<MyChromosome> createInitialPopulation() {
		Population<MyChromosome> population = new Population<>();
		int length = 9;
		for(int i = 0; i < Constants.DEFAULT_NUMBER_OF_CHROMOSOMES; i++){
			population.addChromosome(new MyChromosome(length));
		}
		return population;
	}

	private static class MyChromosome implements Chromosome<MyChromosome> {

		private String value = "";

		private final int length;

		/**
		 * 
		 */
		public MyChromosome(int length) {
			this.length = length;
			for (int i = 0; i < length; i++) {
				value += getRandomVal();
			}
		}

		/**
		 * 
		 */
		public MyChromosome(String value) {
			this.length = value.length();
			this.value = new String(value);
		}

		@Override
		public List<MyChromosome> crossover(MyChromosome anotherChromosome) {

			List<MyChromosome> list = new ArrayList<>();
			String v1 = "";
			String v2 = "";
			for (int i = 0; i < length; i++) {
				if (this.value.charAt(i) == anotherChromosome.value.charAt(i)) {
					v1 += this.value.charAt(i);
					v2 += this.value.charAt(i);
				} else {
					if (new Random().nextBoolean()) {
						v1 += this.value.charAt(i);
						v2 += anotherChromosome.value.charAt(i);
					} else {
						v2 += this.value.charAt(i);
						v1 += anotherChromosome.value.charAt(i);
					}
				}
			}
			list.add(new MyChromosome(v1));
			list.add(new MyChromosome(v2));
			return list;
		}

		@Override
		public MyChromosome mutate() {
			int indx = new Random().nextInt(length);
			String newVal = value.substring(0, indx) + new Random().nextInt(9) + value.substring(indx + 1);
			return new MyChromosome(newVal);
		}

		private int getRandomVal() {
			return new Random().nextInt(this.length);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return value.toString();
		}

	}

}
