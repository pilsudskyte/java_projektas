package lt.baltictalents.struct;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class SimpleArrayList<T> extends AbstractCollection<T> implements List<T> {


  protected int size = 0;
  protected Object[] items;

  /**
   * Sukuria tuščią sąrašą su maksimalia talpa initialSize;
   * @param initialSize
   */
  public SimpleArrayList(int initialSize) {
    items = new Object[initialSize];
  }

  /**
   * Sukuria pilnai užpildytą sąrašą iš masyvo
   * @param items elementai, kuriais užpildyti
   */
  public SimpleArrayList(T ... items) {
    items = items.clone();
  }

  /**
   * Sukuria naują šio sąrašo kopiją
   * @return
   */
  @Override
  public SimpleArrayList<T> clone() throws CloneNotSupportedException {
    SimpleArrayList<T> newList = new SimpleArrayList<>(this.size);
    newList.items = this.items.clone();
    return newList;
  }

  @Override
  public String toString() {
    Object[] elementųMasyvas = this.items;
    String masyvoTekstas = Arrays.toString(this.items);

    return masyvoTekstas;
  }

  /**
   * Patikrina, kad yra vietos sutalpinti tiek elementų.
   * Šioje paprasčiausioje realizacijoje tik nulūžta, jei nėra.
   * @param size
   * @throws IndexOutOfBoundsException jei nėra vietos.
   */
  protected void ensureSize(int size) {
    if (size > items.length) {
      throw new ArrayIndexOutOfBoundsException("Sąrašas perpildytas!!!");
    }
  }

  /**
   * Prideda elementą į sąrašo pabaigą.
   * Jei sąrašas perpildytas, generuojama {@link ArrayIndexOutOfBoundsException}
   *
   * @param obj
   * @throws  {@link IndexOutOfBoundsException} jei sąrašas jau maksimaliai užpildytas
   */
  public void add(T obj) {
    ensureSize(this.size+1);

    items[size] = obj;
    size++;

    version++;
  }

  /**
   * Prideda daug elementų į sąrašo pabaigą.
   * @param items
   * @throws ArrayIndexOutOfBoundsException jei sąrašas perpildytas.
   */
  public final void add(T... items) {
    ensureSize(this.size + items.length);

    for (T item : items) {
      this.add(item);
    }
  }

  /**
   * Šito reikia tam, kad galėčiau kolekciją naudoti for( : ) išraiškoje.
   *
   * @return
   */
  @Override
  public Iterator iterator() {
    throw new UnsupportedOperationException("TODO kada nors ateityje");
  }

  /**
   * grąžina surikiuotą savo paties kopiją
   * @return
   */
  @Override
  public SimpleArrayList<T> sort() {
    Object[] items=this.items.clone();
    Arrays.sort(items);
    return new SimpleArrayList(items);
  }


  public static void main(String... args){

    SimpleArrayList<String> l = new SimpleArrayList<String>(2);  // paprasčiausiam masyvu paremtam sąrašui mes privalom nurodyti dydį ir jo neviršyti
    l.add("xxx", "yyy");                                    // todėl turime įsitikinti, kad išsiskyrėme pakankamai daug vietos iš anksto ir įvesti saugiklius perpildymui
    // l.add("zzz")         // <---- čia jau būtų perpildymo klaida

    SimpleArrayList<String> l2 = new GrowableArrayList<String>(); // augantis sąrašas pasirūpina, kad visuomet būtų paruoštas pakankamai didelis masyvas
    l2.add("xxx","yyy");  // bet mes negalime patikimai prognozuoti, kada bus išprovokuotas pilnas masyvo perkopijavimas į didesnį.
    l2.add("zzz");        // todėl: a) programa gali užstrigti ilgam ir netinkamu laiku, b) kopijavimo metu prarasime KAI KURIUOS pakeitimus, kuriuos per tą laiką
                          // atliks kitos gijos, pvz. naujus elementus

    System.out.println( l2.toString() );
  }

}
 class Massage {
  Date date;
  String text;
  User sender;
 }

 class User {
  String name;
  int ipAddress;
 }


class GrowableArrayList<T> extends SimpleArrayList<T> {

  {
    if(this.items==null) {
      this.items=new Object[1];
    }
  }

  /**
   * Vietoj to, kad nulūžtų, pakeičia vidinį masyvą bent dvigubai didesniu ir toliau add() metodai veikia be lūžimų.
   *
   * @param size – pageidaujamas dydis.
   */
  @Override
  public void ensureSize(int size){
    if(size<0){
      throw new IllegalArgumentException("Array size cannot be negative: "+size);
    }

    if(this.items==null) {
      this.items=new Object[size];
      return;
    }

    if( size>this.items.length*2 ) {
      this.items = Arrays.copyOf(this.items,size);
    }else if(size>this.items.length) {
      this.items = Arrays.copyOf(this.items,this.items.length*2);
    }
  }

}