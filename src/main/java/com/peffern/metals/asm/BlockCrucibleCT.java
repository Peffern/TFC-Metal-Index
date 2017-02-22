package com.peffern.metals.asm;

import java.util.ListIterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Override the Tile Entity setup to use the custom ingot pile tile entity
 * @author peffern
 *
 */
public class BlockCrucibleCT implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) 
	{
		if(name.equals("com.bioxx.tfc.Blocks.Devices.BlockCrucible"))
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
			if((m.name.equals("createNewTileEntity")) && m.desc.equals("(Lnet/minecraft/world/World;I)Lnet/minecraft/tileentity/TileEntity;"))
			{
				ListIterator<AbstractInsnNode> it = m.instructions.iterator();
				//iterate over the bytecode instructions
				while(it.hasNext())
				{
					AbstractInsnNode insn = it.next();
					if(insn instanceof TypeInsnNode)
					{
						TypeInsnNode tinsn = (TypeInsnNode)insn;
						if(tinsn.desc.equals("com/bioxx/tfc/TileEntities/TECrucible"))
						{
							TypeInsnNode newTinsn = new TypeInsnNode(tinsn.getOpcode(), "com/peffern/metals/TECustomCrucible");
							m.instructions.insert(tinsn,newTinsn);
							m.instructions.remove(tinsn);
						}
					}
					else if(insn instanceof MethodInsnNode)
					{
						MethodInsnNode minsn = (MethodInsnNode)insn;
						if(minsn.owner.equals("com/bioxx/tfc/TileEntities/TECrucible"))
						{
							MethodInsnNode newMinsn = new MethodInsnNode(minsn.getOpcode(), "com/peffern/metals/TECustomCrucible", minsn.name, minsn.desc, minsn.itf);
							m.instructions.insert(minsn, newMinsn);
							m.instructions.remove(minsn);
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
