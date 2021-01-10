//Arrays
int[] myIntArray = new int[3];
int[] myIntArray = {1,2,3};
int[] myIntArray = new int[]{1,2,3};

//sort array
Arrays.sort(myIntArray);

//Set remove duplicates on arrays
int end = arr.length;
Set<Integer> set = new HashSet<Integer>();
for(int i = 0; i < end; i++){ 
  set.add(arr[i]);
}


//Map
HashMap<String,Integer> hm=new HashMap<String,Integer>();

//Iterate map 
mapIterator<Map.Entry<String, String>> itr = gfg.entrySet().iterator();
while(itr.hasNext())         { 
  Map.Entry<String, String> entry = itr.next();
  System.out.println("Key = " + entry.getKey() +", Value = " + entry.getValue());      
 } 

//Sort map
 SortedMap map = new TreeMap(java.util.Collections.reverseOrder()); --> mapa que ordena de forma descendiente
 
 
 LinkedList<String> myLinkedList = new LinkedList<String>();

// Add a node with data="First" to back of the (empty) list
myLinkedList.add("First"); 
System.out.println(myLinkedList); 


@Override  public boolean equals(Object obj)   { 
if (obj == null)   return false;  
if (obj == this)  return true;  
return this.getRegno() == ((Employee) obj). getRegno(); 

}  ----public class Person implements Comparable<Person> {
    //...
 
    @Override
    public int compareTo(Person o) {
        return this.lastName.compareTo(o.lastName);
    }
}
The compareTo() method will return a negative int if called with a Person having a greater last name than this, zero if the same last name, and positive otherwise
 
