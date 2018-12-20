package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * Created by Anur IjuoKaruKas on 2018/12/19
 */
public class DynamicProxy implements InvocationHandler {

    public static void main(String[] args) {
        Animal wolf = (Animal) new DynamicProxy().bind(new Wolf());
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        System.out.println(wolf.say());
    }

    public Object object;

    public Object bind(Object o) {
        this.object = o;
        return Proxy.newProxyInstance(o.getClass()
                                       .getClassLoader(), o.getClass()
                                                           .getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object o = method.invoke(object, args);
        o = o + "-Dynamic";
        return o;
    }
}
