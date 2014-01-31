package Automat.structure;

/*Generated by MPS */

import jetbrains.mps.smodel.runtime.ConceptDescriptor;
import java.util.Arrays;
import jetbrains.mps.smodel.runtime.impl.ConceptDescriptorBuilder;
import jetbrains.mps.smodel.runtime.interpreted.StructureAspectInterpreted;

public class StructureAspectDescriptor implements jetbrains.mps.smodel.runtime.StructureAspectDescriptor {
  public StructureAspectDescriptor() {
  }

  public ConceptDescriptor getDescriptor(String conceptFqName) {
    switch (Arrays.binarySearch(stringSwitchCases_1htk8d_a0a0b, conceptFqName)) {
      case 0:
        return new ConceptDescriptorBuilder("Automat.structure.Automat").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept", "jetbrains.mps.lang.core.structure.INamedConcept", "jetbrains.mps.execution.util.structure.IMainClass").children(new String[]{"_has", "_accepts", "_stores"}, new boolean[]{false, false, false}).create();
      case 1:
        return new ConceptDescriptorBuilder("Automat.structure.accepts").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").children(new String[]{"_accepts_creditcard"}, new boolean[]{true}).create();
      case 2:
        return new ConceptDescriptorBuilder("Automat.structure.accepts_banknotes").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").children(new String[]{"value"}, new boolean[]{true}).create();
      case 3:
        return new ConceptDescriptorBuilder("Automat.structure.accepts_coins").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").children(new String[]{"value"}, new boolean[]{true}).create();
      case 4:
        return new ConceptDescriptorBuilder("Automat.structure.accepts_creditcard").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").properties("cardtype").create();
      case 5:
        return new ConceptDescriptorBuilder("Automat.structure.has").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").properties("type", "language", "currency", "color_r", "color_g", "color_b").create();
      case 6:
        return new ConceptDescriptorBuilder("Automat.structure.stores").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").children(new String[]{"money", "items"}, new boolean[]{false, false}).create();
      case 7:
        return new ConceptDescriptorBuilder("Automat.structure.stores_items").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").children(new String[]{"items_item"}, new boolean[]{true}).create();
      case 8:
        return new ConceptDescriptorBuilder("Automat.structure.stores_items_item").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").properties("numcode", "name", "price", "quantity").create();
      case 9:
        return new ConceptDescriptorBuilder("Automat.structure.stores_money").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").children(new String[]{"money_coins", "money_banknotes"}, new boolean[]{true, true}).create();
      case 10:
        return new ConceptDescriptorBuilder("Automat.structure.stores_money_banknotes").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").properties("banknoteType", "quantity").create();
      case 11:
        return new ConceptDescriptorBuilder("Automat.structure.stores_money_coins").super_("jetbrains.mps.lang.core.structure.BaseConcept").parents("jetbrains.mps.lang.core.structure.BaseConcept").properties("coinType", "quantity").create();
      default:
        return StructureAspectInterpreted.getInstance().getDescriptor(conceptFqName);
    }
  }

  private static String[] stringSwitchCases_1htk8d_a0a0b = new String[]{"Automat.structure.Automat", "Automat.structure.accepts", "Automat.structure.accepts_banknotes", "Automat.structure.accepts_coins", "Automat.structure.accepts_creditcard", "Automat.structure.has", "Automat.structure.stores", "Automat.structure.stores_items", "Automat.structure.stores_items_item", "Automat.structure.stores_money", "Automat.structure.stores_money_banknotes", "Automat.structure.stores_money_coins"};
}
