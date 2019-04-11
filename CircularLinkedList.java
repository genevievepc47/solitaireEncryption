import java.util.Iterator;
import java.util.Scanner;
import java.util.*;


public class CircularLinkedList<E> implements Iterable<E> {



    // Your variables
    Node<E> head;
    Node<E> tail;
    int size;  // BE SURE TO KEEP TRACK OF THE SIZE


    // implement this constructor 1

    public CircularLinkedList() {
        head = null;
        size = 0;
        tail = null;

    }


    // I highly recommend using this helper method
    // Return Node<E> found at the specified index
    // be sure to handle out of bounds cases
    private Node<E> getNode(int index ) {
        if (index < 0 || index > size)//O(1)
        {
            throw new IndexOutOfBoundsException("Not a valid index");

        }
        else {

            Node<E> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            return current;
        }
    }//end get node


    //return what index we found a node at
    private int getIndex(E num)
    {
       Node current = head;
            int i  = 0;
            while(current != null)
            {
                if(current.item == num)
                {
                    return i;
                }
                 current = current.next;
                i++;
            }


        return -1;

    }//end get index


    // attach a node to the end of the list
    public boolean add(E item) {
        this.add(size,item);
        return true;

    }


    // Cases to handle
    // out of bounds 1
    // adding to empty list 2
    // adding to front 3
    // adding to "end" 4
    // adding anywhere else 5
    // REMEMBER TO INCREMENT THE SIZE 1
    public void add(int index, E item){

        //scenario 1: index is out of bounds
        if (index < 0 || index > size)//O(1)
        {
            throw new IndexOutOfBoundsException("Not a valid index");

        }

        Node<E> temp = new Node<E>(item);
        //scenario 2: adding to empty list
        if(size ==0)
        {

            this.head = temp;
            this.tail = temp;
        }

        //scenario 3: adding to the front
        else if(index ==0)//O(1)
        {

            temp.next = head;
            head = temp;
        }



        //scenario 4: adding to the tail
        else if(index == size)
        {

            tail.next = temp;
            tail = temp;
            temp.next = head;
        }

        //scenario 5: anywhere else
        else//O(n)
        {

            Node<E> before = this.getNode(index-1);
            temp.next = before.next;
            before.next = temp;

        }


        size++;
    }





    // remove must handle the following cases
    // out of bounds 1
    // removing the only thing in the list 2
    // removing the first thing in the list (need to adjust the last thing in the list to point to the beginning) 3
    // removing the last thing 4
    // removing any other node 5
    // REMEMBER TO DECREMENT THE SIZE
    public E remove(int index)
    {
        E toReturn = null;
        //scenario 1: out of bounds
        if(index <0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Can't remove that");
        }

        //scenario 2: removing the only thing in the list
        if(size ==1)
        {
            toReturn = head.item;
            head = null;
            tail = null;
        }

        //scenario 3: removing the first thing in the list
        else if(index ==0)
        {
            toReturn = head.item;
            head = head.next;
            tail.next = head;
        }


        //scenario 4: removing the last thing
        else if(index == size-1)
        {
            Node<E> before = getNode(index-1);

            toReturn = tail.item;
            tail = before;
            before.next = head;

        }

        //scenario 5: removing any other node
        else
        {
            Node<E> before = getNode(index-1);

            toReturn = before.next.item;
            before.next = before.next.next;

        }
        size--;


        return toReturn;
    }




    // Turns your list into a string
    // Useful for debugging
    public String toString(){
        Node<E> current =  head;
        StringBuilder result = new StringBuilder();
        if(size == 0){
            return "";
        }
        if(size == 1) {
            return head.item.toString();

        }
        else{
            do{

                result.append(current.item);
                result.append(" ==> ");
                current = current.next;
            } while(current != head);
        }
        return result.toString();
    }


    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }

    // provided code for different assignment
    // you should not have to change this
    // change at your own risk!
    // this class is not static because it needs the class it's inside of to survive!
    private class ListIterator<E> implements Iterator<E>{

        Node<E> nextItem;
        Node<E> prev;
        int index;

        @SuppressWarnings("unchecked")
        //Creates a new iterator that starts at the head of the list
        public ListIterator(){
            nextItem = (Node<E>) head;
            index = 0;
        }

        // returns true if there is a next node
        // this is always should return true if the list has something in it
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return size != 0;
        }

        // advances the iterator to the next item
        // handles wrapping around back to the head automatically for you
        public E next() {
            // TODO Auto-generated method stub
            prev =  nextItem;
            nextItem = nextItem.next;
            index =  (index + 1) % size;
            return prev.item;

        }

        // removed the last node was visted by the .next() call
        // for example if we had just created a iterator
        // the following calls would remove the item at index 1 (the second person in the ring)
        // next() next() remove()
        public void remove() {
            int target;
            if(nextItem == head) {
                target = size - 1;
            } else{
                target = index - 1;
                index--;
            }
            CircularLinkedList.this.remove(target); //calls the above class
        }

    }//end iterator class

    // It's easiest if you keep it a singly linked list
    // SO DON'T CHANGE IT UNLESS YOU WANT TO MAKE IT HARDER
    private static class Node<E>{
        E item;
        Node<E> next;

        public Node(E item) {
            this.item = item;
        }

    }//end node class

    public static ArrayList<Integer> makeKeyStrings(int numberOfKeysNeeded, CircularLinkedList<Integer> deckList, ArrayList<Integer> keyStrings)//change back to returning int[] later
    {

        while(keyStrings.size() < numberOfKeysNeeded ) {





            //STEP 1, Swap 27 with the value following it

            //find 27
            int locationOfTwentySeven = deckList.getIndex(27);

            //remove the card behind 27
            if(deckList.tail.item == 27)
            {
                //make 27 new head, make head new tail
               int firstItem = deckList.remove(0);
               int lastItem = deckList.remove(deckList.size-1);

               deckList.add(0,lastItem);
               deckList.add(deckList.size, firstItem);

            }
            else
            {
                int numBehindTwentySeven = deckList.remove(locationOfTwentySeven + 1);

                //add it in front of 27
                deckList.add(locationOfTwentySeven, numBehindTwentySeven);
            }



            //STEP 2, move 28 two places down the list
            int locationOfTwentyEight = deckList.getIndex(28);//find 28

            if(locationOfTwentyEight == 27)//if it is the last item in the list
            {
                deckList.remove(27);
              deckList.add(1,28);
            }
            else if(locationOfTwentyEight == 26)//second to last item
            {
                deckList.remove(26);
                deckList.add(0,28);
            }
            else
            {
                //find 28, remove it, add it two places down

                deckList.remove(locationOfTwentyEight);//remove 28
                deckList.add(locationOfTwentyEight + 2, 28);//move it two places down the list

            }

            //STEP 3 do the triple cut.
            // everything above the first joker(which ever one is in front), goes to the bottom of the deck
            //and everything below the second joker goes to the top

            //compare 28 location and 27 location, find out which one is first
            //remove all elements above first joker and put them in seperate list
            //remove all elements below second joker and put them in a seperate list
            //put the first list back at the end, and the second list at the begining

            locationOfTwentyEight = deckList.getIndex(28);//find 28
            locationOfTwentySeven = deckList.getIndex(27);//find 27

            int locationOfFirstJoker = Math.min(locationOfTwentyEight, locationOfTwentySeven);//get the min
            int locationOfSecondJoker = Math.max(locationOfTwentyEight, locationOfTwentySeven);//get the max

            CircularLinkedList<Integer> frontThird = new CircularLinkedList<Integer>();
            CircularLinkedList<Integer> lastThird = new CircularLinkedList<Integer>();

            //add stuff to new lists
            for (int j = locationOfSecondJoker + 1; j < deckList.size; j++)//loop through the last third of the list
            {
                lastThird.add(deckList.getNode(j).item);

            }

            for (int i = 0; i < locationOfFirstJoker; i++)//loop through first third of list
            {
                frontThird.add(deckList.getNode(i).item);
            }

            //remove it from deck list
            for (int k = deckList.size - 1; k > locationOfSecondJoker; k--)//loop through the last third of the list
            {
                deckList.remove(k);

            }

            for (int l = locationOfFirstJoker - 1; l >= 0; l--)//loop through first third of list
            {
                deckList.remove(l);
            }


            //add the back third to the front
            for (int w = 0; w < lastThird.size; w++) {
                deckList.add(w, lastThird.getNode(w).item);
            }

            //add the first third to the back
            for (int n = 0; n < frontThird.size; n++)//loop through first third
            {
                deckList.add(frontThird.getNode(n).item);
            }

            //STEP 4, look at bottom card, Count down from the top card by
            //a quantity of cards equal to the value of that bottom card.
            //(If the bottom card is a joker, let its value be 27
            //Take that group of cards and move them to the bottom of the deck
            //Return the bottom card to the bottom of the deck

            int bottomCard = deckList.tail.item;//look at bottom card

            if (bottomCard == 28)//if it is a joker
            {
                bottomCard = 27;//make it this
            }

            CircularLinkedList<Integer> frontList4 = new CircularLinkedList<Integer>();
            for (int r = 0; r < bottomCard; r++)//loop through the first x top cards where x is the number of the bottom card
            {
                frontList4.add(deckList.getNode(r).item);
            }

            for (int g = bottomCard - 1; g >= 0; g--)//loop through backwards and delete from decklist
            {
                deckList.remove(g);
            }

            for (int s = 0; s < frontList4.size; s++)//loop through the front list and add it to the end
            {
                deckList.add(deckList.size - 1, frontList4.getNode(s).item);//add it to the end
            }

            //STEP 5, look at top card value
            //count down that any cards
            //thats they key you want to add, but if its a joker start over
            int topCard = deckList.head.item;

            if (topCard == 28)//if it is a joker, make it 27
            {
                topCard = 27;
            }

            int keyToAdd = (deckList.getNode(topCard).item);

            if (keyToAdd > 26)//if its a joker, do again
            {
                makeKeyStrings(numberOfKeysNeeded, deckList, keyStrings);

            } else {
                keyStrings.add(keyToAdd);
                makeKeyStrings(numberOfKeysNeeded, deckList, keyStrings);
            }


        }//end while we need to keep making keystrings



        return keyStrings;
    }//end make key string method

    private static String encrypt(String messageToEncrypt, CircularLinkedList<Integer> deckList)
    {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        String StringtoReturn = "";



        //get rid of all spaces and punctuation from string
        messageToEncrypt = messageToEncrypt.replaceAll("[^A-Za-z0-9]", "");
        messageToEncrypt = messageToEncrypt.toUpperCase();

        if(messageToEncrypt.length()%(5) > 0)//if it is not divisible by 5
        {
            for(int i = 0; i < messageToEncrypt.length()%(5); i++)//loop as many times as we need to add an x
            {
                messageToEncrypt = messageToEncrypt + "X";
            }

        }



        //loop through string convert to a num and add to array list of nums
        for(int s =0; s < messageToEncrypt.length(); s++)//loop through all letters on message
        {
            char ch = messageToEncrypt.charAt(s);

            int n = (int)ch - (int)'A';
            nums.add(n);
        }

        //make keystrings
        ArrayList<Integer> keyStrings=new ArrayList<Integer>();
        keyStrings = makeKeyStrings(messageToEncrypt.length(),deckList, keyStrings);

        //do operation on the nums
        //loop though nums adding the keystream to it, if greater than 26, subtract 26
        for(int j = 0; j < nums.size(); j++ )
        {
            int newNum = (nums.get(j) + keyStrings.get(j)) % 26;
            //if(newNum >26)
            //{
            //    newNum = newNum - 26;
            //}

            nums.set(j, newNum);
        }

        //convert back to letters
        for(int k = 0; k <nums.size(); k++)//loop through all nums
        {
            String let = String.valueOf((char)(nums.get(k) + 'A'));
            StringtoReturn = StringtoReturn + let;
        }



        return StringtoReturn;

    }//end encrypt method

    public static String decrypt(String messageToDecrypt, CircularLinkedList<Integer> deckList)
    {
        String stringToReturn = "";
        ArrayList<Integer> nums = new ArrayList<Integer>();

        //convert it to nums
        for(int i = 0; i < messageToDecrypt.length(); i ++)//loop through encrypted message
        {
            char ch = messageToDecrypt.charAt(i);
            int n = ch - 'A';
            nums.add((n));
        }

        //make keystrings
        ArrayList<Integer> keyStrings=new ArrayList<Integer>();
        keyStrings = makeKeyStrings(messageToDecrypt.length(),deckList, keyStrings);

        //do operation on nums with keystreams
        for(int j = 0; j< nums.size(); j++)//loop through nums
        {
            if(nums.get(j) < keyStrings.get(j))
            {
                nums.set(j,nums.get(j) + 26);
            }
            int newNum = (nums.get(j) - keyStrings.get(j)) %26;
            nums.set(j,newNum);

        }
        //convert to letters
        //convert back to letters
        for(int k = 0; k <nums.size(); k++)//loop through all nums
        {
            String let = String.valueOf((char)(nums.get(k)+'A'));
            stringToReturn = stringToReturn + let;
        }


        return stringToReturn;
    }//end decrypt method

    public static void main(String[] args)
    {
        int[] deck = new int[]{ 4, 1, 7, 10, 13, 16, 19, 22, 25, 28, 3, 6, 9, 12, 15, 18, 21, 24, 27, 2, 5, 8, 11, 14, 17, 20, 23, 26};

        //make a linked list and put the deck in it
        CircularLinkedList<Integer> deckList = new CircularLinkedList<Integer>();
        for (int i = 0; i < deck.length; i++) {
            deckList.add(i, deck[i]);
        }


        //ask them to enter a message to encrypt
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter a message to encrypt: ");
        String messageToEncrypt = scanner.nextLine();



        String messageToDecrypt = encrypt(messageToEncrypt,deckList);
        System.out.println("Encrypted to: " + messageToDecrypt);


        //ask them to enter the encrypted message
        ////System.out.println("Enter a message to decrypt: ");
        //String messageToDecrypt = scanner.nextLine();
        //System.out.println(deckList);

        deckList = new CircularLinkedList<Integer>();
        for (int i = 0; i < deck.length; i++) {
            deckList.add(i, deck[i]);
        }
        //System.out.println(deckList);
        System.out.println("Decrytped to: " + decrypt(messageToDecrypt,deckList ));
       // System.out.println(deckList);

        //make keystrings from deck so we can decrypt
        //ArrayList<Integer> keyStrings=new ArrayList<Integer>();


        //keyStrings = makeKeyStrings(messageToDecrypt.length(),deckList, keyStrings);

    }





}//end circular linked list class
