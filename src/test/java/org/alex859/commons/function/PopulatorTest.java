package org.alex859.commons.function;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * Not really a test, just playing with executors.
 */
public class PopulatorTest
{

    private Populator<Person, FullName> populator =
            (person, fullName) ->
            {
                System.out.println("Populating in thread: " + Thread.currentThread().getName());
                fullName.fullName = person.name + " " + person.surname;
            };

    @Test
    public void withFutureSource() throws Exception
    {
        final FullName fullName = new FullName();

        final ExecutorService executor = Executors.newFixedThreadPool(2);

        final CompletableFuture<Person> personFuture = CompletableFuture.supplyAsync(() ->
                {
                    System.out.println("Creating source in thread: " + Thread.currentThread().getName());
                    return new Person("Ale", "Cicci");
                }
                , executor);

        final Function<FullName, CompletableFuture<Void>> futurePopulator = populator.withFutureSource(personFuture, executor);

        futurePopulator.apply(fullName).join();

        System.out.println(fullName.fullName);


    }

    class Person
    {
        final String name;
        final String surname;

        Person(final String name, final String surname)
        {
            this.name = name;
            this.surname = surname;
        }
    }

    class FullName
    {
        String fullName;
    }
}