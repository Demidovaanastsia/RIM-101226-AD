package homework_1;

import java.util.*;

public class ConsoleApp {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.print("Пожалуйста введите данные: ");
		String lineOfNumbers = in.nextLine();

		in.close();

		boolean maskCheck = lineOfNumbers.matches(
				"^(?:\\{?\\\"?C[0-9]{3}_[0-9]+-[0-9]+(-[0-9]+)?\\\"?\\}?, ?)*(?:\\{?\\\"?C[0-9]{3}_[0-9]+-[0-9]+(-[0-9]+)?\\\"?\\}? ?){1}$");
		if (maskCheck) {

			String[] draftListOfNumbers = lineOfNumbers.replace("{", "").replace("}", "").replace(" ", "")
					.replace("\"", "").split(",");
			int[][] cleanListOfNumbers = numberNormalize(draftListOfNumbers);
			informationOutput(cleanListOfNumbers);
		} else {
			System.out.println("Неверный формат ввода данных!");
			System.exit(0);
		}
	}

	static int[][] numberNormalize(String[] draftListOfNumbers) {

		int[][] cleanListOfNumbers = new int[draftListOfNumbers.length][4];

		for (int numCounter = 0; numCounter < cleanListOfNumbers.length; numCounter++) {
			cleanListOfNumbers[numCounter][0] = Integer.parseInt(draftListOfNumbers[numCounter].substring(
					draftListOfNumbers[numCounter].indexOf("C") + 1, draftListOfNumbers[numCounter].indexOf("_")));
			cleanListOfNumbers[numCounter][1] = Integer.parseInt(draftListOfNumbers[numCounter].substring(
					draftListOfNumbers[numCounter].indexOf("_") + 1, draftListOfNumbers[numCounter].indexOf("-")));
			if (draftListOfNumbers[numCounter]
					.substring(draftListOfNumbers[numCounter].indexOf("-") + 1, draftListOfNumbers[numCounter].length())
					.indexOf("-") != -1) {
				cleanListOfNumbers[numCounter][2] = Integer.parseInt(draftListOfNumbers[numCounter].substring(
						draftListOfNumbers[numCounter].indexOf("-") + 1,
						draftListOfNumbers[numCounter].indexOf("-", draftListOfNumbers[numCounter].indexOf("-") + 1)));
				cleanListOfNumbers[numCounter][3] = Integer.parseInt(draftListOfNumbers[numCounter].substring(
						draftListOfNumbers[numCounter].indexOf("-", draftListOfNumbers[numCounter].indexOf("-") + 1)
								+ 1));
			} else {
				cleanListOfNumbers[numCounter][2] = Integer.parseInt(
						draftListOfNumbers[numCounter].substring(draftListOfNumbers[numCounter].indexOf("-") + 1));
				cleanListOfNumbers[numCounter][3] = -1;
			}
		}

		return (cleanListOfNumbers);
	}

	static void informationOutput(int[][] listOfNumbers) {
		TreeMap nameCoding = new TreeMap();

		nameCoding.put(100, new String("Легковой авто"));
		nameCoding.put(200, new String("Грузовой авто"));
		nameCoding.put(300, new String("Пассажирский транспорт"));
		nameCoding.put(400, new String("Тяжелая техника(краны)"));

		TreeMap[] costInfo = costGSMClasses(listOfNumbers);
		TreeMap costGSM = costInfo[0];
		TreeMap[] boundaryValues = Arrays.copyOfRange(costInfo, 1, 3);
		TreeMap minValueCost = calcMinValue(costInfo[2]);
		TreeMap maxValueCost = calcMaxValue(costInfo[1]);
		double costGSMTotal = costGSMTotal(costGSM);

		int[][] sortInfoAdditionParCopy1 = new int[listOfNumbers.length][4];
		int[][] sortInfoAdditionParCopy2 = new int[listOfNumbers.length][4];

		for (int i3 = 0; i3 < listOfNumbers.length; i3++)
			for (int j3 = 0; j3 < listOfNumbers[i3].length; j3++)
				sortInfoAdditionParCopy1[i3][j3] = listOfNumbers[i3][j3];

		int[][] sortInfoAdditionPar = sortListOfNumber(sortInfoAdditionParCopy1, 3);

		for (int i4 = 0; i4 < listOfNumbers.length; i4++)
			for (int j4 = 0; j4 < listOfNumbers[i4].length; j4++)
				sortInfoAdditionParCopy2[i4][j4] = listOfNumbers[i4][j4];

		int[][] sortInfoMileage = sortListOfNumber(sortInfoAdditionParCopy2, 2);
		int[] numbers = { 100, 200, 300, 400 };

		System.out.println("Расходы на каждый класс авто");
		Set set1 = costGSM.entrySet();
		Iterator i1 = set1.iterator();
		while (i1.hasNext()) {
			Map.Entry me1 = (Map.Entry) i1.next();
			System.out.print(nameCoding.get(me1.getKey()) + " - " + me1.getKey() + ": ");
			if ((double) me1.getValue() == 0.0) {
				System.out.println("Нет данных");
			} else {
				System.out.printf("%.2f у.е\n", me1.getValue());
			}
		}

		System.out.printf("\nОбщая стоимость расходов на ГСМ: %.2f у.е\n\n", costGSMTotal);

		System.out.println("Наибольшая стоимость расходов для каждого типа авто");
		Set set2 = boundaryValues[0].entrySet();
		Iterator i2 = set2.iterator();
		while (i2.hasNext()) {
			Map.Entry me2 = (Map.Entry) i2.next();
			System.out.print(nameCoding.get(me2.getKey()) + " - " + me2.getKey() + ": ");
			if ((double) me2.getValue() == 0.0) {
				System.out.println("Нет данных");
			} else {
				System.out.printf("%.2f у.е\n", me2.getValue());
			}
		}

		System.out.println("\nНаибольшая среди всех типов авто: Тип - " + maxValueCost.firstKey() + " ("
				+ nameCoding.get(maxValueCost.firstKey()) + "), Значение - " + maxValueCost.get(maxValueCost.firstKey())
				+ " у.е\n");

		System.out.println("Наименьшая стоимость расходов для каждого типа авто");
		Set set3 = boundaryValues[1].entrySet();
		Iterator i3 = set3.iterator();
		while (i3.hasNext()) {
			Map.Entry me3 = (Map.Entry) i3.next();
			System.out.print(nameCoding.get(me3.getKey()) + " - " + me3.getKey() + ": ");
			if ((double) me3.getValue() == 0.0) {
				System.out.println("Нет данных");
			} else {
				System.out.printf("%.2f у.е\n", me3.getValue());
			}
		}

		System.out.println("\nНаименьшая среди всех типов авто: Тип - " + minValueCost.firstKey() + " ("
				+ nameCoding.get(minValueCost.firstKey()) + "), Значение - " + minValueCost.get(minValueCost.firstKey())
				+ " у.е\n");

		System.out.println("Информация по каждом авто (сортировка по доп.параметру)\n");
		for (int Iter = 0; Iter < numbers.length; Iter++) {
			System.out.println("Авто тип " + numbers[Iter] + " (" + nameCoding.get(numbers[Iter]) + ") :\n");
			for (int IterNext = 0; IterNext < sortInfoAdditionPar.length; IterNext++) {
				if (sortInfoAdditionPar[IterNext][0] == numbers[Iter]) {
					System.out.println("Тип авто: " + sortInfoAdditionPar[IterNext][0]);
					System.out.println("Номер авто: " + sortInfoAdditionPar[IterNext][1]);
					System.out.println("Пробег: " + sortInfoAdditionPar[IterNext][2]);
					if (sortInfoAdditionPar[IterNext][3] == -1) {
						System.out.println("Доп.параметр: Нет доп.параметра");
					} else {
						System.out.println("Доп.параметр: " + sortInfoAdditionPar[IterNext][3]);
					}

					System.out.println();
				}
			}
		}
		System.out.println("Информация по каждом авто (сортировка по пробегу)\n");
		for (int Iter = 0; Iter < numbers.length; Iter++) {
			System.out.println("Авто тип " + numbers[Iter] + " (" + nameCoding.get(numbers[Iter]) + ") :\n");
			for (int IterNext = 0; IterNext < sortInfoMileage.length; IterNext++) {
				if (sortInfoMileage[IterNext][0] == numbers[Iter]) {
					System.out.println("Тип авто: " + sortInfoMileage[IterNext][0]);
					System.out.println("Номер авто: " + sortInfoMileage[IterNext][1]);
					System.out.println("Пробег: " + sortInfoMileage[IterNext][2]);
					if (sortInfoMileage[IterNext][3] == -1) {
						System.out.println("Доп.параметр: Нет доп.параметра");
					} else {
						System.out.println("Доп.параметр: " + sortInfoMileage[IterNext][3]);
					}
					System.out.println();
				}
			}
		}
	}

	static TreeMap[] costGSMClasses(int[][] listOfNumbers) {

		double costCurrentItem;
		double balanceOfCurrentType;
		double maxCost;
		double minCost;

		TreeMap[] results = new TreeMap[3];

		TreeMap costsOfFuel = new TreeMap();

		costsOfFuel.put(100, new Double(46.10));
		costsOfFuel.put(200, new Double(48.90));
		costsOfFuel.put(300, new Double(47.50));
		costsOfFuel.put(400, new Double(48.90));

		TreeMap fuelCosumption = new TreeMap();

		fuelCosumption.put(100, new Double(12.50));
		fuelCosumption.put(200, new Double(12.0));
		fuelCosumption.put(300, new Double(11.50));
		fuelCosumption.put(400, new Double(20.0));

		TreeMap costGSM = new TreeMap();

		costGSM.put(100, new Double(0.0));
		costGSM.put(200, new Double(0.0));
		costGSM.put(300, new Double(0.0));
		costGSM.put(400, new Double(0.0));

		TreeMap boundaryMaxValues = new TreeMap();

		boundaryMaxValues.put(100, new Double(0.0));
		boundaryMaxValues.put(200, new Double(0.0));
		boundaryMaxValues.put(300, new Double(0.0));
		boundaryMaxValues.put(400, new Double(0.0));

		TreeMap boundaryMinValues = new TreeMap();

		boundaryMinValues.put(100, new Double(0.0));
		boundaryMinValues.put(200, new Double(0.0));
		boundaryMinValues.put(300, new Double(0.0));
		boundaryMinValues.put(400, new Double(0.0));

		for (int numCounter = 0; numCounter < listOfNumbers.length; numCounter++) {

			costCurrentItem = (((double) listOfNumbers[numCounter][2] / 100)
					* ((double) fuelCosumption.get(listOfNumbers[numCounter][0]))
					* ((double) costsOfFuel.get(listOfNumbers[numCounter][0])));
			balanceOfCurrentType = ((Double) costGSM.get(listOfNumbers[numCounter][0])).doubleValue();
			costGSM.put(listOfNumbers[numCounter][0], costCurrentItem + balanceOfCurrentType);

			if (costCurrentItem < (Double) boundaryMinValues.get(listOfNumbers[numCounter][0])
					|| (Double) boundaryMinValues.get(listOfNumbers[numCounter][0]) == 0) {
				boundaryMinValues.put(listOfNumbers[numCounter][0], costCurrentItem);
			}

			if (costCurrentItem > (Double) boundaryMaxValues.get(listOfNumbers[numCounter][0])) {
				boundaryMaxValues.put(listOfNumbers[numCounter][0], costCurrentItem);
			}

		}

		results[0] = costGSM;
		results[1] = boundaryMaxValues;
		results[2] = boundaryMinValues;

		return results;

	}

	static double costGSMTotal(TreeMap costGSM) {
		double totalCost = 0.0;
		Set set = costGSM.entrySet();
		Iterator costIter = set.iterator();

		while (costIter.hasNext()) {
			Map.Entry costValue = (Map.Entry) costIter.next();
			totalCost += (double) costValue.getValue();
		}
		return totalCost;
	}

	static TreeMap calcMinValue(TreeMap boundaryMinValues) {
		int minType = 0;
		double minValue = 0.0;

		TreeMap minInfo = new TreeMap();

		Set set = boundaryMinValues.entrySet();
		Iterator Iter = set.iterator();

		while (Iter.hasNext()) {
			Map.Entry typeMinValue = (Map.Entry) Iter.next();
			if ((double) typeMinValue.getValue() < minValue || minValue == 0) {
				minValue = (double) typeMinValue.getValue();
				minType = (int) typeMinValue.getKey();
			}

		}

		minInfo.put(minType, new Double(minValue));

		return minInfo;
	}

	static TreeMap calcMaxValue(TreeMap boundaryMaxValues) {
		int maxType = 0;
		double maxValue = 0.0;

		TreeMap maxInfo = new TreeMap();

		Set set = boundaryMaxValues.entrySet();
		Iterator Iter = set.iterator();

		while (Iter.hasNext()) {
			Map.Entry typeMinValue = (Map.Entry) Iter.next();
			if ((double) typeMinValue.getValue() > maxValue || maxValue == 0) {
				maxValue = (double) typeMinValue.getValue();
				maxType = (int) typeMinValue.getKey();
			}
		}

		maxInfo.put(maxType, new Double(maxValue));

		return maxInfo;
	}

	static int[][] sortListOfNumber(int[][] listOfNumbers, int numOfSortField) {

		for (int elemIter = 1; elemIter < listOfNumbers.length; elemIter++) {
			int[] currentElem = listOfNumbers[elemIter];
			int elemFilterIter = elemIter - 1;
			while (elemFilterIter >= 0 && currentElem[numOfSortField] < listOfNumbers[elemFilterIter][numOfSortField]) {
				listOfNumbers[elemFilterIter + 1] = listOfNumbers[elemFilterIter];
				elemFilterIter--;
			}
			listOfNumbers[elemFilterIter + 1] = currentElem;
		}

		return listOfNumbers;
	}
}

//{"C100_1-100", "C200_1-120-1200", "C300_1-120-30", "C400_1-80-20", "C100_2-50", "C200_2-40-1000", "C300_2-200-45", "C400_2-10-20", "C100_3-10", "C200_3-170-1100", "C300_3-150-29", "C400_3-100-28", "C100_1-300", "C200_1-100-750", "C300_1-32-15"}
