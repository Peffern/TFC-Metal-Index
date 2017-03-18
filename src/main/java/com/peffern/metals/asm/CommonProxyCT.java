package com.peffern.metals.asm;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;
import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Override the Tile Entity setup to use the custom ingot pile tile entity
 * @author peffern
 *
 */
public class CommonProxyCT implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) 
	{
		if(name.equals("com.bioxx.tfc.CommonProxy"))
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
			if((m.name.equals("registerTileEntities")) && m.desc.equals("(Z)V"))
			{
				ListIterator<AbstractInsnNode> it = m.instructions.iterator();
				//iterate over the bytecode instructions
				while(it.hasNext())
				{
					AbstractInsnNode next = it.next();
					if(next instanceof LdcInsnNode)
					{
						LdcInsnNode linsn = (LdcInsnNode)next;
						if(linsn.cst instanceof Type)
						{
							Type type = (Type)linsn.cst;
							if(type.equals(Type.getType("Lcom/bioxx/tfc/TileEntities/TECrucible;")))
							{
								LdcInsnNode newLinsn = new LdcInsnNode(Type.getType("Lcom/peffern/metals/TECustomCrucible;"));
								m.instructions.insert(linsn,newLinsn);
								m.instructions.remove(linsn);
							}
							else if(type.equals(Type.getType("Lcom/bioxx/tfc/TileEntities/TEForge;")))
							{
								LdcInsnNode newLinsn = new LdcInsnNode(Type.getType("Lcom/peffern/metals/TECustomForge;"));
								m.instructions.insert(linsn,newLinsn);
								m.instructions.remove(linsn);
							}
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
