import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        String json = "{\"name\":\"Tristan\",\"age\":20}";

        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(json, Person.class);
        System.out.println(person.name + " - " + person.age);
    }

    static class Person {
        public String name;
        public int age;
    }
}