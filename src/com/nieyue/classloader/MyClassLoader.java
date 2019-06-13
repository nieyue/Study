package com.nieyue.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 自定义classloader
 */
public class MyClassLoader extends ClassLoader
 {
     Class<?> clazz=null;

     public void setClazz(Class<?> clazz) {
         this.clazz = clazz;
     }

     public static Class<?> init(String name) throws ClassNotFoundException {
         MyClassLoader mcl=new MyClassLoader(ClassLoader.getSystemClassLoader().getParent() );
         Class<?> c1=Class.forName(name,true,mcl);
         return c1;
     }

     public MyClassLoader()
    {
        
    }
    
    public MyClassLoader(ClassLoader parent)
    {
        super(parent);
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException
    {
        File file = getClassFile(name);
        try
        {
            byte[] bytes = getClassBytes(file);
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return super.findClass(name);
    }
    
    private File getClassFile(String name)
    {
        File file=null;

        try {
            clazz = Class.forName(name);//外部放入
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         //System.err.println(clazz.getResource("").getPath()+clazz.getSimpleName()+".class");
            // File file = new File("E:\\nieyue\\ide\\Study\\src\\com\\nieyue\\classloader\\Test.class");
            file = new File(clazz.getResource("").getPath()+clazz.getSimpleName()+".class");
        return file;
    }
    
    private byte[] getClassBytes(File file) throws Exception
    {
        // 这里要读入.class的字节，因此要使用字节流
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel wbc = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);
        
        while (true)
        {
            int i = fc.read(by);
            if (i == 0 || i == -1)
                break;
            by.flip();
            wbc.write(by);
            by.clear();
        }
        
        fis.close();
        
        return baos.toByteArray();
    }
}