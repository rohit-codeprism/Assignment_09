package com.rohit.assignment_09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Movie {
	
	private int movieId;
	private String movieName;
	private String  category;
	private String langauge;
	private Date releaseDate;
	private String casting ;
	private Double rating ;
	private Double totalBussinessDone;
	
	static SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
	static Connection con;
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLangauge() {
		return langauge;
	}
	public void setLangauge(String langauge) {
		this.langauge = langauge;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getCasting() {
		return casting;
	}
	public void setCasting(String words) {
		this.casting = words;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Double getTotalBussinessDone() {
		return totalBussinessDone;
	}
	public void setTotalBussinessDone(Double totalBussinessDone) {
		this.totalBussinessDone = totalBussinessDone;
	}
	
	
	//Read data from movieDetails File 
	List<Movie> populateMovies(String filename) throws Exception
	{
		List<Movie> movies = new ArrayList<Movie>();
		
		String line = "";
		FileReader file = new FileReader(filename);
		BufferedReader br = new BufferedReader(file);
		while((line = br.readLine()) != null)
		{
			Movie m = new Movie();
			String[] words = line.split(",");
			m.setMovieId(Integer.parseInt((words[0])));
			m.setMovieName(words[1]);
			m.setCategory((words[2]));
			m.setLangauge(words[3]);
			m.setReleaseDate(sdf.parse(words[4]));
			m.setCasting(words[5]);
			m.setRating(Double.parseDouble(words[6]));
			m.setTotalBussinessDone(Double.parseDouble(words[7]));
			movies.add(m);
			
		}
		return movies;
	}
	
	//Store all movie details into database
	Boolean allAllMoviesInDb(List<Movie> movies) throws SQLException
	{
	  boolean flag = false;
	 for(Movie movie : movies)	
	 {
		 int rowCount=0;
			try {
			con=DBConnection.getcon();
			String sql="insert into movie_details values(?,?,?,?,?,?,?,?)";
			PreparedStatement pst= con.prepareStatement(sql);
			pst.setInt(1, movie.getMovieId());
			pst.setString(2, movie.getMovieName());
			pst.setString(3, movie.getCategory());
			pst.setString(4, movie.getLangauge());
			//pst.setString(5, movie.getReleaseDate());
			pst.setString(6, movie.getCasting());
			pst.setDouble(7, movie.getTotalBussinessDone());
			rowCount=pst.executeUpdate();
			if(rowCount == 1)
				flag = true;
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally 
			{
				con.close();

		    }
	 }
	   return flag;
   }
	
	 public static File getSerializedDirectory(String str)
	    {
	        File serializedDir = new File(str);
	        if (!serializedDir.exists()) {
	            serializedDir.mkdir();
	        }
	        return serializedDir;   
	    }
	
	//Serialize movie data in the provided file in the given project directory
	void serializeMovies(List<Movie> movies, File fileName)
	{
		 try
         {
            FileOutputStream fileOut =
            new FileOutputStream(fileName+"//serializedData");
            ObjectOutputStream out =
                               new ObjectOutputStream(fileOut);
            out.writeObject(movies);
            out.close();
             fileOut.close();
         }catch(IOException i)
         {
             i.printStackTrace();
         }
		
		
	}
	
	//Deserialize movies  from given files
	List<Movie> deserializeMovie(String filename)
	{
		Movie object1 = null;
		ArrayList<Movie> movie = new ArrayList<Movie>();  
        // Deserialization 
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            object1 = (Movie)in.readObject(); 
            movie.add(object1);  
            in.close(); 
            file.close(); 
              
        } 
          
        catch(IOException ex) 
        { 
		
		 System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
        return movie;
	}
	
	//Find the Movies realeased in entered year
	List<Movie> getMoviesRealeasedInYear(int year) throws SQLException
	{
        ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try {
		con=DBConnection.getcon();
		String sql="select * from Movie where releaseDate=?";
		PreparedStatement pst= con.prepareStatement(sql);
		pst.setInt(1, year);
		//rowCount=pst.executeUpdate();
		ResultSet rs= pst.executeQuery();
		if(rs.next())
		{
			movie.add((Movie) rs);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			con.close();

		}
		return movie;
		
	}
	
	//Find the list of movies by actor
	List<Movie> getMoviesByActor(String actorNames) throws SQLException
	{
        ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try {
		con=DBConnection.getcon();
		String sql="select * from Movie where actorName=?";
		PreparedStatement pst= con.prepareStatement(sql);
		pst.setString(1,actorNames);
		//rowCount=pst.executeUpdate();
		ResultSet rs= pst.executeQuery();
		if(rs.next())
		{
			movie.add((Movie) rs);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			con.close();

		}
		return movie;
		
	}
	
    //Find the set of movies as per the review comments  done business more than entered amount descending order of amount
	List<Movie> businessDone(double amount) throws SQLException
	{
        ArrayList<Movie> movie = new ArrayList<Movie>();
		
		try {
		con=DBConnection.getcon();
		String sql="select * from Movie where amount = ?";
		PreparedStatement pst= con.prepareStatement(sql);
		pst.setDouble(1,amount);
		ResultSet rs= pst.executeQuery();
		if(rs.next())
		{
			movie.add((Movie) rs);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			con.close();

		}
		return movie;
		
	}
	
	//Update Movie Rating
	void updateRatings(Movie movie, double rating ,List<Movie> movies) throws SQLException
	{
		String sql="update Movie set rating = ? where movieName = ?";
	    PreparedStatement pst= con.prepareStatement(sql);
	    pst.setDouble(1,rating);
 	    pst.setString(2, movie.getMovieName());
 	    int rs=pst.executeUpdate();
	    if(rs>0)
	      System.out.println("Rating updated successfully");
	    else
		   System.out.println("Rating Not updated ");
			
		
	}
	
	//Update Business Done by Movie
	void updateBusiness(Movie movie, double amount,List<Movie> movies) throws SQLException
	{
		String sql="update Movie set bussines = ? where movieName = ?";
	    PreparedStatement pst= con.prepareStatement(sql);
	    pst.setDouble(1,amount);
 	    pst.setString(2, movie.getMovieName());
 	    int rs=pst.executeUpdate();
	    if(rs>0)
	      System.out.println("Bussiness updated successfully");
	    else
		  System.out.println("Bussiness  Not updated ");
			
	}









	

}
