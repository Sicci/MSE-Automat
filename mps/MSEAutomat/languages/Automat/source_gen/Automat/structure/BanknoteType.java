package Automat.structure;

/*Generated by MPS */

import java.util.List;
import jetbrains.mps.internal.collections.runtime.ListSequence;
import jetbrains.mps.internal.collections.runtime.backports.LinkedList;

public enum BanknoteType {
  _5("5", "5"),
  _10("10", "10"),
  _20("20", "20");

  private String myName;

  public String getName() {
    return this.myName;
  }

  public String getValueAsString() {
    return this.myValue;
  }

  public static List<BanknoteType> getConstants() {
    List<BanknoteType> list = ListSequence.fromList(new LinkedList<BanknoteType>());
    ListSequence.fromList(list).addElement(BanknoteType._5);
    ListSequence.fromList(list).addElement(BanknoteType._10);
    ListSequence.fromList(list).addElement(BanknoteType._20);
    return list;
  }

  public static BanknoteType getDefault() {
    return null;
  }

  public static BanknoteType parseValue(String value) {
    if (value == null) {
      return BanknoteType.getDefault();
    }
    if (value.equals(BanknoteType._5.getValueAsString())) {
      return BanknoteType._5;
    }
    if (value.equals(BanknoteType._10.getValueAsString())) {
      return BanknoteType._10;
    }
    if (value.equals(BanknoteType._20.getValueAsString())) {
      return BanknoteType._20;
    }
    return BanknoteType.getDefault();
  }

  private String myValue;

  BanknoteType(String name, String value) {
    this.myName = name;
    this.myValue = value;
  }

  public String getValue() {
    return this.myValue;
  }
}
