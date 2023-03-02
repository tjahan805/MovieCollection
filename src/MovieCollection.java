import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1) {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        if (results.size() > 0) {
            // sort the results by title
            sortResults(results);

            // now, display them all to the user
            for (int i = 0; i < results.size(); i++) {
                String title = results.get(i).getTitle();

                // this will print index 0 as choice 1 in the results list; better for user!
                int choiceNum = i + 1;
                System.out.println("" + choiceNum + ". " + title);
            }

            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            Movie selectedMovie = results.get(choice - 1);
            displayMovieInfo(selectedMovie);
            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        } else {
            System.out.println("\nNo movie titles match that search term!");
            System.out.println("** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keywords = movies.get(i).getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        displayResults(results, "title");
    }

    private void searchCast()
    {
        System.out.print("Enter a person to search for (first or last name): ");
        String searchTerm = scanner.nextLine();

        // call helper method to build and get unique cast member list
        ArrayList<String> uniqueCastMemberList = buildUniqueCastList();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        // search through all cast members in the unique cast members list
        for (int i = 0; i < uniqueCastMemberList.size(); i++)
        {
            String castMember = uniqueCastMemberList.get(i).toLowerCase();

            if (castMember.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(uniqueCastMemberList.get(i));
            }
        }

        if (results.size() > 0)
        {
            // call sort helper method, which sorts an arraylist of Strings
            sortStrings(results);

            // call helper method that shows a numbered list of matching cast members, and RETURNS the string selected
            String chosenCastMember = displayStringResultsAndChoose(results);

            // call helper method that shows numbered list of movies that the selected cast member appears in
            displayMovieListForCast(chosenCastMember);
        }
        else
        {
            System.out.println("No results match your search");
            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
    }


        return castMembersSplit;
    }

    private void listGenres()
    {
        // call helper method to build and get unique genre member list
        ArrayList<String> uniqueGenreList = buildUniqueGenreList();

        // call sort helper method, which sorts the arraylist of Strings
        sortStrings(uniqueGenreList);

        // call helper method that shows a numbered list of genres, and RETURNS the string selected
        String chosenGenre = displayStringResultsAndChoose(uniqueGenreList);

        // call helper method that shows numbered list of movies that the selected cast member appears in
        displayMovieListForGenre(chosenGenre);
    }


    private void listHighestRated()
    {
        // sort movies by rating; this sorts low to high; note that there is no "secondary sort" so
        // movies with the same rating should appear together in the list,
        // but not necessarily in alphabetical order
        sortResultsByRating(movies);

        // since movies are sorted low to high, store last 50 elements in a temp arraylist
        ArrayList<Movie> movieRevenueList = new ArrayList<Movie>();

        for (int i = 1; i <= 50; i++)
        {
            int movieIndex = movies.size() - i;
            movieRevenueList.add(movies.get(movieIndex));
        }

        displayResults(movieRevenueList, "rating");
    }

    private void listHighestRevenue()
    {
        // sort movies by revenue; this sorts low to high; note that there is no "secondary sort" so
        // movies with the same revenue should appear together in the list,
        // but not necessarily in alphabetical order
        sortResultsByRevenue(movies);

        // since movies are sorted low to high, store last 50 elements in a temp arraylist
        ArrayList<Movie> movieRevenueList = new ArrayList<Movie>();

        for (int i = 1; i <= 50; i++)
        {
            int movieIndex = movies.size() - i;
            movieRevenueList.add(movies.get(movieIndex));
        }

        displayResults(movieRevenueList, "revenue");
    }

    private void importMovieList(String fileName) {
        try {
            movies = new ArrayList<Movie>();
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] movieFromCSV = line.split(",");

                // pulling out the data from the array and converting
                //  to int and double as necessary
                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                // creating a Movie object
                Movie nextMovie = new Movie(title, cast, director, tagline, keywords,
                        overview, runtime, genres, userRating, year, revenue);

                // adding Movie to movies
                movies.add(nextMovie);
            }
            bufferedReader.close();
        } catch(IOException exception) {
            System.out.println("Unable to access " + exception.getMessage());
        }
    }

}
