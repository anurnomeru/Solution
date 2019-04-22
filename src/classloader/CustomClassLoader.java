package classloader;

import java.io.FileInputStream;

/**
 * Created by Anur IjuoKaruKas on 4/19/2019
 */
public class CustomClassLoader extends ClassLoader {

    //被加载的class文件存放目录
    private String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(java.lang.String name) throws ClassNotFoundException {
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(java.lang.String name) throws ClassNotFoundException {
        try {
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }

    private byte[] loadByte(String name) throws Exception {
        //通过类的全路径名称生成路径
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();

        return data;
    }
}
