package simulation;

public record SimulationStatistics (
	int noOfAnimals,
	int noOfPlants,
	double meanLifeLength,
	double meanNumberOfChildren,
	double meanEnergy,
	int dayNumber
) {
	@Override
	public String toString() {
		return String.format("Number of animals: %d%n"
				+ "Number of plants: %d%n"
				+ "Mean age: %.2f%n"
				+ "Mean no of children: %.2f%n"
				+ "Mean energy: %.2f%n"
				+ "Day number: %d%n",
			noOfAnimals,
			noOfPlants,
			meanLifeLength,
			meanNumberOfChildren,
			meanEnergy,
			dayNumber);
	}
}
