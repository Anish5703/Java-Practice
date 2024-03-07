class BookNotAvailableException extends RuntimeException     //exception if the book is not available
{
    public BookNotAvailableException(String message)
    {
        super(message);
    }
}
class BookNotExistsException extends RuntimeException        //exception if the book was never available in the library
{
    public BookNotExistsException(String message)
    {
        super(message);
    }
}
class BorrowedLimitExceedException extends RuntimeException   //exception if the borrowed limit of a user has exceeds
{  
    public BorrowedLimitExceedException(String message)
    {
        super(message);
    }
}
class BookStoringLimitExceedException extends RuntimeException   //exception if the adding book limit reached
{
    public BookStoringLimitExceedException(String message)
    {
        super(message);
    }
}

//Class For Book 
class Book
{
      String title;          //book name
      String author;         //book author
      int availability = 0;  //initializing availability
      int  quantity;         //total quantity of book

      public Book(){}      //Default class Book Constructor
    
    public Book(String title,String author,int quantity)  //custom constructor for class book 
    {
      this.title = title;
      this.author = author;
      this.quantity = quantity;
      this.availability = quantity;
    }
    //checking if book is available to borrow
    public boolean isBookAvailable(String title,String author)  //returning boolean value true if book is available
    {
        if(availability>0)
         return true;
        else
         return false;
    }    
 }



//Class for Library
class Library 
{
    static Book[] books;       //arrayObject of class book
    static int bookCount;      //total quantity of books


    public Library()           //Default Constructor for Class Library
    {}
    
    //Constructor defining maximum book Type limit in library
    public Library(int maxBooks)
    {
        this.books = new Book[maxBooks];
        this.bookCount = 0;
    }

    //Add book to library
    void addBook(String Btitle,String Bauthor,int Bquantity)
    {
        boolean status = true;
        
        if(bookCount<books.length)       //checking if books quantity exceeds limit
       {
         for(int i=0;i<bookCount;i++)
         {
            if(books[i].title==Btitle &&  books[i].author==Bauthor)     //if book with name and author already exists then add quantity only
               {
                books[i].quantity += Bquantity;                     //add new quantity to old quantity of the book
                books[i].availability += Bquantity;                 //add to the availability
                System.out.println("Book with same details already exists so Quanatity is only Increased");
                status = false;
                break;
               }
          }
        if(status)   
         books[bookCount++]= new Book(Btitle,Bauthor,Bquantity);    
       }
       else
        throw new BookStoringLimitExceedException("Cannot add new books");
    }
    
    //Remove book from library
  void removeBook(String title,String author)
    {
        boolean status = false;        //setting boolean value 
        int foundIndex=0;              
        for(int i=0;i<bookCount;i++)  //searching for index  of required book
        {
            if(title==books[i].title && author==books[i].author)    //matching title & author
             {
               foundIndex = i;
               status = true;
               break;           
             }
        }
       
       if(status)
        {
            if(foundIndex==bookCount-1)   //if index is the last book count 
             bookCount--;                 //decrementing book count
            else
            {
                for(int i=foundIndex;i<bookCount-1;i++)
                 books[i] = books[i+1];                  //replace content of index with next index
                 bookCount--;                            //decrementing book count
            }
        }
        if(status)
          System.out.println("Book Removed Successfully");
        else
         throw new BookNotExistsException("Unable to perform action"); //if book not found to remove
    }


 //check the availability of the book
    public void findAvailablity(String title,String author)
    {
        int i;
        boolean status=false;
        //checking if book with the name and author exists
      for( i=0;i<bookCount;i++)
      {
        if( books[i].title==title  &&  books[i].author==author )
        {
            status = true;
            break;
        }
      }
      if(status)   //if book is avialable
      {
        if(books[i].isBookAvailable(title,author)) 
        {
            System.out.println("Book is Available");
            
        }
        else    //if book is borrowed 
        {
            throw new BookNotAvailableException("Already Borrowed");
        }
      }
      else   //if book doesnot exists
      {
       
        throw new BookNotExistsException("Not available in our library");
      }
    }

    //find books with author names 
      public void findBook(String author)
    {
       boolean status = false;
        for(int i=0;i<bookCount;i++)
        {
            if(books[i].author==author)
            {
            System.out.println("Author : "+author+"| Book : "+books[i].title);
            System.out.println("Quantity: "+books[i].quantity + " | Available : "+books[i].availability);
            System.out.println();
            status = true;
            }
        }
        if(!status)
        {
            throw new BookNotExistsException("No book is available of this author");
        }
    }
    
    //find authors with book names 
      public void findAuthor(String title)
    {
       boolean status = false;
        for(int i=0;i<bookCount;i++)
        {
            if(books[i].title==title)
            {
            System.out.println("Book : "+title+"| Author : "+books[i].author);
            System.out.println("Quantity: "+books[i].quantity + " | Available : "+books[i].availability);
            System.out.println();
            status = true;
            }
        }
        if(!status)
        {
            throw new BookNotExistsException("No author is found with this book title");
        }
    }

//Display lists of all books from Library
    public void displayBooks()
    {
        System.out.println("Library Books : ");
        for(int i=0;i<bookCount;i++)
        {
            System.out.println("- "+books[i].title+" by "+books[i].author);
            System.out.println("Available Quantity: "+books[i].availability+" | Total Quantity : "+books[i].quantity);
            System.out.println();
        }
    }


}



public class LibraryManagementSystem
{
    public static void main(String[] args)
    {
       Library libraryObj = new Library(10);
       libraryObj.addBook("Hustle","Jack William",3);
       libraryObj.addBook("Blood and Bones","Thomas King",2);
       libraryObj.addBook("Deep Ocean","Orwell",3);
       libraryObj.addBook("Atom amd nucleus","Jack William",2);
       libraryObj.addBook("Deep Ocean","Orwell",5);
       libraryObj.findAvailablity("Blood and Bones","Thomas King");
       libraryObj.displayBooks();
    //    libraryObj.removeBook("Blood and Bones","Thomas King");
    //    libraryObj.displayBooks();
    //    libraryObj.findAvailablity("Deep Ocean","Orwell");
    //    libraryObj.findBook("Jack William");
    //    libraryObj.findAuthor("Hustle");
    }
}