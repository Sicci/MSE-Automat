<?xml version="1.0" encoding="UTF-8"?>
<model modelUID="r:5facbd70-7c58-4793-8997-e2774e11000d(Automat.sandbox)">
  <persistence version="8" />
  <language namespace="90dfac8b-2e14-49b1-8f27-124b46f03641(Automat)" />
  <language namespace="ceab5195-25ea-4f22-9b92-103b95ca8c0c(jetbrains.mps.lang.core)" />
  <language namespace="f3061a53-9226-4cc5-a443-f952ceaf5816(jetbrains.mps.baseLanguage)" />
  <import index="tyhk" modelUID="r:5f1d2d0f-b054-43a7-9bb2-e26d154a0e1d(Automat.structure)" version="2" implicit="yes" />
  <import index="tpck" modelUID="r:00000000-0000-4000-0000-011c89590288(jetbrains.mps.lang.core.structure)" version="0" implicit="yes" />
  <root type="tyhk.Automat" typeId="tyhk.8785849198913167871" id="3092453947968759268" nodeInfo="ng">
    <property name="name" nameId="tpck.1169194664001" value="Mr.Coffee" />
    <node role="_has" roleId="tyhk.8785849198913471863" type="tyhk.has" typeId="tyhk.8785849198913471264" id="7827320562854880030" nodeInfo="ng">
      <property name="color_r" nameId="tyhk.5708215893054770772" value="1" />
      <property name="color_b" nameId="tyhk.5708215893054788897" value="158" />
      <property name="currency" nameId="tyhk.8785849198913471332" value="$" />
      <property name="language" nameId="tyhk.8785849198913471347" value="en" />
      <property name="color_g" nameId="tyhk.5708215893054788891" value="90" />
      <property name="type" nameId="tyhk.8785849198913471330" value="coffee" />
    </node>
    <node role="_stores" roleId="tyhk.8785849198914852068" type="tyhk.stores" typeId="tyhk.8785849198914793268" id="6119712891003197363" nodeInfo="ng">
      <node role="money" roleId="tyhk.8785849198914852344" type="tyhk.stores_money" typeId="tyhk.8785849198914793385" id="6119712891003197365" nodeInfo="ng">
        <node role="money_banknotes" roleId="tyhk.8785849198915125174" type="tyhk.stores_money_banknotes" typeId="tyhk.8785849198915124652" id="166346444446847600" nodeInfo="ng">
          <property name="banknoteType" nameId="tyhk.8785849198915124723" value="100" />
          <property name="quantity" nameId="tyhk.8785849198915124769" value="200" />
        </node>
        <node role="money_coins" roleId="tyhk.8785849198914793877" type="tyhk.stores_money_coins" typeId="tyhk.8785849198914793739" id="6119712891003197367" nodeInfo="ng">
          <property name="coinType" nameId="tyhk.8785849198914793952" value="10" />
          <property name="quantity" nameId="tyhk.8785849198914794167" value="100" />
        </node>
        <node role="money_coins" roleId="tyhk.8785849198914793877" type="tyhk.stores_money_coins" typeId="tyhk.8785849198914793739" id="6119712891003213741" nodeInfo="ng">
          <property name="coinType" nameId="tyhk.8785849198914793952" value="20" />
          <property name="quantity" nameId="tyhk.8785849198914794167" value="200" />
        </node>
        <node role="money_coins" roleId="tyhk.8785849198914793877" type="tyhk.stores_money_coins" typeId="tyhk.8785849198914793739" id="6119712891003213744" nodeInfo="ng">
          <property name="coinType" nameId="tyhk.8785849198914793952" value="50" />
          <property name="quantity" nameId="tyhk.8785849198914794167" value="300" />
        </node>
        <node role="money_coins" roleId="tyhk.8785849198914793877" type="tyhk.stores_money_coins" typeId="tyhk.8785849198914793739" id="6119712891007188116" nodeInfo="ng">
          <property name="coinType" nameId="tyhk.8785849198914793952" value="100" />
          <property name="quantity" nameId="tyhk.8785849198914794167" value="400" />
        </node>
      </node>
      <node role="items" roleId="tyhk.3092453947967876817" type="tyhk.stores_items" typeId="tyhk.4931417553779057712" id="6119712891006381157" nodeInfo="ng">
        <node role="items_item" roleId="tyhk.3092453947967876413" type="tyhk.stores_items_item" typeId="tyhk.3092453947967827984" id="6119712891006381159" nodeInfo="ng">
          <property name="numcode" nameId="tyhk.3092453947967856603" value="1" />
          <property name="name" nameId="tyhk.3092453947967856605" value="SuperLatte" />
          <property name="price" nameId="tyhk.3092453947967856608" value="120" />
          <property name="quantity" nameId="tyhk.3092453947967856612" value="100" />
        </node>
        <node role="items_item" roleId="tyhk.3092453947967876413" type="tyhk.stores_items_item" typeId="tyhk.3092453947967827984" id="6119712891006381161" nodeInfo="ng">
          <property name="numcode" nameId="tyhk.3092453947967856603" value="201" />
          <property name="name" nameId="tyhk.3092453947967856605" value="SuperChino" />
          <property name="price" nameId="tyhk.3092453947967856608" value="200" />
          <property name="quantity" nameId="tyhk.3092453947967856612" value="50" />
        </node>
      </node>
    </node>
    <node role="_accepts" roleId="tyhk.8785849198914049522" type="tyhk.accepts" typeId="tyhk.8785849198913843381" id="166346444446666967" nodeInfo="ng">
      <node role="_accepts_creditcard" roleId="tyhk.8785849198914641038" type="tyhk.accepts_creditcard" typeId="tyhk.8785849198914640857" id="166346444446666968" nodeInfo="ng">
        <property name="cardtype" nameId="tyhk.166346444445062816" value="VISA" />
      </node>
      <node role="_accepts_creditcard" roleId="tyhk.8785849198914641038" type="tyhk.accepts_creditcard" typeId="tyhk.8785849198914640857" id="166346444446666975" nodeInfo="ng">
        <property name="cardtype" nameId="tyhk.166346444445062816" value="MasterCard" />
      </node>
    </node>
  </root>
</model>

