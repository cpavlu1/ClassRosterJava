package classroster;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Roster 
{
	private LinkedSet<Student> myset = null;
	private String className;
	private Iterator<Student> globaliter;
	//---------------------------------------------------------------------------
	// Create a blank roster to start storing students
	//---------------------------------------------------------------------------
	public Roster(String cn)
	{
		myset = new LinkedSet<Student>();
		className = cn;
		globaliter = myset.iterator();
	};
	//---------------------------------------------------------------------------
	// Add a student to the class
	//---------------------------------------------------------------------------
	public void add(String l, String f, String n, String id, String picfile, String e)
	{
		Student toAdd = new Student (l,f,n,id,picfile,e);
		myset.add(toAdd);
	}
	//---------------------------------------------------------------------------
	// Remove a student from the class
	//---------------------------------------------------------------------------
	public boolean remove (String id)
	{
		 try {
			if (myset.remove(new Student(null,null,null,id,null,null)) == null)
					 return false;
		} catch (Exception e) {
			System.out.println("There is no student with that ID");
			e.printStackTrace();
		}
		 return true;
		
	}
	//---------------------------------------------------------------------------
	//	Return the class name
	//---------------------------------------------------------------------------
	public String getClassName() 
	{
		return className;
	}
	//---------------------------------------------------------------------------
	// Set the class name
	//---------------------------------------------------------------------------
	public void setClassName(String className) 
	{
		this.className = className;
	}
	//---------------------------------------------------------------------------
	// Return the Student name based on ID
	//---------------------------------------------------------------------------
	public Student getStudent(String id)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		Iterator<Student> iter2 = myset.iterator();
		// Create Students to store student and for failure cases
		Student failed = new Student(null, null, null, id, null, null);
		Student toReturn = new Student(null,null,null,null,null,null);
		// Move through to find student
		while(iter.hasNext())
		{
			// Store parallel student
			toReturn = iter2.next();
			if(iter.next().getId().equals(id))
			{
				// Return student
				return toReturn;
			}
		}
		// For failure
		return failed;
	}
	//---------------------------------------------------------------------------
	// Return just the next student
	//---------------------------------------------------------------------------
	public Student getNextStudent()
	{
		while(globaliter.hasNext())
		{
			return globaliter.next();
		}
		if(globaliter.hasNext() == false)
		{
			globaliter = myset.iterator();
		}
		return globaliter.next();
	}
	//---------------------------------------------------------------------------
	// Iterate through the students
	//---------------------------------------------------------------------------
	public int numberOfStudents()
	{
		Iterator<Student> iter = myset.iterator();
		int count = 0;
		while(iter.hasNext())
		{
			count++;
			iter.next();
		}
		return count;
	}
	//---------------------------------------------------------------------------
	// Update Student Last Name
	//---------------------------------------------------------------------------
	public void updateLastName(String id, String ln)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		// Create Students to store student new data
		Student update = new Student(null,null,null,null,null,null);
		// Cycle through to find student to update and change data
		update = iter.next();
		while(iter.hasNext())
		{
			if(update.getId().equals(id))
			{
				update.setLast(ln);
			}
			update = iter.next();
		}
	}
	//---------------------------------------------------------------------------
	// Update Student First Name
	//---------------------------------------------------------------------------
	public void updateFirstName(String id, String fn)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		// Create Students to store student new data
		Student update = new Student(null,null,null,null,null,null);
		// Cycle through to find student to update and change data
		update = iter.next();
		while(iter.hasNext())
		{
			if(update.getId().equals(id))
			{
				update.setFirst(fn);
			}
			update = iter.next();
			
		}
	}
	//---------------------------------------------------------------------------
	// Update Student Nickname
	//---------------------------------------------------------------------------
	public void updateNickname(String id, String n)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		// Create Students to store student new data
		Student update = new Student(null,null,null,null,null,null);
		// Cycle through to find student to update and change data
		update = iter.next();
		while(iter.hasNext())
		{
			if(update.getId().equals(id))
			{
				update.setNickname(n);
			}
			update = iter.next();
		}
	}
	//---------------------------------------------------------------------------
	// Update Student ID
	//---------------------------------------------------------------------------
	public void updateID(String id, String nid)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		// Create Students to store student new data
		Student update = new Student(null,null,null,null,null,null);
		// Cycle through to find student to update and change data
		update = iter.next();
		while(iter.hasNext())
		{
			if(update.getId().equals(id))
			{
				update.setId(nid);
			}
			update = iter.next();
		}
	}
	//---------------------------------------------------------------------------
	// Update Student Pic
	//---------------------------------------------------------------------------
	public void updatePicFile(String id, String pf)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		// Create Students to store student new data
		Student update = new Student(null,null,null,null,null,null);
		// Cycle through to find student to update and change data
		update = iter.next();
		while(iter.hasNext())
		{
			if(update.getId().equals(id))
			{
				update.setPicFile(pf);
			}
			update = iter.next();
		}
	}
	//---------------------------------------------------------------------------
	// Update Student Email
	//---------------------------------------------------------------------------
	public void updateEmail(String id, String e)
	{
		// Create iterators to move through and store students
		Iterator<Student> iter = myset.iterator();
		// Create Students to store student new data
		Student update = new Student(null,null,null,null,null,null);
		// Cycle through to find student to update and change data
		update = iter.next();
		while(iter.hasNext())
		{
			if(update.getId().equals(id))
			{
				update.setEmail(e);
			}
			update = iter.next();
		}
	}
	//---------------------------------------------------------------------------
	// Write to file to pull students
	//---------------------------------------------------------------------------
	public void writeToFile()
	{
		// Create a built in java bufferedwriter
        BufferedWriter writer = null;
        try {
        	// Create and initialize the file to save data to
            File logFile = new File("roster.txt");
            // This will output the full path where the file will be written to...
            // System.out.println(logFile.getCanonicalPath());
            //------------------------------------------------------
            // Built in bufferedwriter is passed the roster file
            writer = new BufferedWriter(new FileWriter(logFile));
            //write everything to the roster file
            //writer.write("Hello world!");
            Iterator<Student> iter = myset.iterator();
            writer.write(className + System.lineSeparator());
    		while (iter.hasNext()) 
    		{
    			Student log = iter.next();
    			writer.write(log.getLast()+","+log.getFirst()+","+log.getNickname()+","+log.getId()+","+log.getPicFile()+","+log.getEmail()+System.lineSeparator());
    		}
    		//------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
        
	}
	//---------------------------------------------------------------------------
	// Read from file to pull students
	//---------------------------------------------------------------------------
	public void readFromFile(FileDialog fd) throws IOException
	{
		//Create file and find path
		File logFile = new File(fd.getDirectory()+fd.getFile());
		// This will output the full path where the file will be written to...
        //System.out.println(logFile.getCanonicalPath());
		//Create a new bufferedreader built in object and pass it the roster file
		BufferedReader br = new BufferedReader(new FileReader(logFile));
		try {
			//Create and intialize the StringBuilder object
		    //StringBuilder sb = new StringBuilder();
		    //Create a string and pass it a line of the roster file
		    className = br.readLine();
		    String line = br.readLine();
		    //Check to see if the line has anything
		    while (line != null) 
		    {
		    	//Store the line as a string object
		        //sb.append(line);
		        String stringline = line;
		        String[] fields = stringline.split(", -");
		        Student student = new Student(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
		        System.out.println(student);
		        myset.add(student);
		        //read the next line
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
	}
	//---------------------------------------------------------------------------
	// Read from file to pull students
	//---------------------------------------------------------------------------
	public void readFromFile() throws IOException
	{
		//Create file and find path
		File logFile = new File("roster.txt");
		// This will output the full path where the file will be written to...
        //System.out.println(logFile.getCanonicalPath());
		//Create a new bufferedreader built in object and pass it the roster file
		BufferedReader br = new BufferedReader(new FileReader(logFile));
		try {
			//Create and intialize the StringBuilder object
		    //StringBuilder sb = new StringBuilder();
		    //Create a string and pass it a line of the roster file
		    className = br.readLine();
		    String line = br.readLine();
		    //Check to see if the line has anything
		    while (line != null) 
		    {
		    	//Store the line as a string object
		        //sb.append(line);
		        String stringline = line;
		        String[] fields = stringline.split(",");
		        Student student = new Student(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
		        myset.add(student);
		        //read the next line
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
	}
	//---------------------------------------------------------------------------
	// Convert student to string
	//---------------------------------------------------------------------------
	public String toString () 
	{
		String toReturn = className+"\n";
		Iterator<Student> iter = myset.iterator();

		while (iter.hasNext()) {
			toReturn = toReturn + iter.next()+"\n";
		}		
		return toReturn;
	}
}
