package classroster;

import java.util.*;

public class LinkedSet<T> implements SetADT<T>
{
   private static Random rand = new Random();

   private int count;  // the current number of elements in the set 

   private LinearNode<T> contents; 

   //-----------------------------------------------------------------
   //  Creates an empty set.
   //-----------------------------------------------------------------
   public LinkedSet()
   {
      count = 0;
      contents = null;
   }

   //-----------------------------------------------------------------
   //  Adds the specified element to the set if it's not already
   //  present.
   //-----------------------------------------------------------------
   public void add (T element)
   {
      if (!(contains(element)))
      {
         LinearNode<T> node = new LinearNode<T> (element);
         node.setNext(contents);
         contents = node;
         count++;
      }
   }

   //-----------------------------------------------------------------
   //  Adds the contents of the parameter to this set.
   //-----------------------------------------------------------------
   public void addAll (SetADT<T> set)
   {
      Iterator<T> scan = set.iterator();

      while (scan.hasNext())
         add (scan.next());
   }

   //-----------------------------------------------------------------
   //  Removes a random element from the set and returns it. Throws
   //  an EmptySetException if the set is empty.
   //-----------------------------------------------------------------
   public T removeRandom() throws Exception
   {
      LinearNode<T> previous, current;
      T result = null;

      if (isEmpty())
         throw new Exception();

      int choice = rand.nextInt(count) + 1;

      if (choice == 1)
      {
         result = contents.getElement();
         contents = contents.getNext();
      }
      else
      {
         previous = contents;
         for (int skip=2; skip < choice; skip++)
            previous = previous.getNext();
         current = previous.getNext();
         result = current.getElement();
         previous.setNext(current.getNext());
      }
      
      count--;
 
      return result;
   }

   //-----------------------------------------------------------------
   //  Removes the specified element from the set and returns it.
   //  Throws an EmptySetException if the set is empty and a
   //  NoSuchElemetnException if the target is not in the set.
   //-----------------------------------------------------------------
   public T remove (T target) throws Exception
   {
      boolean found = false;
      LinearNode<T> previous, current;
      T result = null;

      if (isEmpty())
         throw new Exception();

      if (contents.getElement().equals(target))
      {
         result = contents.getElement();
         contents = contents.getNext();
      }
      else
      {
         previous = contents;
         current = contents.getNext();
         for (int look=0; look < count && !found; look++) {
            if (current.getElement().equals(target))
               found = true;
            else
            {
               previous = current;
               current = current.getNext();
            }
         }
         if (!found)
            throw new NoSuchElementException();

         result = current.getElement();
         previous.setNext(current.getNext());
      }
         
      count--;
 
      return result;
   }
   
   //-----------------------------------------------------------------
   //  Returns a new set that is the union of this set and the
   //  parameter.
   //-----------------------------------------------------------------
   public LinkedSet<T> union (SetADT<T> set)
   {
      LinkedSet<T> both = new LinkedSet<T>();

      LinearNode<T> current = contents;

      while (current != null)
      {
         both.add (current.getElement());
         current = current.getNext();
      }

      Iterator<T> scan = set.iterator();
      while (scan.hasNext())
         both.add (scan.next());

      return both;
   }

   //-----------------------------------------------------------------
   //  Returns true if this set contains the specified target
   //  element.
   //-----------------------------------------------------------------
   public boolean contains (T target)
   {
      boolean found = false;

      LinearNode<T> current = contents;

      for (int look=0; look < count && !found; look++)
         if (current.getElement().equals(target))
            found = true;
         else
	    current = current.getNext();

      return found;
   }

   //-----------------------------------------------------------------
   //  Returns true if this set contains exactly the same elements
   //  as the parameter.
   //-----------------------------------------------------------------
   public boolean equals (SetADT<T> set) throws Exception
   {
      boolean result = false;
      LinkedSet<T> temp1 = new LinkedSet<T>();
      LinkedSet<T> temp2 = new LinkedSet<T>();
      T obj;

      if (size() == set.size())
      { 
         temp1.addAll(this);
         temp2.addAll(set);

         Iterator<T> scan = set.iterator();

         while (scan.hasNext())
         {
            obj = scan.next();   
            if (temp1.contains(obj))
            {
               temp1.remove(obj);
               temp2.remove(obj);
            }
  
         }
         result = (temp1.isEmpty() && temp2.isEmpty());
      }

      return result;
   }

   //-----------------------------------------------------------------
   //  Returns true if this set is empty and false otherwise. 
   //-----------------------------------------------------------------
   public boolean isEmpty()
   {
      return (size() == 0);
   }
 
   //-----------------------------------------------------------------
   //  Returns the number of elements currently in this set.
   //-----------------------------------------------------------------
   public int size()
   {
      return count;
   }

   //-----------------------------------------------------------------
   //  Returns an iterator for the elements currently in this set.
   //-----------------------------------------------------------------
   public Iterator<T> iterator()
   {
      return new LinkedIterator<T> (contents);
   }

   //-----------------------------------------------------------------
   //  Returns a string representation of this set. 
   //-----------------------------------------------------------------
   public String toString()
   {
      String result = "";

      LinearNode<T> current = contents;

      while (current != null)
      {
         result += current.getElement().toString() + "\n";
         current = current.getNext();
      }

      return result;
   }
}