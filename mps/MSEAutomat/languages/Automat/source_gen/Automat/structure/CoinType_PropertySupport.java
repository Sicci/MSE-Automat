package Automat.structure;

/*Generated by MPS */

import jetbrains.mps.smodel.PropertySupport;
import java.util.Iterator;
import jetbrains.mps.internal.collections.runtime.ListSequence;

public class CoinType_PropertySupport extends PropertySupport {
  public boolean canSetValue(String value) {
    if (value == null) {
      return true;
    }
    Iterator<CoinType> constants = ListSequence.fromList(CoinType.getConstants()).iterator();
    while (constants.hasNext()) {
      CoinType constant = constants.next();
      if (value.equals(constant.getName())) {
        return true;
      }
    }
    return false;
  }

  public String toInternalValue(String value) {
    if (value == null) {
      return null;
    }
    Iterator<CoinType> constants = ListSequence.fromList(CoinType.getConstants()).iterator();
    while (constants.hasNext()) {
      CoinType constant = constants.next();
      if (value.equals(constant.getName())) {
        return constant.getValueAsString();
      }
    }
    return null;
  }

  public String fromInternalValue(String value) {
    CoinType constant = CoinType.parseValue(value);
    if (constant != null) {
      return constant.getName();
    }
    return "";
  }
}
