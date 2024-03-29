import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
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
            System.out.println("- see all movies_data.csv of a (g)enre");
            System.out.println("- list top 50 (r)ated movies_data.csv");
            System.out.println("- list top 50 (h)igest revenue movies_data.csv");
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

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies_data.csv in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
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
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
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

    private void searchCast()
    {
        System.out.print("Enter actor name: ");
        String input = scanner.nextLine().toLowerCase();
        TreeSet<String> actors = new TreeSet<String>();
        for (Movie movie: movies) {
            String[] cast = movie.getCast().split("\\|");
            for (int i = 0; i < cast.length; i++) {
                actors.add(cast[i]);
            }
        }
        String[] actorList = actors.toArray(new String[0]);
        for (int i = 0; i < actorList.length; i++) {
            if (actorList[i].toLowerCase().indexOf(input) == -1) {
                actorList[i] = null;
            }
        }
        int counter = 1;
        for (int i = 0; i < actorList.length; i++) {
            if (actorList[i] != null) {
                System.out.println(counter + "." + actorList[i]);
                counter++;
            }
        }
        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");
        int numberInput = Integer.parseInt(scanner.nextLine());
        String name = "";
        counter = 0;
        for (int i = 0; i < actorList.length && counter != numberInput; i++) {
            if (actorList[i] != null) {
                counter++;
            }
            if (counter == numberInput) {
                name = actorList[i];
            }
        }
        ArrayList<Movie> actorMovies = new ArrayList<Movie>();
        for (Movie movie: movies) {
            if (movie.getCast().contains(name)) {
                actorMovies.add(movie);
            }
        }
        sortResults(actorMovies);
        for (int i = 0; i < actorMovies.size(); i++)
        {
            String title = actorMovies.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + actorMovies.get(i).getTitle());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = actorMovies.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies_data.csv in collection
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

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();
            String keywords = results.get(i).getKeywords();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + " (" + keywords + ")");
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        TreeSet<String> genres = new TreeSet<String>();
        for (Movie movie: movies) {
            String[] genre = movie.getGenres().split("\\|");
            genres.addAll(Arrays.asList(genre));
        }
        String[] genresList = genres.toArray(new String[0]);
        int counter = 0;
        for (String genre: genres) {
            counter++;
            System.out.println(counter + ". " + genre);
        }

        System.out.print("Choose a genre: ");
        int input = Integer.parseInt(scanner.nextLine());
        ArrayList<Movie> movieGenre = new ArrayList<Movie>();
        System.out.println(genresList[input + 1]);
        for (Movie movie: movies) {
            if (movie.getGenres().contains(genresList[input - 1])) {
                movieGenre.add(movie);
            }
        }
        sortResults(movieGenre);
        for (int i = 0; i < movieGenre.size(); i++)
        {
            String title = movieGenre.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + movieGenre.get(i).getTitle());
        }
        System.out.print("Choose a title: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = movieGenre.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> temp = (ArrayList<Movie>)movies.clone();
        ArrayList<Movie> temp2 = (ArrayList<Movie>)movies.clone();
        do {
            temp = (ArrayList<Movie>)temp2.clone();
            for (int i = 1; i < temp2.size(); i++) {
                if (temp2.get(i).getUserRating() > temp2.get(i - 1).getUserRating()) {
                    Movie temporary = temp2.get(i - 1);
                    temp2.set(i - 1, temp2.get(i));
                    temp2.set(i, temporary);
                }
            }
        } while (!temp.equals(temp2));

        for (int i = 0; i < 50; i++) {
            System.out.println(i + 1 + ". " + temp.get(i).getTitle() + ": " + temp.get(i).getUserRating());
        }
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> temp = (ArrayList<Movie>)movies.clone();
        ArrayList<Movie> temp2 = (ArrayList<Movie>)movies.clone();
        do {
            temp = (ArrayList<Movie>)temp2.clone();
            for (int i = 1; i < temp2.size(); i++) {
                if (temp2.get(i).getRevenue() > temp2.get(i - 1).getRevenue()) {
                    Movie temporary = temp2.get(i - 1);
                    temp2.set(i - 1, temp2.get(i));
                    temp2.set(i, temporary);
                }
            }
        } while (!temp.equals(temp2));

        for (int i = 0; i < 50; i++) {
            System.out.println(i + 1 + ". " + temp.get(i).getTitle() + ": " + temp.get(i).getRevenue());
        }
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

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

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}