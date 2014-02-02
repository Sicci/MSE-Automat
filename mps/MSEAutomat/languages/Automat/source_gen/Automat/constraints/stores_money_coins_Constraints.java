package Automat.constraints;

/*Generated by MPS */

import jetbrains.mps.smodel.runtime.base.BaseConstraintsDescriptor;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.mps.openapi.model.SNode;
import jetbrains.mps.smodel.IOperationContext;
import jetbrains.mps.smodel.runtime.CheckingNodeContext;
import jetbrains.mps.internal.collections.runtime.ListSequence;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SNodeOperations;
import jetbrains.mps.internal.collections.runtime.IWhereFilter;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SPropertyOperations;
import jetbrains.mps.smodel.SNodePointer;

public class stores_money_coins_Constraints extends BaseConstraintsDescriptor {
  public stores_money_coins_Constraints() {
    super("Automat.structure.stores_money_coins");
  }

  @Override
  public boolean hasOwnCanBeChildMethod() {
    return true;
  }

  @Override
  public boolean canBeChild(@Nullable SNode node, SNode parentNode, SNode link, SNode childConcept, final IOperationContext operationContext, @Nullable final CheckingNodeContext checkingNodeContext) {
    boolean result = static_canBeAChild(node, parentNode, link, childConcept, operationContext);

    if (!(result) && checkingNodeContext != null) {
      checkingNodeContext.setBreakingNode(canBeChildBreakingPoint);
    }

    return result;
  }

  public static boolean static_canBeAChild(final SNode node, SNode parentNode, SNode link, SNode childConcept, final IOperationContext operationContext) {
    return ListSequence.fromList(SNodeOperations.getDescendants(parentNode, "Automat.structure.stores_money_coins", false, new String[]{})).where(new IWhereFilter<SNode>() {
      public boolean accept(SNode it) {
        return SPropertyOperations.getInteger(it, "coinType") == SPropertyOperations.getInteger(node, "coinType");
      }
    }).count() <= 1;
  }

  private static SNodePointer canBeChildBreakingPoint = new SNodePointer("r:d6654046-e3fe-4d62-9060-4210d958514f(Automat.constraints)", "6616384595875180984");
}