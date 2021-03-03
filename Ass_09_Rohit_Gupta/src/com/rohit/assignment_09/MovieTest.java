package com.rohit.assignment_09;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class MovieTest {
	
	public static void main(String[]args) throws Exception
	{
		Movie movie = new Movie();
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Movie.txt file location");
		String filepath = s.nextLine();
		System.out.println("Data From movie.txt file are as ");
		List<Movie> fatchedMovie  = movie.populateMovies(filepath);
		
		System.out.println("Inserted all movie into Database: "+ movie.allAllMoviesInDb(fatchedMovie));
		
		System.out.println("Enter serialization file location");
		String sfileName = s.nextLine();
		
		File directory = movie.getSerializedDirectory(sfileName);
		
		
		System.out.println("Serializing Movie data ");
		movie.serializeMovies(fatchedMovie, directory);
		
		//Deserialize movies  from given files
		System.out.println("Deserializing Movie from the file");
		for(Movie m : movie.deserializeMovie(sfileName))
		{
			System.out.println(m);
		}
		
		//Find the Movies realeased in entered year
		System.out.println("Enter year to search Movie ");
		int year  = Integer.parseInt(s.nextLine());
		for(Movie m : movie.getMoviesRealeasedInYear(year))
		{
			System.out.println(m);
		}
		
		//Find the list of movies by actor
		System.out.println("Enter Actor name to find the Movie:");
		String actorName = s.nextLine();
		for(Movie m: movie.getMoviesByActor(actorName))
		{
			System.out.println(m);
		}
		
		
		//Find the set of movies as per the review comments  done business more than entered amount descending order of amount
		System.out.println("Enter the Earned Amount to search a movie");
		Double amt = Double.parseDouble(s.nextLine());
		for(Movie m : movie.businessDone(amt))
		{
			System.out.println(m);
		}
		
		//Update Movie Rating
		System.out.println("Enter a Rating to update Movie Rating ");
		Double rating  = Double.parseDouble(s.nextLine());
		movie.updateRatings(movie, rating, fatchedMovie);

		
		//Update Business Done by Movie
		System.out.println("Enter amount to update Bussiness");
		Double updateAmt = Double.parseDouble(s.nextLine());
		movie.updateBusiness(movie, updateAmt, fatchedMovie);
		
		
		System.out.println("All services End");
	}

	
	
}
