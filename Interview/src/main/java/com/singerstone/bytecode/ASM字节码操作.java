package com.singerstone.bytecode;


import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_STATIC;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;


public class ASM字节码操作 {

    public static void main(String[] args) {
        String rootPath = System.getProperty("user.dir");
        String classFilePath =
                rootPath + File.separator + "LeetCode" + File.separator + "MainFragment.class";
        String outClassFilePath =
                rootPath + File.separator + "LeetCode" + File.separator + "MainFragmentOut.class";
        System.out.println("yogachen->" + classFilePath);
        File classFile = new File(classFilePath);
        try {
            InputStream is = new FileInputStream(classFile);
            ClassReader classReader = new ClassReader(is);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor classVisitor = new TraceClassVisitor(Opcodes.ASM5, classWriter);
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
            FileOutputStream os = new FileOutputStream(outClassFilePath);
            os.write(classWriter.toByteArray());
            is.close();
            os.flush();
            os.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static class TraceClassVisitor extends ClassVisitor {


        public TraceClassVisitor(int i, ClassVisitor classVisitor) {
            super(i, classVisitor);
        }

        @Override
        public void visit(int version, int access, String name, String signature,
                String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            cv.visitMethod(ACC_PRIVATE, "empty", "()V", null, null);
            cv.visitMethod(ACC_PRIVATE | ACC_STATIC, "emptyStatic", "()V", null, null);
            cv.visitMethod(ACC_INTERFACE, "close", "()V", null,
                    new String[]{"java/lang/Exception"});
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                String signature, String[] exceptions) {
            MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
            return new TraceMethodAdapter(api, methodVisitor, access, name, desc);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
        }
    }

    public static class TraceMethodAdapter extends AdviceAdapter {


        protected TraceMethodAdapter(int api, MethodVisitor mv, int access, String name,
                String desc) {
            super(api, mv, access, name, desc);
        }

        @Override
        protected void onMethodEnter() {
            System.out.println(methodDesc);

            super.onMethodEnter();
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
        }
    }


}
