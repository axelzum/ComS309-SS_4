package first.experiment;

public class User
{

  private final int id;
  private final String name;
  private final String email;
  private String userName;

  public User(int ID, String name, String email, String userName)
  {
    id = ID;
    this.name = name;
    this.email = email;
    this.userName = userName;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String uName)
  {
    userName = uName;
  }

}

