package org.cjcoders;

import org.cjcoders.hexfight.gui.utils.resources.Resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mrakr_000 on 2014-05-21.
 */
public class PoliTest {

    private BlaA blaA = new BlaA();
    private BlaB blaB = new BlaB();

    public void sort(I i){
        blaA.use(i);
        blaB.use(i);
    }

    public static void main(String... args){
        I i = new A();
        PoliTest t = new PoliTest();
        t.sort(i);
    }

}

class BlaA{
    void use(I i){
        act(i);
    }
    private void act(I i) {}
    private void act(A i) {
        i.act();
    }
}
class BlaB{
    void use(I i){
        act(i);
    }
    private void act(I i) {}
    private void act(B i) {
        i.act();
    }
}


interface I{
    void act();
}

class A implements I{

    @Override
    public void act() {
        System.out.println("A");
    }
}
class B implements I{

    @Override
    public void act() {
        System.out.println("B");
    }
}
class C implements I{

    @Override
    public void act() {
        System.out.println("C");
    }
}
