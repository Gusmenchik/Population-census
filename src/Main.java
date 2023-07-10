import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");

        Collection<Person> persons = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(random.nextInt(names.size())),
                    families.get(random.nextInt(families.size())),
                    random.nextInt(100),
                    Sex.values()[random.nextInt(Sex.values().length)],
                    Education.values()[random.nextInt(Education.values().length)])
            );
        }

        // Находим количество несовершеннолетних
        long countMinors = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + countMinors);

        // Получаем список фамилий призывников
        List<String> conscriptLastNames = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() < 27)
                .map(Person::getFamily)
                .toList();
        System.out.println("Фамилии призывников: " + conscriptLastNames);

        // Получаем отсортированный по фамилии список потенциально работоспособных людей с высшим образованием
        List<Person> potentialWorkers = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER &&
                        ((person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 65) ||
                                (person.getSex() == Sex.WOMAN && person.getAge() >= 18 && person.getAge() <= 60)))
                .sorted(Comparator.comparing(Person::getFamily))
                .toList();
        System.out.println("Потенциально работоспособные люди с высшим образованием:");
        potentialWorkers.forEach(System.out::println);
    }
}

