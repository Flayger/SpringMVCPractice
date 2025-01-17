package org.flayger.springcource.dao;

import com.mysql.cj.protocol.Resultset;
import org.flayger.springcource.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //вернуть список всех людей
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    //    //одного человека
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age, email, address) VALUES(?,?,?,?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());

    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?,age=?,email=?, address=? WHERE id=?",
                updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    //тест пакетной вставки
    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?,?)", person.getId(),
                    person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println("Time 1-1: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@ya.ru", "some address"));
        }
        return people;
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?,?,?,?)",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, people.get(i).getId());
                preparedStatement.setString(2, people.get(i).getName());
                preparedStatement.setInt(3, people.get(i).getAge());
                preparedStatement.setString(4, people.get(i).getEmail());
            }
            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after = System.currentTimeMillis();
        System.out.println("Time batch: " + (after - before));
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
}
