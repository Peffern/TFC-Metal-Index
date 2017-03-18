package com.peffern.metals.asm;

import java.util.ArrayList;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

import static org.objectweb.asm.Opcodes.*;

/**
 * Override the block setup to use custom sheet and trapdoor blocks
 * @author peffern
 *
 */
public class WAILADataCT implements IClassTransformer
{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) 
	{
		if(name.equals("com.bioxx.tfc.WAILA.WAILAData"))
			return asmify(basicClass);
		else
			return basicClass;
	}
	
	private byte[] asmify(byte[] bytes)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		/*
		 * {
mv = cw.visitMethod(ACC_PUBLIC, "metalTrapDoorBody", "(Lnet/minecraft/item/ItemStack;Ljava/util/List;Lmcp/mobius/waila/api/IWailaDataAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Ljava/util/List;", "(Lnet/minecraft/item/ItemStack;Ljava/util/List<Ljava/lang/String;>;Lmcp/mobius/waila/api/IWailaDataAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Ljava/util/List<Ljava/lang/String;>;", null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(964, l0);
mv.visitMethodInsn(INVOKESTATIC, "com/bioxx/tfc/WAILA/WAILAData", "s", "()Ljava/lang/String;", false);
mv.visitVarInsn(ASTORE, 5);
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLineNumber(965, l1);
mv.visitVarInsn(ALOAD, 2);
mv.visitVarInsn(ALOAD, 5);
mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
mv.visitInsn(POP);
Label l2 = new Label();
mv.visitLabel(l2);
mv.visitLineNumber(966, l2);
mv.visitVarInsn(ALOAD, 2);
mv.visitInsn(ARETURN);
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLocalVariable("this", "Lcom/bioxx/tfc/WAILA/WAILAData;", null, l0, l3, 0);
mv.visitLocalVariable("itemStack", "Lnet/minecraft/item/ItemStack;", null, l0, l3, 1);
mv.visitLocalVariable("currenttip", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", l0, l3, 2);
mv.visitLocalVariable("accessor", "Lmcp/mobius/waila/api/IWailaDataAccessor;", null, l0, l3, 3);
mv.visitLocalVariable("config", "Lmcp/mobius/waila/api/IWailaConfigHandler;", null, l0, l3, 4);
mv.visitLocalVariable("t", "Ljava/lang/String;", null, l1, l3, 5);
mv.visitMaxs(2, 6);
mv.visitEnd();
}
		 */
		
		
		MethodNode m = new MethodNode(ACC_PUBLIC, "metalTrapDoorBody", "(Lnet/minecraft/item/ItemStack;Ljava/util/List;Lmcp/mobius/waila/api/IWailaDataAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Ljava/util/List;", "(Lnet/minecraft/item/ItemStack;Ljava/util/List<Ljava/lang/String;>;Lmcp/mobius/waila/api/IWailaDataAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Ljava/util/List<Ljava/lang/String;>;", null);
		m.instructions = new InsnList();
		LabelNode l0 = new LabelNode();
		m.instructions.add(l0);
		m.instructions.add(new VarInsnNode(ALOAD, 3));
		m.instructions.add(new MethodInsnNode(INVOKESTATIC, "com/peffern/metals/TFCMetalIndex", "s", "(Ljava/lang/Object;)Ljava/lang/String;", false));
		m.instructions.add(new VarInsnNode(ASTORE,5));
		LabelNode l1 = new LabelNode();
		m.instructions.add(l1);
		m.instructions.add(new VarInsnNode(ALOAD, 2));
		m.instructions.add(new VarInsnNode(ALOAD, 5));
		m.instructions.add(new MethodInsnNode(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true));
		m.instructions.add(new InsnNode(POP));
		LabelNode l2 = new LabelNode();
		m.instructions.add(l2);
		m.instructions.add(new VarInsnNode(ALOAD, 2));
		m.instructions.add(new InsnNode(ARETURN));
		LabelNode l3 = new LabelNode();
		m.instructions.add(l3);
		m.localVariables = new ArrayList<LocalVariableNode>();
		m.localVariables.add(new LocalVariableNode("this", "Lcom/bioxx/tfc/WAILA/WAILAData;", null, l0, l2, 0));
		m.localVariables.add(new LocalVariableNode("itemStack", "Lnet/minecraft/item/ItemStack;", null, l0, l2, 1));
		m.localVariables.add(new LocalVariableNode("currenttip", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", l0, l2, 2));
		m.localVariables.add(new LocalVariableNode("accessor", "Lmcp/mobius/waila/api/IWailaDataAccessor;", null, l0, l2, 3));
		m.localVariables.add(new LocalVariableNode("config", "Lmcp/mobius/waila/api/IWailaConfigHandler;", null, l0, l2, 4));
		m.localVariables.add(new LocalVariableNode("t", "Ljava/lang/String;", null, l1, l3, 5));
		m.maxStack = 2;
		m.maxLocals = 6;
		
		int s = classNode.methods.size();
		for(int i = 0; i < s; i++)
		{
			MethodNode me = classNode.methods.get(i);
			if(me.name.equals("metalTrapDoorBody") && me.desc.equals("(Lnet/minecraft/item/ItemStack;Ljava/util/List;Lmcp/mobius/waila/api/IWailaDataAccessor;Lmcp/mobius/waila/api/IWailaConfigHandler;)Ljava/util/List;"))
			{
				classNode.methods.set(i, m);
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
	}

}
