package cn.mcmod.sakura.compat.ct;

import cn.mcmod.sakura.api.recipes.PotRecipes;
import cn.mcmod.sakura.util.SakuraRecipeRegister;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;


@ZenClass("mods.sakura.campfirePot")
@ZenRegister
public class CTSakuraCampfirePot {
	@ZenMethod
	public static void RemoveRecipe(IIngredient input) {
		Object itemInput=null;
		if (input instanceof IItemStack) {
			itemInput=CraftTweakerMC.getItemStack(input);
		} 
		else if(input instanceof IOreDictEntry) {
			itemInput=((IOreDictEntry)input).getName();
		}
		if(itemInput!=null)
			SakuraRecipeRegister.actions.add(new Removal(itemInput));
	}
	@ZenMethod
	public static void AddRecipe(IIngredient[] input,IItemStack output,ILiquidStack input_fluid) {
		if(input.length>0){
			Object[] array = new Object[input.length];
		    for(int i = 0; i < input.length;i++){
		    	if (input[i] instanceof IItemStack) 
		    		array[i]=CraftTweakerMC.getItemStack(input[i]);
				else if(input[i] instanceof IOreDictEntry) 
					array[i]=((IOreDictEntry)input[i]).getName();	
			}

		    SakuraRecipeRegister.actions.add(new Addition(array, CraftTweakerMC.getItemStack(output),CraftTweakerMC.getLiquidStack(input_fluid)));
		}
	}
	
	@ZenMethod
	public static void ClearAllRecipe() {
		SakuraRecipeRegister.actions.add(new ClearAllRecipe());
	}
	
    private static final class Removal implements IAction
    {
        private final Object itemInput;

        private Removal(Object itemInput)
        {
            this.itemInput = itemInput;
        }

        @Override
        public void apply()
        {
        	PotRecipes.ClearRecipe(itemInput);
        }

        @Override
        public String describe()
        {
            return "Removing a recipe for Campfire Pot";
        }
    }
	
    private static final class Addition implements IAction
    {
        private final Object[] itemInput;
        private final ItemStack itemOutput;
        private final FluidStack fluidInput;
        
        private Addition(Object[] itemInput, ItemStack itemOutput,FluidStack fluidInput)
        {
            this.itemInput = itemInput;
            this.itemOutput = itemOutput;
            this.fluidInput = fluidInput;
        }

        @Override
        public void apply()
        {
        	PotRecipes.addRecipe(new PotRecipes(itemOutput, itemInput, fluidInput));
        }

        @Override
        public String describe()
        {
            return "Adding a recipe for Stone Mortar";
        }
    }

	
	private static final class ClearAllRecipe implements IAction
    {
        @Override
        public void apply()
        {
        	PotRecipes.ClearAllRecipe();
        }

        @Override
        public String describe()
        {
            return "Removing all recipes from Stone Mortar";
        }
    }
}
