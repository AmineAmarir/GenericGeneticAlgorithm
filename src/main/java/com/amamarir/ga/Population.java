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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author amamarir
 *
 * Population Class
 */
public class Population<C extends Chromosome<C>> {

	/**
	 * Chromosomes list
	 */
	private List<C> chromosomes;

	/**
	 * Population Constructor
	 */
	public Population() {
		chromosomes = new ArrayList<C>();
	}

	/**
	 * add chromosome if it doesn't exist in the population
	 * 
	 * @param C Chromosome
	 */
	public void addChromosome(C chromosome) {
		if(!chromosomes.contains(chromosome))
			this.chromosomes.add(chromosome);
	}
	
	/**
	 * remove chromosome if exist
	 * 
	 * @param C Chromosome
	 */
	public void removeChromosome(C chromosome) {
		if(chromosomes.contains(chromosome))
			this.chromosomes.remove(chromosome);
	}

	/**
	 * size of population
	 * 
	 * @return int size
	 */
	public int getSize() {
		return this.chromosomes.size();
	}

	/**
	 * get chromosome by index
	 * 
	 * @param index 
	 * @return C chromsome or null if doesn't exist
	 */
	public C getChromosomeByIndex(int index) {
		return this.chromosomes.get(index);
	}

	/**
	 * Get random chromosome
	 * 
	 * @return C chromosome
	 */
	public C getRandomChromosome() {
		// TODO improve random generator
		// maybe use pattern strategy
		return this.getChromosomeByIndex(new Random().nextInt(this.chromosomes.size()));
	}

	/**
	 * sort population
	 * 
	 * @param Comparator 
	 */
	public void sortPopulationByFitness(Comparator<C> chromosomesComparator) {
		Collections.shuffle(this.chromosomes);
		Collections.sort(this.chromosomes, chromosomesComparator);
	}
	
	/**
	 * set the population in a specific length
	 * 
	 * @param len
	 */
	public void trim(int len) {
		this.chromosomes = this.chromosomes.subList(0, len);
	}
	
	/**
	 * clone the population
	 * 
	 * @return Population<C>
	 */
	@Override
	public Population<C> clone() {
		Population<C> newPopulation = new Population<>();
		for(C chromosome : chromosomes)
			newPopulation.addChromosome(chromosome);
		return newPopulation;
	}
}
