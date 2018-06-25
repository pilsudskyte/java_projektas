package lt.baltictalents.struct;

import java.util.Iterator;

public class EmptyCollection<T> extends AbstractCollection<T> implements Countable{

  @Override
  public Countable clone() throws CloneNotSupportedException{
    return new EmptyCollection<>();
  }

  @Override
  public Iterator iterator() {
    return null;
  }
}
