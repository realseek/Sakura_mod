package cn.mcmod.sakura;

import cn.mcmod.sakura.api.kimono.KimonoLoader;
import cn.mcmod.sakura.block.BlockLoader;
import cn.mcmod.sakura.client.SakuraParticleType;
import cn.mcmod.sakura.entity.SakuraEntityRegister;
import cn.mcmod.sakura.entity.villager.VillagerCreationWA;
import cn.mcmod.sakura.event.SakuraEventLoader;
import cn.mcmod.sakura.item.ItemLoader;
import cn.mcmod.sakura.item.drinks.DrinksLoader;
import cn.mcmod.sakura.packet.PacketKeyMessage;
import cn.mcmod.sakura.packet.PacketKeyMessageHandler;
import cn.mcmod.sakura.potion.PotionLoader;
import cn.mcmod.sakura.tileentity.TileEntityRegistry;
import cn.mcmod.sakura.util.SakuraRecipeRegister;
import cn.mcmod.sakura.world.gen.WorldGenLoader;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber
public class CommonProxy {
    public static CreativeTabs tab;
    private static SimpleNetworkWrapper network;
    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }
    public void preInit(FMLPreInitializationEvent event) {
        tab = new CreativeTabsSakura();
        new PotionLoader(event);
        new BlockLoader(event);
        new ItemLoader(event);
        new DrinksLoader();
        SakuraEntityRegister.entityRegister();

        MinecraftForge.EVENT_BUS.register(new SakuraEventLoader());
        new SakuraOreDictLoader();
        VillagerCreationWA.registerComponents();
		VillagerCreationWA villageHandler = new VillagerCreationWA();
		VillagerRegistry.instance().registerVillageCreationHandler(villageHandler);
    }

    public void init(FMLInitializationEvent event) {
    	MinecraftForge.ORE_GEN_BUS.register(new WorldGenLoader());
    	new WorldGenLoader();
        TileEntityRegistry.init();
        KimonoLoader.Init();
        SakuraRecipeRegister.Init();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(SakuraMain.MODID);
    	network.registerMessage(new PacketKeyMessageHandler(),PacketKeyMessage.class,0,Side.SERVER);
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerFluidBlockRendering(Block block, String name) {

    }

    public void spawnParticle(SakuraParticleType particleType, double x, double y, double z, double velX, double velY, double velZ) {

    }

}
