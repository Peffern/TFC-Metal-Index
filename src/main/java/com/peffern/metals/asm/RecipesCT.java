package com.peffern.metals.asm;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

import static org.objectweb.asm.Opcodes.*;

/**
 * Insert Pewter-sheeted Trapdoors into the trapdoor creator method
 * @author peffern
 *
 */
public class RecipesCT implements IClassTransformer
{

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) 
	{
		if(name.equals("com.bioxx.tfc.Core.Recipes"))
			return asmify(basicClass);
		else
			return basicClass;
	}
	
	private byte[] asmify(byte[] bytes)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		for(MethodNode m : classNode.methods)
		{
			if((m.name.equals("addTrapDoor")) && m.desc.equals("(Lnet/minecraft/item/Item;I)V"))
			{
				ListIterator<AbstractInsnNode> it = m.instructions.iterator();
				//iterate over the bytecode instructions
				while(it.hasNext())
				{
					AbstractInsnNode insn = it.next();
					if(insn instanceof InsnNode)
					{
						InsnNode theInsn = (InsnNode)insn;
						if(theInsn.getOpcode() == RETURN)
						{
							AbstractInsnNode previous = theInsn.getPrevious();
							
							InsnList insns = new InsnList();
							
							insns.add(new VarInsnNode(ALOAD,2));
							insns.add(new VarInsnNode(ALOAD,0));
							insns.add(new VarInsnNode(ILOAD,1));
							insns.add(new MethodInsnNode(INVOKESTATIC, "com/peffern/metals/TFCMetalIndex", "addTrapDoors", "(Lcom/bioxx/tfc/api/Crafting/AnvilManager;Lnet/minecraft/item/Item;I)V", false));

							m.instructions.insert(previous, insns);							
						}
					}
				}
			}
		}
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
	}

}
