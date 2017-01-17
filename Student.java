package classroster;

public class Student 
{
	private String last;
	private String first;
	private String nickname;
	private String id;
	private String picFile;
	private String email;
	private String path = "images/";
	public Student(String last, String first, String nickname, String id,
			String picFile, String email) 
	{

		this.last = last;
		this.first = first;
		this.nickname = nickname;
		this.id = id;
		this.picFile = picFile;
		this.email = email;
	}
	public String getLast() 
	{
		return last;
	}
	public void setLast(String last) 
	{
		this.last = last;
	}
	public String getFirst() 
	{
		return first;
	}
	public void setFirst(String first) 
	{
		this.first = first;
	}
	public String getNickname() 
	{
		return nickname;
	}
	public void setNickname(String nickname) 
	{
		this.nickname = nickname;
	}
	public String getId() 
	{
		return id;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getPicFile() 
	{
		return picFile;
	}
	public void setPicFile(String picFile) 
	{
		this.picFile = path + picFile;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String toString () 
	{
		return last+" "+nickname+" "+id+" "+email;
	}
	public boolean equals(Object totest) 
	{
		return id.equals(((Student)totest).getId());
	}

}
