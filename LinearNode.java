package classroster;

public class LinearNode<T>
{
 private LinearNode<T> next;
 private T element;

 //---------------------------------------------------------
 //  Creates an empty node.
 //---------------------------------------------------------
 public LinearNode()
 {
    next = null;
    element = null;
 }

 //---------------------------------------------------------
 //  Creates a node storing the specified element.
 //---------------------------------------------------------
 public LinearNode (T elem)
 {
    next = null;
    element = elem;
 }

 //---------------------------------------------------------
 //  Returns the node that follows this one.
 //---------------------------------------------------------
 public LinearNode<T> getNext()
 {
    return next;
 }

 //---------------------------------------------------------
 //  Sets the node that follows this one.
 //---------------------------------------------------------
 public void setNext (LinearNode<T> node)
 {
    next = node;
 }

 //---------------------------------------------------------
 //  Returns the element stored in this node.
 //---------------------------------------------------------
 public T getElement()
 {
    return element;
 }

 //---------------------------------------------------------
 //  Sets the element stored in this node.
 //---------------------------------------------------------
 public void setElement (T elem)
 {
    element = elem;
 }
 public static void main(String[] args) {
	   LinearNode<Integer> node1 = new LinearNode<Integer>(1);
	   LinearNode<Integer> node2 = new LinearNode<Integer>(2);
	   LinearNode<Integer> node3 = new LinearNode<Integer>(3);
	   LinearNode<Integer> startPtr;
	   startPtr = node3;
	   node3.setNext(node2);
	   node2.setNext(node1);
	   node1 = null;
	   node2 = null;
	   node3 = null;
	   System.out.println(startPtr.getElement());
	   System.out.println(startPtr.getNext().getElement());
	   System.out.println(startPtr.getNext().getNext().getElement());
	   
	   LinearNode<Integer> tempPtr = startPtr;
	   while (tempPtr != null) {
		   System.out.print(tempPtr.getElement()+" ");
		   tempPtr = tempPtr.getNext();
	   }
	   System.out.println();
	   
 }
}
